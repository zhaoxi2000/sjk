package com.ijinshan.sjk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTimeFormat {

    public static void main(String[] args) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(date);
    }

}
