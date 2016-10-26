package com.yangyong.windleaf.wifi_bt.json;

/**
 * Created by YangYong on 2016/8/12 0012.
 */
public class Address {

    private String road;
    private String streate;
    private String provience;
    private String no;
    public String getRoad() {
        return road;
    }
    public void setRoad(String road) {
        this.road = road;
    }
    public String getStreate() {
        return streate;
    }
    public void setStreate(String streate) {
        this.streate = streate;
    }
    public String getProvience() {
        return provience;
    }
    public void setProvience(String provience) {
        this.provience = provience;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Address{" +
                "road='" + road + '\'' +
                ", streate='" + streate + '\'' +
                ", provience='" + provience + '\'' +
                ", no='" + no + '\'' +
                '}';
    }
}
