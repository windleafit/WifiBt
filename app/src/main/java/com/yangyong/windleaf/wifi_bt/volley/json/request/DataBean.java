package com.yangyong.windleaf.wifi_bt.volley.json.request;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class DataBean {


    /**
     * device :
     * account :
     * pwd :
     * remote : false
     * monitor : false
     * alarm : false
     */

    private String device;
    private String account;
    private String pwd;
    private boolean remote;
    private boolean monitor;
    private boolean alarm;

    public DataBean() {
    }

    public DataBean(String device, String account, String pwd, boolean remote, boolean monitor, boolean alarm) {
        this.device = device;
        this.account = account;
        this.pwd = pwd;
        this.remote = remote;
        this.monitor = monitor;
        this.alarm = alarm;
    }

    public String getDevice() { return device;}

    public void setDevice(String device) { this.device = device;}

    public String getAccount() { return account;}

    public void setAccount(String account) { this.account = account;}

    public String getPwd() { return pwd;}

    public void setPwd(String pwd) { this.pwd = pwd;}

    public boolean getRemote() { return remote;}

    public void setRemote(boolean remote) { this.remote = remote;}

    public boolean getMonitor() { return monitor;}

    public void setMonitor(boolean monitor) { this.monitor = monitor;}

    public boolean getAlarm() { return alarm;}

    public void setAlarm(boolean alarm) { this.alarm = alarm;}
}
