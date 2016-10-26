package me.yangyong.kity.java.thread;

/**
 * 线程模板
 */
public class Thready {

    private Thread mThread;
    private Runnable mRunnable;

    /**
     * 线程模板
     *
     * @param callback
     */
    public Thready(Callback callback) {
        init(callback);
    }

    /**
     * 初始化
     *
     * @param callback
     */
    private void init(final Callback callback) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                callback.once();
            }
        };
        mThread = new Thread(mRunnable);
    }


    /**
     * 启动线程
     */
    public void start() {
        mThread.start();
    }

    /**
     * 对象回收后，关闭工作线程
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        stop();
        super.finalize();
    }

    public void stop() {
        mThread.interrupt();
    }

    /**
     * 接口
     */
    public interface Callback {
        void once();
    }
}
