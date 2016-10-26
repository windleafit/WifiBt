package me.yangyong.kity.java.bean;

/**
 * 两次执行时间间隔必须大于设定时间
 */
public class SlowRun {

    private int interval;//最小间隔时间
    private long last;//上次执行时间

    /**
     * @param interval 两次执行最小间隔时间
     */
    public SlowRun(int interval) {
        this.interval = interval;
        last = System.currentTimeMillis();
    }

    /**
     * 执行
     *
     * @param runnable 执行的代码，无论调用频率多块，两次执行时间必定大于periond
     */
    public void execute(Runnable runnable) {
        long current = System.currentTimeMillis();
        //时间间隔大于设定值
        if (current - last > interval) {
            last = current;
            runnable.run();
        }
    }

}
