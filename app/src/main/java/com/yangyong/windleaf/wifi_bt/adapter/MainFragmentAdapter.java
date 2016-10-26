package com.yangyong.windleaf.wifi_bt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yangyong.windleaf.wifi_bt.fragment.discover.ControlFragment;
import com.yangyong.windleaf.wifi_bt.fragment.discover.MessageFragment;
import com.yangyong.windleaf.wifi_bt.fragment.discover.SettingFragment;


public class MainFragmentAdapter extends FragmentPagerAdapter {

    String[] mTitles = new String[]{"消息", "控制", "设置"};

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MessageFragment.newInstance(0);
            case 1:
                return ControlFragment.newInstance(1);
            case 2:
                return SettingFragment.newInstance(2);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
