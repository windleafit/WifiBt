package me.yangyong.kity.java.judge;

/**
 * 布尔值判断
 */
public final class Bool {

    /**
     * 所有元素为true
     *
     * @param bools
     * @return
     */
    public static boolean allTrue(Boolean... bools) {
        return Element.all(true, bools);
    }

    /**
     * 所有元素为false
     *
     * @param bools
     * @return
     */
    public static boolean allFalse(Boolean... bools) {
        return Element.all(false, bools);
    }


    /**
     * 至少有1个元素为真
     *
     * @param bools
     * @return
     */
    public static boolean containTrue(Boolean... bools) {
        return Element.contain(true, bools);
    }

    /**
     * 至少有1个元素为假
     *
     * @param bools
     * @return
     */
    public static boolean containFalse(Boolean... bools) {
        return Element.contain(false, bools);
    }

}
