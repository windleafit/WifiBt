package me.yangyong.kity.android.base.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yangyong.kity.android.log.CatLog;
import me.yangyong.kity.android.log.SdLog;

public abstract class BaseFragment extends Fragment {

    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;

    public BaseFragment() {super();}

    protected abstract void init();

    protected abstract void initView();

    protected abstract void initBean();

    protected abstract void initEvent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
        if (getActivity().isFinishing())
            releaseResource();
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach");
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
}
