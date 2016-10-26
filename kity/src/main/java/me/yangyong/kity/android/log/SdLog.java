package me.yangyong.kity.android.log;


import me.yangyong.kity.android.sd.SdCard;
import me.yangyong.kity.java.time.Time;

import java.io.IOException;
import java.util.List;

/**
 * 写在SD卡根目录里的工具类
 */
public class SdLog {

    private static final boolean log = true;
    private static final String dir = "";

    private SdLog() {

    }
//    public static final String dir = "SdLog" + File.separator;

    /**
     * 写文件(覆盖)
     *
     * @param fileName
     * @param content
     * @param time     显示时间
     */
    public static void cover(String fileName, String content, boolean time) {
        if (!log)
            return;
        write(fileName, (time ? lable() + "\n" : "") + content, false);
    }

    /**
     * 写文件
     *
     * @param fileName 文件名
     * @param content  内容
     * @param add      是追加
     */
    public static void write(String fileName, String content, boolean add) {
        if (!log)
            return;
        SdCard sdCard = new SdCard();
        try {
            sdCard.write(dir + fileName, content, add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String lable() {
        return "[" + Time.millis() + "]";
    }

    /**
     * 写文件(覆盖)
     *
     * @param fileName
     * @param content
     */
    public static void cover(String fileName, String content) {
        if (!log)
            return;
        write(fileName, content, false);
    }

    /**
     * 写文件(追加)，是否开启时间戳
     *
     * @param fileName
     * @param content
     * @param time     显示时间
     */
    public static void add(String fileName, String content, boolean time) {
        if (!log)
            return;
        write(fileName, (time ? lable() + "\n" : "") + content, true);
    }

    /**
     * 写文件(追加)
     *
     * @param fileName
     * @param content
     */
    public static void add(String fileName, String content) {
        if (!log)
            return;
        write(fileName, content, true);
    }

    /**
     * 写文件(追加)，换行，是否开启时间戳
     *
     * @param fileName
     */
    public static void line(String fileName, boolean time) {
        if (!log)
            return;
        write(fileName, (time ? lable() : "") + "\n", true);
    }

    /**
     * 写文件(追加)，换行，不显示时间戳
     *
     * @param fileName
     * @param content
     */
    public static void line(String fileName, String content) {
        if (!log)
            return;
        line(fileName, content, false);
    }

    /**
     * 写文件(追加)，换行，是否开启时间戳
     *
     * @param fileName
     * @param content
     * @param time
     */
    public static void line(String fileName, String content, boolean time) {
        if (!log)
            return;
        write(fileName, (time ? lable() : "") + content + "\n", true);
    }

    /**
     * 写文件(追加)，换行，不显示时间戳
     *
     * @param fileName
     */
    public static void line(String fileName) {
        if (!log)
            return;
        line(fileName, "", false);
    }

    /**
     * 写文件，换行，是否开启时间戳
     *
     * @param fileName 文件名
     * @param list     内容列表
     * @param time     时间
     */
    public static void line(String fileName, List<String> list, boolean time) {
        if (!log)
            return;
        SdCard sdCard = new SdCard();
        try {
            sdCard.stepReady(dir + fileName);
            for (String content : list) {
                sdCard.stepWriteLine(time ? lable() : "" + content);
            }
            sdCard.stepClose();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件(追加)，换行，时间戳 【忽略开关】
     *
     * @param fileName
     * @param content
     */
    public static void force(String fileName, String content) {
        SdCard sdCard = new SdCard();
        try {
            sdCard.write(dir + fileName, content + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
