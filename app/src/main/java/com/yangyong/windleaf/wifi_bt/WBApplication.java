package com.yangyong.windleaf.wifi_bt;


import me.yangyong.kity.android.base.application.BaseApplication;
import me.yangyong.kity.android.toast.Toasty;

/**
 * Created by JOUAV on 2015/9/11.
 */
public class WBApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        //使用ToastManager代替Toast调用
        Toasty.holder.init(this);

    }

}
