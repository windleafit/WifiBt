package me.yangyong.kity.android.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import me.yangyong.kity.android.base.ActivityController;
import me.yangyong.kity.android.base.application.BaseApplication;


public abstract class BaseActivity extends Activity {

    protected BaseApplication mApplication;

    protected SharedPreferences mSharedPreferences;
    protected SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        ActivityController.addActivity(this);
        mApplication = (BaseApplication) getApplication();
        mSharedPreferences = mApplication.mSharedPreferences;
        mEditor = mApplication.mEditor;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log("onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        log("onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    /**
     * 释放资源
     */
    protected abstract void releaseResource();

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    /**
     * 日志
     *
     * @param msg
     */
    private void log(String msg) {
        mApplication.log(getClass().getSimpleName(), msg);
    }

    protected abstract void init();

    protected abstract void initView();

    protected abstract void initBean();

    protected abstract void initEvent();
}
