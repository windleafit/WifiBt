package me.yangyong.kity.java.thread;

/**
 * 循环线程
 */
public class ThreadLoop {

    private Thread mThread;
    private Runnable mRunnable;

    private boolean isRunning;

    /**
     * 循环线程
     *
     * @param callback
     */
    public ThreadLoop(Callback callback) {
        init(callback);
    }

    /**
     * 初始化
     *
     * @param callback
     */
    private void init(final Callback callback) {
        isRunning = true;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    callback.loop();
                }
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
     * 线程是否正在运行
     *
     * @return
     */
    public boolean isRunning() {
        return isRunning;
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
        isRunning = false;
        mThread.interrupt();
    }

    public void interrupt() {
        mThread.interrupt();
    }

    /**
     * 接口
     */
    public interface Callback {
        void loop();
    }
}
