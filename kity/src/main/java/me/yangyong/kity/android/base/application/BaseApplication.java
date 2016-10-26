package me.yangyong.kity.android.base.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import me.yangyong.kity.android.log.CatLog;
import me.yangyong.kity.android.log.SdLog;
import me.yangyong.kity.android.toast.Toasty;


public abstract class BaseApplication extends Application {

    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;

    private boolean isLog = false;

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        log("onCreate");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        Toasty.holder.init(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        log("onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        log("onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        log("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        log("onTrimMemory");
    }

    private void log(String msg) {
        if (isLog) {
            //终端Log
            CatLog.d(getClass().getSimpleName(), msg);
            //文件Log
            SdLog.line("Life.txt", getClass().getSimpleName() + " -> " + msg, true);
        }
    }

    /**
     * 日志
     *
     * @param tag
     * @param msg
     */
    public void log(String tag, String msg) {
        if (isLog) {
            //终端Log
            CatLog.d(tag, msg);
            //文件Log
            SdLog.line("Life.txt", tag + " -> " + msg, true);
        }
        //getLocalClassName()
    }
}
