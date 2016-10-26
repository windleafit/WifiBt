package me.yangyong.kity.java;

/**
 * 类型转换
 */
public class Cast {

    /**
     * <pre>
     * 在Java中，经常会将一个Object类型转成自己想要的Map、List等等。通常的做法是：
     *
     * Object obj = ....;
     * Map<String, String> castMap = (HashMap<String, String>) obj;
     * 在这里会产生unchecked cast warning，有代码洁癖的就会想办法干掉它。解决办法就是在方法上添加一个注解@SuppressWarnings("unchecked") ,比较优雅的方案就是提供一个工具类，然后写个专门cast的方法来做这个工作。
     *
     * @param o
     * @param <T>
     * @return
     * @SuppressWarnings("unchecked")
     * public static <T> T cast(Object obj) {
     * return (T) obj;
     * }
     * 于是乎上面的就可以这么解决了：
     * <p/>
     * Object obj = ....;
     * Map<String, String> castMap = cast(obj); //import static method
     * 更健壮一点的需要处理一下这里会抛出的ClassCastException，打个warn日志干个啥的都OK。
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> T to(Object o) {
        return (T) o;
    }

}
