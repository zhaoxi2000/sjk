package com.ijinshan.sjk.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.vo.pc.SearchApp;

public final class ListSort {
    private static final Logger logger = LoggerFactory.getLogger(ListSort.class);

    @SuppressWarnings("unchecked")
    public static <T extends Object> void sort(List<Integer> orderedIds, List<T> list, boolean listIsOrderedByPK) {
        Object[] a = list.toArray();
        List<T> tmp = new ArrayList<T>(list.size());
        Object tmpO = null;
        if (!listIsOrderedByPK) {
            for (Integer id : orderedIds) {
                for (Object obj : a) {
                    tmpO = obj;
                    if (tmpO instanceof App) {
                        if (((App) obj).getId().intValue() == id.intValue()) {
                            tmp.add((T) tmpO);
                        }
                    } else if (tmpO instanceof SearchApp) {
                        if (((SearchApp) obj).getId() == id.intValue()) {
                            tmp.add((T) tmpO);
                        }
                    }
                }
            }
        } else {
            for (Integer id : orderedIds) {
                int index = binarySearchIndex(list, id.intValue());
                if (index >= 0) {
                    tmp.add(list.get(index));
                }
            }
        }
        list.clear();
        list.addAll(tmp);
        tmp.clear();
        tmp = null;
    }

    /**
     * @param list
     * @param value
     * @return -1 not found
     */
    public static <T extends Object> int binarySearchIndex(List<T> list, int value) {
        int low = 0;
        int high = list.size() - 1;
        int mid = -1;
        while (low <= high) {
            mid = (low + high) >> 1;
            Object o = list.get(mid);
            int objVal = 0;
            if (o instanceof App) {
                objVal = ((App) o).getId().intValue();
            } else if (o instanceof SearchApp) {
                objVal = ((SearchApp) o).getId();
            }

            if (objVal == value)
                return mid;
            else if (objVal > value)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}
