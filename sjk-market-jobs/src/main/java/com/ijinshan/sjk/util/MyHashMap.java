package com.ijinshan.sjk.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHashMap<K, Object> extends HashMap<K, Object> {
    private static final Logger logger = LoggerFactory.getLogger(MyHashMap.class);

    public MyHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Object put(K key, Object value) {

        if (this.containsKey(key)) {
            TreeSet<String> val = (TreeSet) super.get(key);
            TreeSet<String> tset = (TreeSet) value;
            Iterator<String> s = tset.iterator();
            while (s.hasNext()) {
                val.add(s.next());
            }
            Object obj = (Object) val;
            return super.put(key, obj);
        }
        return super.put(key, value);
    }

}
