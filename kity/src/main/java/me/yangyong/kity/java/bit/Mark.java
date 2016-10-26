package me.yangyong.kity.java.bit;

/**
 * 掩码操作类
 * <p/>
 * 低位从0开始
 * 例如
 * int类型的bit位：0位~31位
 * <p/>
 * 例子：
 * 想要获取掩码：0x1c = 0b 0001 1100
 * 调用函数 create(4,2) 即可
 */
public class Mark {

    /**
     * 左移
     *
     * @param value
     * @param length
     * @return
     */
    public static int right(int value, int length) {
        return value >> length;
    }

    /**
     * 右移
     *
     * @param value
     * @param length
     * @return
     */
    public static int left(int value, int length) {
        return value << length;
    }

    /**
     * 获得 [位highBit ~ 位lowBit] 之间位置1的掩码值
     * * <p/>
     * 例子：
     * create(2,0) -> 0b0111
     * create(7,4) -> 0xf0
     *
     * @param highBit 高bit位
     * @param lowBit  低bit位
     * @return
     */
    public static int create(int highBit, int lowBit) {
        if (highBit < lowBit || lowBit < 0)
            return 0;
        return (1 << (highBit + 1)) - (1 << lowBit);
    }


    /**
     * 获得低length长的位置1的掩码值
     * <p/>
     * 例子：
     * create(1) -> 0b0001
     * create(3) -> 0b0111
     * create(8) -> 0xff
     *
     * @param length 掩码位个数
     * @return
     */
    public static int create(int length) {
        if (length < 0)
            return 0;
        return 1 << length - 1;
    }


    /**
     * 获取value的mark值
     * <p/>
     * 例子：range(0b1110,3,2) -> 0b1100
     *
     * @param value   被屏蔽值
     * @param highBit 高bit位
     * @param lowBit  低bit位
     * @return
     */
    public static int range(int value, int highBit, int lowBit) {
        return value & create(highBit, lowBit);
    }

    /**
     * 获取value的低length位mark值
     * <p/>
     * 例子：range(0b1110,2) -> 0b0010
     *
     * @param value
     * @param length 掩码位个数
     * @return
     */
    public static int length(int value, int length) {
        return value & create(length);
    }

    /**
     * 获取value的mark值并右移lowBit位
     * <p/>
     * 例子：value(0b1110,3,2) -> 0b0011
     *
     * @param value   被屏蔽值
     * @param highBit 高bit位
     * @param lowBit  低bit位
     * @return
     */
    public static int value(int value, int highBit, int lowBit) {
        return range(value, highBit, lowBit) >> lowBit;
    }

    /**
     * 获取第bit位为1的mark与value后的值
     * * <p/>
     * 例子：bit(0b1110,2) -> 0b0100
     *
     * @param value
     * @param bit
     * @return
     */
    public static int bit(int value, int bit) {
        return value & (1 << bit);
    }

}
