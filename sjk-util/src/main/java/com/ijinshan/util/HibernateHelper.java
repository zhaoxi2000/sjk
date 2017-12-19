package com.ijinshan.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

public final class HibernateHelper {

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Query query) {
        return query.list();
    }

    public static <T> String toSQLList(List<T> list) {
        StringBuilder sb = new StringBuilder(list.size() * 10);
        for (T t : list) {
            sb.append(t).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int firstResult(int currentPage, int pageSize) {
        final int offset = (currentPage - 1) * pageSize;
        
        if (offset < 0) {
            StringBuilder msg = new StringBuilder(100);
            msg.append(" offset: ").append(offset);
            msg.append(" currentPage: ").append(currentPage);
            msg.append(" pageSize: ").append(pageSize);
            throw new IllegalArgumentException(msg.toString());
        }
        return offset;
    }

    public static void addOrder(Criteria cri, String sort, String order) {
        if (order != null) {
            if (!"asc".equals(order) && !"desc".equals(order)) {
                throw new IllegalArgumentException(order);
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if ("asc".equals(order)) {
                cri.addOrder(Order.asc(sort));
            } else {
                cri.addOrder(Order.desc(sort));
            }
        }
    }
}
