package com.kingsoft.sjk;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTurnLine {
    private static final Logger logger = LoggerFactory.getLogger(TestTurnLine.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        String fileDir = "c:/test123.txt";

        try {
            String updateInfo = FileUtils.readFileToString(new File(fileDir), "UTF-8");

            updateInfo = updateInfo.replaceAll("\r\n", "<br />").replaceAll("\\\\n", "");
            System.out.println(updateInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
