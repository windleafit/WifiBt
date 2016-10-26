package me.yangyong.kity.java.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by JOUAV on 2015/12/23.
 */
public class Date {

    private Date() {

    }

    public static final String date() {
        DateFormat df = new SimpleDateFormat("");
        return df.format(new java.util.Date());
    }
}
