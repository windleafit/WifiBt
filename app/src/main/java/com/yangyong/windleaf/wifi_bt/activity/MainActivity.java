package com.yangyong.windleaf.wifi_bt.activity;

import android.os.Bundle;

import com.yangyong.windleaf.wifi_bt.R;
import com.yangyong.windleaf.wifi_bt.fragment.account.LoginFragment;
import com.yangyong.windleaf.wifi_bt.fragment.discover.MainFragment;

import me.yangyong.kity.android.toast.Toasty;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity implements LoginFragment.OnLoginSuccessListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, LoginFragment.newInstance());
        }

        init();
    }

    public void init() {
        initView();
        initEvent();
        initBean();
    }

    public void initView() {

    }

    public void initEvent() {

    }

    public void initBean() {

    }

    @Override
    public void onLoginSuccess(String account) {
        Toasty.holder.show("Login success account = " + account);
        start(MainFragment.newInstance());
    }
}
