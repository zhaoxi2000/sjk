package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.UserDao;
import com.ijinshan.sjk.po.User;
import com.ijinshan.util.HibernateHelper;

@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

    @Override
    public Class<User> getType() {
        return User.class;
    }

    @Override
    public User login(String username, String pwd) {
        Query query = getSession().createQuery("from User u where u.name=:username and password=:pwd");
        query.setString("username", username);
        query.setString("pwd", pwd);
        @SuppressWarnings("unchecked")
        List<User> list = query.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        String hql = "delete User where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate() == ids.size();
    }

    @Override
    public List<User> queryUserList(int page, int rows, String keyword, String order, String sort) {
        // 查询条件
        Criteria cri = searchByFilter(keyword);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<User> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(String keyword) {
        Criteria cri = searchByFilter(keyword);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    private Criteria searchByFilter(String keyword) {
        Criteria cri = getSession().createCriteria(User.class);
        if (StringUtils.isNotEmpty(keyword)) {
            cri.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
        }
        return cri;
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "desc".equals(order.toLowerCase())) {
                cri.addOrder(Order.desc("id"));
            } else {
                cri.addOrder(Order.asc("id"));
            }
        }
    }

    @Override
    public boolean updatePwd(Integer id, String newPwd) {
        String hqlUpdate = "update User u set u.password = :newPwd where u.id = :id";
        int updateCounts = getSession().createQuery(hqlUpdate).setString("newPwd", newPwd).setInteger("id", id)
                .executeUpdate();
        return updateCounts > 0;
    }

    @Override
    public boolean isExistsUser(String name, String email) {
        Query query = getSession().createQuery("from User u where u.name=:username and  u.email=:email");
        query.setString("username", name);
        query.setString("email", email);
        @SuppressWarnings("unchecked")
        List<User> list = query.list();
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
