package com.ijinshan.sjk.service.programmeimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.AbstractBaseMarketService;

public class ApiImporterMarketServiceImpl extends AbstractBaseMarketService {
    private static final Logger logger = LoggerFactory.getLogger(ApiImporterMarketServiceImpl.class);

    @Override
    public void importFull() {
        logger.info("{}. ImportFull begins... ", this.getMarketName());
        Date lastReqDate = new Date();
        PaginationMarketApp marketPagination = null;
        int again = 0;
        boolean hasException = false;
        do {
            // logger.debug("Begin to get next page for {}!",
            // this.getMarketName());
            try {
                hasException = false;
                marketPagination = savePaginationForFull();
            } catch (Exception e) {
                hasException = true;
            }
            if (marketPagination != null) {
                again = 0;
            } else {
                again++;
                if (again < TRY_TIME) {
                    sleepForTry();
                }
            }
            if (marketPagination == null && again >= TRY_TIME) {
                logger.info("{}. It is a fail to importFull !!!", this.getMarketName());
                ignoreCurrentPageForFull();
                again = 0;
            }
        } while ((hasException && again < TRY_TIME)
                || marketPagination.getCurrentPage() < marketPagination.getTotalPages());

        logger.info("ImportFull done!  {}", this.getMarketName());
        resetMarketForFull(lastReqDate);
    }

    @Override
    public void importIncrement() {
        logger.info("{}. ImportIncrement begins...", this.getMarketName());
        Session session = null;
        Transaction tx = null;
        boolean successed = false;
        Date now = new Date();
        do {
            session = sessions.openSession();
            session.setDefaultReadOnly(false);
            try {
                Market market = null;
                try {
                    market = getMarket(session);
                } finally {
                    session.close();
                }
                int currentPage = 0, totalPage = 0;
                oneIncrement: do {
                    PaginationMarketApp increment = getMarketAppForIncrement(market);
                    if (increment != null) {
                        currentPage = increment.getCurrentPage();
                        totalPage = increment.getTotalPages();
                        List<MarketApp> marketApps = increment.getData();
                        List<MarketApp> offMarketApps = new ArrayList<MarketApp>();
                        if (marketApps != null && !marketApps.isEmpty()) {
                            // open again
                            if (!session.isOpen()) {
                                session = sessions.openSession();
                            }
                            tx = session.beginTransaction();
                            savePaginationMarketApp(session, market, marketApps, offMarketApps);
                            market.setIncrementLastReqCurrentPage(currentPage);
                            market.setIncrementTotalPages(totalPage);
                            session.merge(market);
                            tx.commit();
                            tx = null;
                            session.close();
                            deleteMarketAppsTransaction(offMarketApps);
                            logger.info(
                                    "{}. Increment totalPage: {}\t currentPage: {}\t size: {}\t offMarketApps size: {}",
                                    this.getMarketName(), totalPage, currentPage, marketApps.size(),
                                    offMarketApps.size());
                            marketApps.clear();
                            offMarketApps.clear();
                        }
                        if (currentPage >= totalPage) {
                            successed = true;
                        }
                    } else {
                        successed = false;
                        break oneIncrement;
                    }
                } while (totalPage > 0 && currentPage < totalPage);
                if (successed) {
                    resetMarketForIncrement(now);
                } else {
                    sleepForTry();
                }
            } catch (Exception e) {
                successed = false;
                if (tx != null) {
                    tx.rollback();
                }
                logger.error("Exception", e);
                sleepForTry();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        } while (!successed);
        logger.info("{}. ImportIncrement done!", this.getMarketName());
    }

    private PaginationMarketApp savePaginationForFull() throws Exception {
        // saveOrUpdate to table MarketApp
        PaginationMarketApp marketPagination = null;
        Session session = sessions.openSession();
        session.setDefaultReadOnly(false);
        Transaction tx = null;
        try {
            Market market = getMarket(session);
            marketPagination = getMarketAppForFull(market);
            if (marketPagination != null && marketPagination.getTotalPages() >= marketPagination.getCurrentPage()) {
                List<MarketApp> marketApps = marketPagination.getData();
                List<MarketApp> offMarketApps = new ArrayList<MarketApp>();
                if (marketApps != null && !marketApps.isEmpty()) {
                    Object[] infos = new Object[] { market.getMarketName(), marketPagination.getTotalPages(),
                            marketPagination.getCurrentPage(), marketApps.size() };
                    accessMarketDao.getMarketlogger().info(
                            "Begin to import {} on one page. totalPages: {} currentPage : {} , size: {}", infos);
                    tx = session.beginTransaction();
                    savePaginationMarketApp(session, market, marketApps, offMarketApps);
                    market.setFullLastReqCurrentPage(marketPagination.getCurrentPage());
                    market.setFullTotalPages(marketPagination.getTotalPages());
                    market.setFullLastTime(new Date());
                    session.merge(market);
                    tx.commit();
                    tx = null;
                    session.clear();
                    session.close();
                    deleteMarketAppsTransaction(offMarketApps);
                    marketApps.clear();
                    offMarketApps.clear();
                }
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Exception", e);
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return marketPagination;
    }

    public PaginationMarketApp getMarketAppForIncrement(Market market) throws Exception {
        return getAccessMarketDao().getMarketAppForIncrement(market);
    }

    public PaginationMarketApp getMarketAppForFull(Market market) throws Exception {
        return getAccessMarketDao().getMarketAppForFull(market);
    }

    private void ignoreCurrentPageForFull() {
        Session session = super.sessions.openSession();
        try {
            Market market = super.getMarket(session);
            market.setFullLastReqCurrentPage(market.getFullLastReqCurrentPage() + 1);
            market.setFullLastTime(new Date());
            marketDao.update(session, market);
            session.flush();
            session.clear();
            logger.info("IgnoreCurrentPage market! {}", market.getMarketName());
        } finally {
            session.close();
        }
    }

}
