package com.yangyong.windleaf.wifi_bt.volley.json.response;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class ReceivedBean {

    /**
     * protocol : 0
     * ret : true
     * power : 0
     */

    private int protocol;
    private boolean ret;
    private int power;

    public ReceivedBean() {
    }

    public ReceivedBean(int protocol, boolean ret, int power) {
        this.protocol = protocol;
        this.ret = ret;
        this.power = power;
    }

    public int getProtocol() { return protocol;}

    public void setProtocol(int protocol) { this.protocol = protocol;}

    public boolean getRet() { return ret;}

    public void setRet(boolean ret) { this.ret = ret;}

    public int getPower() { return power;}

    public void setPower(int power) { this.power = power;}

    @Override
    public String toString() {
        return "ReceivedBean{" +
                "power=" + power +
                ", protocol=" + protocol +
                ", ret=" + ret +
                '}';
    }
}
