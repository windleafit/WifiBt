package me.yangyong.kity.java.bit;

/**
 * 传值
 * <p/>
 * 优先级：高到低
 * 运算符	结合性
 * [ ] . ( ) (方法调用)
 * 　　	从左向右
 * <p/>
 * ! ~ ++ -- +(一元运算) -(一元运算)
 * 　　	从右向左
 * / %
 * 　　	从左向右
 * + -
 * 　　	从左向右
 * << >> >>>
 * 　　	从左向右
 * < <= > >= instanceof
 * 　　	从左向右
 * == !=
 * 　　	从左向右
 * &
 * 　　	从左向右
 * ^
 * 　　	从左向右
 * |
 * 　　	从左向右
 * &&
 * 　　	从左向右
 * ||
 * 　　	从左向右
 * ?:
 * 　　	从右向左
 * <p/>
 * = += -= *= /= %= &= |= ^= <<= >>= >>=
 * 从右向左
 */
public class Bit {

    /**
     * 将 [high~low] 之间的位置1
     *
     * @param value
     * @param highBit
     * @param lowBit
     * @return
     */
    public static int write(int value, int highBit, int lowBit) {
        value = value | Mark.create(highBit, lowBit);
        return value;
    }

    /**
     * 将bit位置1
     *
     * @param value
     * @param bit
     * @return
     */
    public static int write(int value, int bit) {
        value = value | (1 << bit);
        return value;
    }

    /**
     * 将 [high~low] 之间的位清0
     *
     * @param value
     * @param highBit
     * @param lowBit
     * @return
     */
    public static int clear(int value, int highBit, int lowBit) {
        value = value & ~Mark.create(highBit, lowBit);
        return value;
    }

    /**
     * 将bit位清0
     *
     * @param value
     * @param bit
     * @return
     */
    public static int clear(int value, int bit) {
        value = value & ~(1 << bit);
        return value;
    }

    /**
     * 交换位
     *
     * @param value
     * @param bit1
     * @param bit2
     */
    public static int exchange(int value, int bit1, int bit2) {
        boolean b1 = (Mark.bit(value, bit1) > 0);
        boolean b2 = (Mark.bit(value, bit2) > 0);
        if (b1 != b2) {
            value = b1 ? write(value, bit2) : clear(value, bit2);
            value = b2 ? write(value, bit1) : clear(value, bit1);
        }
        return value;
    }
}
