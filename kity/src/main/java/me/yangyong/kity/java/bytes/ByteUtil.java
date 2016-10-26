package me.yangyong.kity.java.bytes;

import java.util.Arrays;

/**
 * byte数组
 */
public class ByteUtil {

    /**
     * 转换为无符号整形，不考虑正负数（10进制）
     *
     * @param b
     * @return
     */
    public static int decUnsigned(byte b) {
        return b & 0xff;
    }

    /**
     * 转换为有符号整形(无需转，默认就是)（10进制）
     *
     * @param b
     * @return
     */
    public static int decSigned(byte b) {
        return b;
    }


    /**
     * 转换为无符号数（16进制）
     *
     * @param b
     * @return
     */
    public static String hexUnsignedString(byte b) {
        return String.format("%02x", decUnsigned(b));
    }

    /**
     * 转换为无符号数（10进制）
     *
     * @param b
     * @return
     */
    public static String decUnsignedString(byte b) {
        return String.valueOf(decUnsigned(b));
    }

    /**
     * 转换为有符号数（16进制）
     *
     * @param b
     * @return
     */
    public static String decSignedString(byte b) {
        return String.valueOf(b);
    }


    /**
     * 数组转换
     *
     * @param array
     * @return
     */
    public static int[] toIntArray(byte[] array) {
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i] & 0xff;
        }
        return intArray;
    }

    /**
     * 数组转换
     *
     * @param array
     * @return
     */
    public static String[] toDecStringArray(byte[] array) {
        String[] intArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = decUnsignedString(array[i]);
        }
        return intArray;
    }

    /**
     * 数组转换
     *
     * @param array
     * @return
     */
    public static String[] toHexStringArray(byte[] array) {
        String[] hexArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            hexArray[i] = hexUnsignedString(array[i]);
        }
        return hexArray;
    }

    /**
     * 数组转换
     *
     * @param array
     * @return
     */
    public static String[] toDecSignedStringArray(byte[] array) {
        String[] intArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = decSignedString(array[i]);
        }
        return intArray;
    }

    /**
     * 数组转换
     *
     * @param array
     * @return
     */
    public static String[] toHexSignedStringArray(byte[] array) {
        String[] hexArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            hexArray[i] = String.format("%02x", array[i]);
        }
        return hexArray;
    }


    /**
     * 打印数组字符串（10进制）
     * 例子：[170, 68, 24, 32, 132, 231, 0, 0, 126, 195]
     *
     * @param array
     * @return
     */
    public static String printDecArrayString(byte[] array) {
        return Arrays.toString(ByteUtil.toIntArray(array));
    }

    /**
     * 打印数组字符串（16进制）
     * 例子：[aa, 44, 18, 20, 84, e7, 00, 00, 7e, c3]
     *
     * @param array
     * @return
     */
    public static String printHexArrayString(byte[] array) {
        return Arrays.toString(ByteUtil.toHexStringArray(array));
    }

    /**
     * 打印数组
     * 例子：0x44,0x18,0x0b,0x00
     *
     * @param array
     * @return
     */
    public static String printHexWith0x(byte[] array) {
        return print(array, true, true, ",");
    }

    /**
     * 打印数组
     * 例子：44 18 0b 00 38
     *
     * @param array
     * @return
     */
    public static String printHexWithSpace(byte[] array) {
        return print(array, true, false, " ");
    }

    /**
     * 打印数组
     * 例子：32,97,210,0,0,0,0
     *
     * @param array
     * @return
     */
    public static String printDecWithSemi(byte[] array) {
        return print(array, false, false, ",");
    }

    /**
     * 打印数组
     * 例子：32 97 210 0 0 0 0
     *
     * @param array
     * @return
     */
    public static String printDecWithSpace(byte[] array) {
        return print(array, false, false, " ");
    }

    /**
     * 打印数组
     *
     * @param array
     * @param hex
     * @param Ox
     * @param separator
     * @return
     */
    public static String print(byte[] array, boolean hex, boolean Ox, String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (hex) {
                if (Ox)
                    builder.append("0x");
                builder.append(hexUnsignedString(array[i]));
            } else {
                builder.append(decUnsignedString(array[i]));
            }
            if (i != array.length - 1)
                builder.append(separator);
        }
        return builder.toString();
    }


    public static void main(String[] args) {
        byte b = -1;
        System.out.println(decSignedString(b));
        System.out.println(decUnsigned(b));
        System.out.println(decUnsignedString((byte) 0xff));
        System.out.println(toByte("-4"));
        int a = 255;
        System.out.println(toByte(a));
    }


    /**
     * 将16进制格式的String转为byte
     * <p/>
     * 支持10进制和16进制等格式(16进制必须为标准格式，有前缀0x)
     * 支持有符号的-128~127的byte类型，或者无符号的0~255的int类型
     * 溢出部分会被截掉
     *
     * @param value
     * @return
     */
    public static byte toByte(String value) {
        return Integer.decode(value).byteValue();
    }

    /**
     * 将int转为byte
     *
     * @param value
     * @return
     */
    public static byte toByte(int value) {
        return (byte) value;
    }

    /**
     * 将String[]转为byte[]
     *
     * @param array
     * @return
     */
    public static byte[] toByteArray(String[] array) {
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = toByte(array[i]);
        }
        return bytes;
    }

    /**
     * 将int[]转为byte[]
     *
     * @param array
     * @return
     */
    public static byte[] toByteArray(int[] array) {
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = toByte(array[i]);
        }
        return bytes;
    }

    /**
     * 将String转为byte[]
     *
     * @param value
     * @return
     */
    public static byte[] toByteArray(String value) {
        return toByteArray(toByteStringArray(value));
    }

    /**
     * 将String转为String[]
     *
     * @param value
     * @return
     */
    public static String[] toByteStringArray(String value) {
        return value.toLowerCase()
                .replace("0x", " ")
                .replace(",", " ")
                .trim()
                .split(" +");
    }

    /**
     * 将String转为byte[]
     *
     * @param value
     * @return
     */
    public static byte[] toByteArrayFromHexWithout0x(String value) {
        return toByteArray(toByteStringArrayFromHexWithout0x(value));
    }

    /**
     * 将String转为String[]
     *
     * @param value
     * @return
     */
    public static String[] toByteStringArrayFromHexWithout0x(String value) {
        String str = value.toLowerCase()
                .replace(",", " ")
                .trim();
        String[] bytes = str.split(" +");
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i].contains("-")) {
                StringBuilder buffer = new StringBuilder(bytes[i]);
                buffer.insert(1, "0x");
                bytes[i] = buffer.toString();
            } else {
                bytes[i] = "0x" + bytes[i];
            }
        }
        return bytes;
    }


}
