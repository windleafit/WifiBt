package me.yangyong.kity.java.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 取得系统时间
 * 1。
 * long time=System.currentTimeMillis();
 * <p/>
 * 2。
 * final Calendar mCalendar=Calendar.getInstance();
 * mCalendar.setTimeInMillis(time);
 * 取得小时：mHour=mCalendar.get(Calendar.HOUR);
 * 取得分钟：mMinuts=mCalendar.get(Calendar.MINUTE);
 * <p/>
 * <p/>
 * 3。
 * Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
 * t.setToNow(); // 取得系统时间。
 * int year = t.year;
 * int month = t.month;
 * int date = t.monthDay;
 * int hour = t.hour;    // 0-23
 * <p/>
 * 4。
 * DateFormat df = new SimpleDateFormat("HH:mm:ss");
 * df.format(new Date());
 */
public class Time {

    private Time() {

    }

    /**
     * 当前时间，时:分:秒:毫秒
     *
     * @return
     */
    public static final String millis() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        return df.format(new java.util.Date());
    }

    /**
     * 当前时间，时:分:秒
     *
     * @return
     */
    public static final String second() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new java.util.Date());
    }

    /**
     * 当前时间，时:分
     *
     * @return
     */
    public static final String minute() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(new java.util.Date());
    }

    /**
     * 当前时间，时
     *
     * @return
     */
    public static final String hour() {
        DateFormat df = new SimpleDateFormat("HH");
        return df.format(new java.util.Date());
    }

    /**
     * 获取当前时间
     *
     * @param HH  时
     * @param mm  分
     * @param ss  秒
     * @param SSS 毫秒
     * @return
     */
    public static final String time(boolean HH, boolean mm, boolean ss, boolean SSS) {
        StringBuffer format = new StringBuffer();
        if (HH) {
            format.append("HH");
        }
        if (mm) {
            if (format.length() != 0)
                format.append(":");
            format.append("mm");
        }
        if (ss) {
            if (format.length() != 0)
                format.append(":");
            format.append("ss");
        }
        if (SSS) {
            if (format.length() != 0)
                format.append(":");
            format.append("SSS");
        }
        DateFormat df = new SimpleDateFormat(format.toString());
        return df.format(new java.util.Date());
    }

}
