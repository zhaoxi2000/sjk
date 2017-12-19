package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.MetroDao;
import com.ijinshan.sjk.po.Metro;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MetroDaoImpl extends AbstractBaseDao<Metro> implements MetroDao {
    private static final Logger logger = LoggerFactory.getLogger(MetroDaoImpl.class);

    @Override
    public Class<Metro> getType() {
        return Metro.class;
    }

    @Override
    public List<Metro> search(String type, Boolean hidden, Boolean deleted) {
        Criteria cri = getSession().createCriteria(Metro.class);
        if (type != null && StringUtils.isNotEmpty(type)) {
            cri.add(Restrictions.eq("type", type));
        }
        if (hidden != null) {
            cri.add(Restrictions.eq("hidden", hidden));
        }
        if (deleted != null) {
            cri.add(Restrictions.eq("deleted", deleted));
        }
        List<Metro> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long count(String type) {
        StringBuilder queryString = new StringBuilder("select count(id) from ").append(getType().getName());
        queryString.append(" where type = :type and hidden = 0");
        Query query = getSession().createQuery(queryString.toString());
        query.setParameter("type", type);
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public int delete(int id) {
        Query query = getSession().createQuery("delete from Metro where id=:id");
        query.setInteger("id", id);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public Boolean updateDeleted(List<Integer> ids, boolean deleted) {
        String hql = "update Metro set Deleted =:deleted where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setBoolean("deleted", deleted);
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    @Override
    public int updateHide(List<Integer> ids) {
        String hql = "update Metro set Hidden = 1 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public int updateShow(List<Integer> ids) {
        String hql = "update Metro set Hidden = 0 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

}
