package me.yangyong.kity.java.judge;

/**
 * Created by JOUAV on 2015/12/24.
 */
@Deprecated
public class Compare {

    /**
     * value与多项数据对比，有一项相等为true
     *
     * @param value
     * @param values
     * @return
     */
    @SafeVarargs
    public static <T> boolean equal(T value, T... values) {
        return Element.contain(value, values);
    }

}
