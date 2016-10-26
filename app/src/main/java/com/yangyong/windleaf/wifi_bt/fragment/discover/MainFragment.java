package com.yangyong.windleaf.wifi_bt.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangyong.windleaf.wifi_bt.R;
import com.yangyong.windleaf.wifi_bt.adapter.MainFragmentAdapter;
import com.yangyong.windleaf.wifi_bt.base.BaseMainFragment;


/**
 * Created by YoKeyword on 16/2/3.
 */
public class MainFragment extends BaseMainFragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mToolbar.setTitle("蓝牙门禁系统");
        initToolbarNav(mToolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.message));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.control));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.setting));
        mViewPager.setAdapter(new MainFragmentAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
