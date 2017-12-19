package com.ijinshan.sjk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] firstArray = new int[] { 2, 3, };
        int[] secondArray = new int[] { 10, 20, 51 };
        for (int a : firstArray) {
            for (int b : secondArray) {
                System.out.println(a + "," + b);
            }
        }

    }
}
