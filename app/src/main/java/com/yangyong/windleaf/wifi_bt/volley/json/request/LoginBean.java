package com.yangyong.windleaf.wifi_bt.volley.json.request;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class LoginBean {

    /**
     * device :
     * account :
     * pwd :
     */

    private String device;
    private String account;
    private String pwd;

    public LoginBean(String device, String account, String pwd) {
        this.device = device;
        this.account = account;
        this.pwd = pwd;
    }

    public LoginBean() {
    }

    public String getDevice() { return device;}

    public void setDevice(String device) { this.device = device;}

    public String getAccount() { return account;}

    public void setAccount(String account) { this.account = account;}

    public String getPwd() { return pwd;}

    public void setPwd(String pwd) { this.pwd = pwd;}
}
