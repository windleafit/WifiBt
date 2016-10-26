package com.yangyong.windleaf.wifi_bt.volley.json.request;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class DeleteAccountBean {

    /**
     * device :
     * account :
     * pwd :
     * child :
     */

    private String device;
    private String account;
    private String pwd;
    private String child;

    public DeleteAccountBean() {
    }

    public DeleteAccountBean(String device, String account, String pwd, String child) {
        this.device = device;
        this.account = account;
        this.pwd = pwd;
        this.child = child;
    }

    public void setDevice(String device) { this.device = device;}

    public void setAccount(String account) { this.account = account;}

    public void setPwd(String pwd) { this.pwd = pwd;}

    public void setChild(String child) { this.child = child;}

    public String getDevice() { return device;}

    public String getAccount() { return account;}

    public String getPwd() { return pwd;}

    public String getChild() { return child;}
}
