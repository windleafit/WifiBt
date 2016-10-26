package com.yangyong.windleaf.wifi_bt.socket.event;

/**
 * 事件定义
 */
public enum Event {

    PacketReceived,

    PacketReceiveTimeout,

    PacketTransmitError,

    PacketOverFlow,

    PacketTransmitCompleted,

    /**
     * Aircraft：数据通信正常
     * DataLink：Tcp网络已连接，Udp网络已建立连接可发送消息
     */
    Connected,
    /**
     * Aircraft：数据通信中断
     * DataLink：Tcp网络已断开，Udp网络已断开建立的连接
     */
    Disconnected,
    /**
     * DataLink：连接中 x
     */
    Connecting,

    /**
     * Aircraft：正在重连
     */
    ReConnecting,

    /**
     * Aircraft：网络已连接
     */
    LinkConnected,
    /**
     * Aircraft：网络断开连接
     */
    LinkDisconnected,

    /**
     * 遥测数据更新事件
     */
    TelemetryUpdated,

    PacketReceiveError,

    DataLinkIdle,

    DataLinkInactive,

    WayPoint,


    /**
     * 飞控遥测数据
     */
    HiResGps,
    HiRes,
    SystemStatus,
    /**
     * 服务器GPRS数据
     */
    GPRS,


    RecvData,
    SenderData,

    //解码收到的数据包
    DecodeStationPacket,
    DecodeWebPacket,

    //处理收到的数据包
    DealStationPacket,
    DealWebPacket,

    EncodeStationPacket,
    EncodeWebPacket,

    RecvBuffer,
    SenderBuffer,

    EncodePacket,
    DecodePacket,

    //Web服务器相关

    //网络响应
    WebResponse,
    //业务消息
    WebBusinessMessage,

    //出错
    Exception,


}
