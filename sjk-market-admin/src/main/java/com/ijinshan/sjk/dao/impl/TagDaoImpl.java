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
import com.ijinshan.sjk.dao.TagDao;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.po.marketmerge.TagTopic;
import com.ijinshan.util.HibernateHelper;

@Repository
public class TagDaoImpl extends AbstractBaseDao<Tag> implements TagDao {
    private static final Logger logger = LoggerFactory.getLogger(TagDaoImpl.class);

    @Override
    public Class<Tag> getType() {
        return Tag.class;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Tag> list(TagType tagType) {
        Criteria cri = getSession().createCriteria(Tag.class);
        if (null != tagType) {
            // 注释原因： tagType是枚举类型（int） Tag实体tagType属性是Short类型 不能直接把int 转化成
            // short 类型
            cri.add(Restrictions.eq("tagType", Short.valueOf(tagType.getVal())));
        }
        cri.addOrder(Order.desc("rank"));
        return HibernateHelper.list(cri);
    }

    @Override
    public List<TagTopic> searchTopicList(Integer pid, String keywords, int page, int rows, String sort, String order) {
        List<TagTopic> list = new ArrayList<TagTopic>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT   x.Id, x.Name, x.TagDesc, x.Catalog,x.TagType, x.ImgUrl, x.PId,x.Rank, t.Name as PName ");
        sbSql.append(" FROM Tag x LEFT JOIN   Tag t  ");
        sbSql.append(" ON x.PId = t.Id ");
        sbSql.append(" where x.TagType=1 ");
        if (pid != null) {
            sbSql.append("  and (x.PId =:pid)");
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
        Query q = getSession().createSQLQuery(sbSql.toString()).addEntity(TagTopic.class);
        if (pid != null) {
            q.setInteger("pid", Integer.valueOf(pid));
        }
        if (StringUtils.isNotEmpty(keywords)) {
            q.setString("Name", "%" + keywords + "%");
        }
        q.setFirstResult(((page - 1) * rows));
        q.setMaxResults(rows);
        list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public long countForSearching(Integer pid, TagType tagType, String keywords) {
        // 查询条件
        Criteria cri = getSession().createCriteria(Tag.class);
        if (pid != null) {
            cri.add(Restrictions.eq("pid", pid));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        cri.add(Restrictions.eq("tagType", tagType.getVal()));
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        String hql = "delete Tag where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

}
