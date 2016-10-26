package me.yangyong.kity.android.base.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import me.yangyong.kity.android.base.ActivityController;
import me.yangyong.kity.android.log.CatLog;
import me.yangyong.kity.android.log.SdLog;


public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected SharedPreferences mSharedPreferences;
    protected SharedPreferences.Editor mEditor;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        ActivityController.addActivity(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
        if (isFinishing())
            releaseResource();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
        ActivityController.removeActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 释放资源
     */
    protected abstract void releaseResource();

    /**
     * 日志
     *
     * @param msg
     */
    private void log(String msg) {
        CatLog.d(getClass().getSimpleName(), msg);
        SdLog.line("Life.txt", getClass().getSimpleName() + " -> " + msg, true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log("onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        log("onSaveInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //是否执行双击退出
        if (onKeyDownExitDoubleClickEnable()) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    //再按一次后退键退出程序
                    onKeyDownExitToast();
                    exitTime = System.currentTimeMillis();
                } else {
                    //退出代码
                    onKeyDownExiting();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    /**
     * 退出程序的代码
     */
    protected abstract void onKeyDownExiting();

    /**
     * 双击退出的提示
     */
    protected abstract void onKeyDownExitToast();

    /**
     * 是否执行双击退出
     */
    protected abstract boolean onKeyDownExitDoubleClickEnable();

}
