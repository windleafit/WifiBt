package com.yangyong.windleaf.wifi_bt.socket.link;


import com.yangyong.windleaf.wifi_bt.socket.event.Event;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Socket（TCP）数据链路
 */
public class TcpDataLink extends DataLink {

    /**
     * SocketChannel
     */
    private SocketChannel sc;
    /**
     * SocketAddress
     */
    private SocketAddress sa;

    /**
     * 连接命令
     */
    private boolean connectInvoke = false;
    private boolean disconnectInvoke = false;

    /**
     * 构造一个Socket数据链，并开始运行
     *
     * @throws IOException
     */
    public TcpDataLink() {
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.socket().setKeepAlive(false);
            sc.socket().setTcpNoDelay(true);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送buffer的数据，non-blocking
     *
     * @param buffer 需要发送的数据buffer
     * @throws IOException
     */
    @Override
    void SendBuffer(ByteBuffer buffer) throws IOException {
        sc.write(buffer);
    }

    /**
     * 接收数据到buffer，non-blocking
     *
     * @param buffer 接收数据的buffer
     * @throws IOException
     */
    @Override
    void RecvBuffer(ByteBuffer buffer) throws IOException {
        sc.read(buffer);
    }

    /**
     * 初始化处理工作
     *
     * @return true - 表示初始化成功，可以进行收发工作；false - 表示初始化不成功，不能进行收发工作
     * @throws IOException
     */
    @Override
    boolean InitProcess() throws IOException {

        if (disconnectInvoke) {
            disconnectInvoke = false;
            connectInvoke = false;
            sc.close();
            fireEvent(Event.Disconnected, null);
        }

        if (connectInvoke) {
            connectInvoke = false;
            if (!sc.isOpen()) {
                sc = SocketChannel.open();
                sc.configureBlocking(false);
                sc.socket().setKeepAlive(false);
                sc.socket().setTcpNoDelay(true);
            }

            if (!sc.isConnected()) {
                sc.connect(sa);
            }
        }

        if (sc.isConnectionPending()) {
            sc.finishConnect();
            if (sc.isConnected()) {
                fireEvent(Event.Connected, null);   //TCP真实连接
            }
        }

        return sc.isConnected();
    }

    @Override
    boolean ExceptionProcess(Exception e) {
        fireEvent(Event.Exception, null);
        e.printStackTrace();
        //说明连接出现错误，不管是什么错误，都关闭通道
        try {
            fireEvent(Event.Disconnected, null);
            sc.close();
        } catch (IOException ioe) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnected() {
        return sc.isConnected();
    }

    @Override
    public boolean isOpend() {
        return sc.isOpen();
    }

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
        this.sa = sa;
        connectInvoke = true;
    }

    /**
     * 对象被清理后应该要断开连接
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (sc.isConnected()) {
            sc.close();
        }
    }


}
