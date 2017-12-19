package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.TagAppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.util.HibernateHelper;

@Repository
public class TagAppDaoImpl extends AbstractBaseDao<AppAndTag> implements TagAppDao {
    private static final Logger logger = LoggerFactory.getLogger(TagAppDaoImpl.class);

    @Override
    public List<AppAndTag> getTags(int tagId, short catalog) {

        Query query = getSession()
                .createQuery(
                        "from AppAndTag re left join fetch re.tag where tagId=:tagId or re.tag.pid =:pid   ORDER BY tagId, rank DESC");
        query.setParameter("tagId", tagId);
        query.setParameter("pid", tagId);
        List<AppAndTag> list = HibernateHelper.list(query);
        List<AppAndTag> ouputList = new ArrayList<AppAndTag>();
        if (list != null && list.size() > 0) {
            setTagApps(catalog, list, ouputList);
        }
        return list;
    }

    /* 获取标签下的应用集合 */
    private void setTagApps(short catalog, List<AppAndTag> list, List<AppAndTag> ouputList) {
        Integer[] ids = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getAppId();
        }
        List<App> appList = getTagApps(ids);
        for (AppAndTag tagApp : list) {
            if (appList != null && appList.size() > 0) {
                setAppAndTag(appList, tagApp);
                // 无用代码 ？
                if (tagApp.getTag().getId() == catalog)
                    ouputList.add(tagApp);
            }
        }
    }

    /* 获取应用 */
    private void setAppAndTag(List<App> appList, AppAndTag appAndTag) {
        for (App app : appList) {
            if (appAndTag.getAppId() == app.getId()) {
                appAndTag.setApp(app);
                break;
            }
        }
    }

    /* 获取专题应用 */
    private List<App> getTagApps(Integer[] ids) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("hidden", false));
        cri.add(Restrictions.in("id", ids));
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public Class<AppAndTag> getType() {
        return AppAndTag.class;
    }
}
