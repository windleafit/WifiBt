package com.yangyong.windleaf.wifi_bt.volley.json.request;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class RegisterChildBean {

    /**
     * device :
     * account :
     * pwd :
     * child :
     * cpwd :
     */

    private String device;
    private String account;
    private String pwd;
    private String child;
    private String cpwd;

    public RegisterChildBean() {
    }

    public RegisterChildBean(String device, String account, String pwd, String child, String cpwd) {
        this.device = device;
        this.account = account;
        this.pwd = pwd;
        this.child = child;
        this.cpwd = cpwd;
    }

    public void setDevice(String device) { this.device = device;}

    public void setAccount(String account) { this.account = account;}

    public void setPwd(String pwd) { this.pwd = pwd;}

    public void setChild(String child) { this.child = child;}

    public void setCpwd(String cpwd) { this.cpwd = cpwd;}

    public String getDevice() { return device;}

    public String getAccount() { return account;}

    public String getPwd() { return pwd;}

    public String getChild() { return child;}

    public String getCpwd() { return cpwd;}
}
