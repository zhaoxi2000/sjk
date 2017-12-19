package com.kingsoft.sjk.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

public final class HibernateHelper {

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(final Query query) {
        return query.list();
    }
}
