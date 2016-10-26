package com.yangyong.windleaf.wifi_bt.socket.link;

import android.os.CountDownTimer;

import com.yangyong.windleaf.wifi_bt.socket.event.Event;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.yangyong.kity.java.event.EventPublisher;
import me.yangyong.kity.java.thread.ThreadLoop;

/**
 * DataLink 代表一个数据链路，其上行（Tx）链路接收Packet对象并解包，通过具体的接口进行发送
 * 下行（Rx）链路通过具体的接口进行接收，并输出Packet流（以事件的形式）。
 * 子类需要实现具体的发送接收接口：
 * void SendBuffer(ByteBuffer buffer)
 * void RecvBuffer(ByteBuffer buffer)
 */
public abstract class DataLink extends EventPublisher {

    private static final int SLEEP = 50;

    /**
     * 发送Packet队列
     */
    private final Queue<ByteBuffer> TxPacketQueue;
    /**
     * 该DataLink的工作线程
     */

    private final ThreadLoop mThreadLoop;
    /**
     * 发送的buffer
     */
    private ByteBuffer mSendBuffer;
    /**
     * 接收的buffer
     */
    private ByteBuffer mRecvBuffer;

    /**
     * 构造器，进行一些初始化（发送队列，重发定时器，当前发送/接收包，工作线程）
     */
    public DataLink() {
//        EventBus.getDefault().register(this);

        TxPacketQueue = new ConcurrentLinkedQueue<>();

        mRecvBuffer = ByteBuffer.allocate(256);
        mRecvBuffer.clear();
        mRecvBuffer.mark();
        mRecvBuffer.limit(1);

        mThreadLoop = new ThreadLoop(new ThreadLoop.Callback() {
            @Override
            public void loop() {
                try {
                    if (InitProcess()) {
//                        EventBus.getDefault().post(new EventDebug("InitProcess success " + Time.millis()));
                        SendProcess();
                        if (!ReceiveProcess())
                            Thread.sleep(SLEEP);
                    } else {
//                        EventBus.getDefault().post(new EventDebug("InitProcess failed " + Time.millis()));
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    ExceptionProcess(e);
                }
            }
        });
    }

    /**
     * 发送数据包线程
     *
     * @throws IOException
     */
    private void SendProcess() throws IOException {
        if (TxPacketQueue.isEmpty())
            return;

        mSendBuffer = TxPacketQueue.poll();
        mSendBuffer.rewind();

        SendBuffer(mSendBuffer); //non-blocking
    }

    /**
     * 直接发送buffer内的数据
     *
     * @param buffer 需要发送的buffer
     * @throws IOException
     */
    abstract void SendBuffer(ByteBuffer buffer) throws IOException;

    /**
     * 接收数据包
     *
     * @return true 接收到有效数据
     * false 接收到无效数据
     * @throws IOException
     */
    private boolean ReceiveProcess() throws IOException {
        if (mRecvBuffer != null) {
            RecvBuffer(mRecvBuffer);
            return fireEvent(Event.RecvBuffer, mRecvBuffer);
        }
        return false;
    }

    /**
     * 直接把数据接收到buffer中
     *
     * @param buffer 用与接收数据的buffer
     * @throws IOException
     */
    abstract void RecvBuffer(ByteBuffer buffer) throws IOException;

    /**
     * 用与进行收发前的准备工作，只有返回true才能进行收发
     *
     * @return true - 表示能进行收发，false - 表示不能进行收发
     * @throws IOException
     */
    abstract boolean InitProcess() throws IOException;

    /**
     * 异常处理
     *
     * @param e
     * @return
     */
    abstract boolean ExceptionProcess(Exception e);

    public ByteBuffer geSendBuffer() {
        return mSendBuffer;
    }

    public ByteBuffer getRecvBuffer() {
        return mRecvBuffer;
    }

    /**
     * 设置接收缓冲区
     *
     * @param mRecvBuffer
     */
    public void setRecvBuffer(ByteBuffer mRecvBuffer) {
        this.mRecvBuffer = mRecvBuffer;
    }

//    @Subscribe
//    public void onEventBus(Event<DatalinkEvent, ByteBuffer> event) {
//        if (event.getEvent() == DatalinkEvent.SenderBuffer) {
//            setSendBuffer(event.getMessage());
//        }
//    }

    /**
     * 发送消息
     *
     * @param buffer
     */
    public void setSendBuffer(ByteBuffer buffer) {
        if (isConnected()) {
            TxPacketQueue.offer(buffer);
        }
    }

    /**
     * 是否连接
     */
    public abstract boolean isConnected();

    /**
     * 通道是否打开
     */
    public abstract boolean isOpend();


    /**
     * 开始运行工作线程
     */
    public void run() {
        mThreadLoop.start();
    }

    /**
     * 工作线程是否在运行
     *
     * @return isRun
     */
    public boolean isRunning() {
        return mThreadLoop.isRunning();
    }

    /**
     * 连接网络
     *
     * @param sa
     */
    public abstract void connect(SocketAddress sa);

    /**
     * 对象回收后，关闭工作线程
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    /**
     * 关闭资源
     */
    public void destroy() {
        //关闭通道
        try {
            disconnect();
            //InitProcess让disconnect();方法生效
            //执行通道的close()操作
            InitProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭线程
        mThreadLoop.stop();
        //清空队列
        TxPacketQueue.clear();

        mSendBuffer = null;
        mRecvBuffer = null;
    }

    /**
     * 断开连接
     */
    public abstract void disconnect();

    /**
     * 清除资源
     */
    public void clear() {
        mThreadLoop.stop();
    }

    /**
     * 用与重发的定时器类
     */
    class RetryTimer extends CountDownTimer {

        public boolean isOverRetry = false;
        public boolean isRetry = false;

        private boolean isFirstTick = true; //第一次进入OnTick不进行重发

        /**
         * 构造一个指定定时长度的重发定时器
         *
         * @param timeout 超时时间
         */
        public RetryTimer(long timeout, int retry_number) {
            super(timeout * (retry_number + 1), timeout);
        }

        /**
         * @param millisUntilFinished Not Use
         */
        @Override
        public void onTick(long millisUntilFinished) {
            if (!isFirstTick) {
                isRetry = true;
            } else {
                isFirstTick = false;
            }
        }

        /**
         * 定时超时后，进行重发
         */
        @Override
        public void onFinish() {
            isOverRetry = true;
        }


        /**
         * 重启定时器，并阻止发送队列继续发送
         */
        public void restart() {
            //canSendNext = false;
            isFirstTick = true;
            isRetry = false;
            isOverRetry = false;
            start();
        }

    }
}
