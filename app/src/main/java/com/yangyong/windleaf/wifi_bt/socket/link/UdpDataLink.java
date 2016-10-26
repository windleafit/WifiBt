package com.yangyong.windleaf.wifi_bt.socket.link;

import com.yangyong.windleaf.wifi_bt.socket.event.Event;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * dc.isConnected()：
 * 1.未连接时 dc.isConnected()为false。
 * 2.网络断开状态时：调用dc.setAddress(sa)，dc.isConnected()变为true。
 * 3.将网络断开后：dc.isConnected()仍然为true。
 * 4.调用dc.disconnect()，dc.isConnected()为false
 * <p/>
 * 总结：UDP连接上不一定真实，但是UDP断开dc.disconnect()则一定断开
 * <p/>
 * 连接上目标地址后，必须先发送数据，才能收到对方发送回来的数据。
 * 如果只连接，对方发送的数据是不能收到的。
 */
public class UdpDataLink extends DataLink {

    /**
     * 缓冲区容量
     */
    private static final int BUFFERSIZE = 512;
    /**
     * DatagramChannel
     */
    private DatagramChannel dc;
    /**
     * SocketAddress
     */
    private SocketAddress sa;
    /**
     * 连接命令
     */
    private boolean connectInvoke = false;
    /**
     * 断开命令
     */
    private boolean disconnectInvoke = false;
    /**
     * 接收通道中的全部数据
     * 原因：DatagramChannel
     * 如果数据报中的字节数大于给定缓冲区中的剩余空间，则丢弃余下的数据报。
     */
    private ByteBuffer channelBuffer;
    private int length = 0; //有效数据长度
    private byte[] buff = new byte[256];

    public UdpDataLink() {
//        EventBus.getDefault().post(new EventDebug("UdpDataLink"));
        channelBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
        channelBuffer.mark();
        channelBuffer.clear();
        try {
            dc = DatagramChannel.open();
            dc.configureBlocking(false);
            run();
        } catch (Exception e) {
            //
        }
    }

    @Override
    void SendBuffer(ByteBuffer buffer) throws IOException {
        dc.write(buffer);
    }

    /**
     * 先从channel中读取全部数据保存在自定义的channelBuffer中，再从channelBuffer中读取数据解析。
     *
     * @param buffer 用与接收数据的buffer
     * @throws IOException
     */
    @Override
    void RecvBuffer(ByteBuffer buffer) throws IOException {
        oldRecvBuffer(buffer);
    }

    @Override
    boolean InitProcess() throws IOException {
        if (disconnectInvoke) {
            disconnectInvoke = false;
//            connectInvoke = false;
            dc.disconnect();
            dc.close();
            fireEvent(Event.Disconnected, null); //UDP网络断开建立连接
        }

        if (connectInvoke) {
            connectInvoke = false;
            if (!dc.isOpen()) {
                dc = DatagramChannel.open();
                dc.configureBlocking(false);
            }
            if (!dc.isConnected()) {    //非真实连接
                dc.connect(sa); //如果已经连接，再次连接会报exception
//                isRealConnected = true;//不一定真实连接，所以不设置
                fireEvent(Event.Connected, null);//UDP网络建立连接，不代表真实已连接
            }
        }
//        EventBus.getDefault().post(new EventDebug("dc.isConnected() = " + dc.isConnected()));
        return dc.isConnected();
    }

    @Override
    boolean ExceptionProcess(Exception e) {
        fireEvent(Event.Exception, null);
        e.printStackTrace();
        try {
            e.printStackTrace();
            fireEvent(Event.Disconnected, null);
            dc.close();
        } catch (IOException ioe) {
        }
        return false;
    }

    /**
     * 网络是否连接
     * TCP代表实际连接，UDP只代表可以连接不代表实际连接
     */
    public boolean isConnected() {
        return dc.isConnected();
    }

    @Override
    public boolean isOpend() {
        return dc.isOpen();
    }

    /**
     * 关闭通道连接
     */
    public void disconnect() {
        disconnectInvoke = true;
    }

    /**
     * 连接
     *
     * @param sa 需要连接的地址
     */
    @Override
    public void connect(SocketAddress sa) {
        connectInvoke = true;
        this.sa = sa;
    }

    /**
     * 对象被清理后应该要断开连接
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (dc.isConnected()) {
            dc.close();
        }
    }

    /**
     * 先从channel中读取全部数据保存在自定义的channelBuffer中，再从channelBuffer中读取数据解析。
     *
     * @param buffer 用与接收数据的buffer
     * @throws IOException
     */
    void oldRecvBuffer(ByteBuffer buffer) throws IOException {

        //剩余空间小于256从通道读取数据
        if (length + 256 < BUFFERSIZE) {
            //从通道读取bytebuffer
            int readNum = dc.read(channelBuffer);
            if (readNum > 0) {
                //buffer有效长度
                length += readNum;
//                fireEvent(Event.Debug, "length = " + length + "; readNum = " + readNum + "; 时刻 -> " + System.currentTimeMillis() % 100000 + "\n");
            }
        }

        //需要复制的数据个数
        int needNum = buffer.remaining();

        //判断需要读取的数据是否足够
        if (needNum > length)
            return;

//        fireEvent(Event.Debug, String.valueOf(length));
//        fireEvent(Event.Debug, " 时刻 -> " + System.currentTimeMillis() % 100000 + "\n");

        //准备读取 pos=0
        channelBuffer.clear();

        //读取
        channelBuffer.get(buff, 0, needNum);
        buffer.put(buff, 0, needNum);

        length -= needNum;

        //压缩数据 pos=有效数据
        channelBuffer.compact();

        //重置pos位置到有效数据末尾
        channelBuffer.position(length);
    }

}
