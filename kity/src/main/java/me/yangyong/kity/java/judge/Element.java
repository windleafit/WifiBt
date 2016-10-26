package me.yangyong.kity.java.judge;

/**
 * 元素判断
 */
public final class Element {

    private Element() {

    }

    /**
     * 集合里是否包含element值
     *
     * @param elements
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> boolean contain(E element, E... elements) {
        for (E e : elements) {
            if (element instanceof String) {
                if (element.equals(e))
                    return true;
            } else {
                if (element == e)
                    return true;
            }
        }
        return false;
    }

    /**
     * 所有元素都与element相同
     *
     * @param element
     * @param elements
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> boolean all(E element, E... elements) {
        for (E e : elements) {
            if (element instanceof String) {
                if (!element.equals(e))
                    return false;
            } else {
                if (element != e)
                    return false;
            }
        }
        return true;
    }
}
