package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.dao.MoTagDao;
import com.ijinshan.sjk.po.MoTag;
import com.ijinshan.sjk.po.marketmerge.MoTagTopic;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MoTagDaoImpl extends AbstractBaseDao<MoTag> implements MoTagDao {
    private static final Logger logger = LoggerFactory.getLogger(MoTagDaoImpl.class);

    @Override
    public Class<MoTag> getType() {
        return MoTag.class;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<MoTag> list(TagType tagType) {
        Criteria cri = getSession().createCriteria(MoTag.class);
        if (null != tagType) {
            cri.add(Restrictions.eq("tagType", Short.valueOf(tagType.getVal())));
        }
        return HibernateHelper.list(cri);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        String hql = "delete MoTag where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public long countForSearching(Short pId, Short tagType, String keywords) {
        // 查询条件
        Criteria cri = searchByFilter(pId, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public List<MoTag> search(Short pId, String keywords, int page, int rows, String sort, String order) {
        // 查询条件
        Criteria cri = searchByFilter(pId, keywords);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<MoTag> list = HibernateHelper.list(cri);
        return list;
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "asc".equals(order.toLowerCase())) {
                cri.addOrder(Order.asc("id"));
            } else {
                cri.addOrder(Order.desc("id"));
            }
        }
    }

    // 查询条件
    private Criteria searchByFilter(Short pId, String keywords) {
        Criteria cri = getSession().createCriteria(MoTag.class);
        if (pId != null) {
            cri.add(Restrictions.eq("pid", Integer.valueOf(pId)));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

    @Override
    public List<MoTagTopic> searchTagList(Short pid, Short tagType, String keywords, int page, int rows, String sort,
            String order) {
        List<MoTagTopic> list = new ArrayList<MoTagTopic>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT   x.Id, x.Name, x.TagDesc, x.Catalog,x.TagType, x.ImgUrl, x.PId,x.Rank, t.Name as PName,x.BigPics,x.MediumPics,x.SmallPics ");
        sbSql.append(" FROM Mo_Tag x LEFT JOIN   Mo_Tag t  ");
        sbSql.append(" ON x.PId = t.Id ");
        sbSql.append(" where 1 = 1 ");
        if (pid != null) {
            sbSql.append("  and (x.PId =:pid)");
        }
        if (tagType != null) {
            sbSql.append("  and x.TagType =:tagType ");
        }
        if (StringUtils.isNotEmpty(keywords)) {
            sbSql.append("  and x.Name like :Name ");
        }
        if (null != order && !"".equals(order.trim()) && null != sort && !"".equals(sort)) {
            sbSql.append("  order by ");
            sbSql.append(sort + "  ");
            sbSql.append(order);
        } else {
            sbSql.append(" order by id desc");
        }
        Query q = getSession().createSQLQuery(sbSql.toString()).addEntity(MoTagTopic.class);
        if (pid != null) {
            q.setInteger("pid", Integer.valueOf(pid));
        }
        if (tagType != null) {
            q.setInteger("tagType", Integer.valueOf(tagType));
        }
        if (StringUtils.isNotEmpty(keywords)) {
            q.setString("Name", "%" + keywords + "%");
        }
        q.setFirstResult(((page - 1) * rows));
        q.setMaxResults(rows);
        list = HibernateHelper.list(q);
        return list;
    }
}
