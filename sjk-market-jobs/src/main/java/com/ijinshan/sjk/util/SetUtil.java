package com.ijinshan.sjk.util;

import java.util.Set;
import java.util.TreeSet;

public class SetUtil {
  //  private static final Logger logger = LoggerFactory.getLogger(SetUtil.class);
    
    public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
        Set<T> tmp = new TreeSet<T>(setA);
        tmp.addAll(setB);
        return tmp;
      }

      public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
        Set<T> tmp = new TreeSet<T>();
        for (T x : setA)
          if (setB.contains(x))
            tmp.add(x);
        return tmp;
      }

      public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
        Set<T> tmp = new TreeSet<T>(setA);
        tmp.removeAll(setB);
        return tmp;
      }
      public static <T> TreeSet<T> differenceT(TreeSet<T> setA, TreeSet<T> setB) {
          TreeSet<T> tmp = new TreeSet<T>(setA);
          tmp.removeAll(setB);
          return tmp;
        }

      public static <T> Set<T> symDifference(Set<T> setA, Set<T> setB) {
        Set<T> tmpA;
        Set<T> tmpB;
        tmpA = union(setA, setB);
        tmpB = intersection(setA, setB);
        return difference(tmpA, tmpB);
      }

      public static <T> boolean isSubset(Set<T> setA, Set<T> setB) {
        return setB.containsAll(setA);
      }

      public static <T> boolean isSuperset(Set<T> setA, Set<T> setB) {
        return setA.containsAll(setB);
      }

}
