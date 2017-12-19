package com.kingsoft.sjk.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

public class HibernateHelper {

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Query query) {
        return query.list();
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
