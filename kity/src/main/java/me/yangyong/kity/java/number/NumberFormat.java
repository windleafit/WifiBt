package me.yangyong.kity.java.number;


public class NumberFormat {

    /**
     * 保留1位小数
     */
    public static String point1(double number) {
        return String.format("%.1f", number);
    }

    /**
     * 保留2位小数
     */
    public static String point2(double number) {
        return String.format("%.2f", number);
    }

    /**
     * 保留3位小数
     */
    public static String point3(double number) {
        return String.format("%.3f", number);
    }

    /**
     * 保留4位小数
     */
    public static String point4(double number) {
        return String.format("%.4f", number);
    }

    public static String point6(double number) {
        return String.format("%.6f", number);
    }

    public static String point8(double number) {
        return String.format("%.8f", number);
    }


    /**
     * 保留1位小数
     */
    public static String point1(float number) {
        return String.format("%.1f", number);
    }

    /**
     * 保留2位小数
     */
    public static String point2(float number) {
        return String.format("%.2f", number);
    }

    /**
     * 保留3位小数
     */
    public static String point3(float number) {
        return String.format("%.3f", number);
    }

    /**
     * 保留4位小数
     */
    public static String point4(float number) {
        return String.format("%.4f", number);
    }

    public static String point6(float number) {
        return String.format("%.6f", number);
    }

    public static String point8(float number) {
        return String.format("%.8f", number);
    }

}
