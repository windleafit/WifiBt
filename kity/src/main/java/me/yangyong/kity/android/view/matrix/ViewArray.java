package me.yangyong.kity.android.view.matrix;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态创建控件组
 * <p/>
 * Created by JOUAV on 2015/5/29.
 */
public abstract class ViewArray<T extends View> {

    protected Activity mActivity;
    protected LinearLayout mLayout;

    private LinearLayout.LayoutParams mLayoutParams;

    private LinearLayout.LayoutParams mRootLayoutParams;

    private T mView;

    protected List<T> mList;

    protected int SIZE;

    private static final int MARGIN_DP = 5;

    private View.OnClickListener mListener;

    /**
     * 构造，需要容器
     *
     * @param activity
     * @param container
     * @param size
     */
    public ViewArray(Activity activity, int container, int size) {
        this(activity, container, size, new float[]{WRAP, WRAP}, 0, WRAP);
    }

    /**
     * 构造，需要容器
     *
     * @param activity
     * @param container
     * @param size
     * @param width_height 传入的宽高
     * @param space        View间的间隔
     * @param unit         宽高数值单位
     */
    public ViewArray(Activity activity, int container, int size, float[] width_height, float space, int unit) {

        mActivity = activity;
        SIZE = size;
        mList = new ArrayList<>();

        mLayout = (LinearLayout) mActivity.findViewById(container);

        if (unit == WRAP) {
            mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            setLayoutParam(width_height, unit);
        }

        int margin_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mActivity.getResources().getDisplayMetrics());
        switch (getOrient()) {
            case LinearLayout.HORIZONTAL:
                mLayoutParams.setMargins(margin_px, 0, 0, 0);
                break;
            case LinearLayout.VERTICAL:
                mLayoutParams.setMargins(0, margin_px, 0, 0);
                break;
        }

        createViews();
    }

    /**
     * 设置宽度
     *
     * @param width_height
     * @param unit
     */
    private void setLayoutParam(float[] width_height, int unit) {
        float width = 0;
        float height = 0;
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                width = width_height[0];
                height = width_height[1];
                break;
            case TypedValue.COMPLEX_UNIT_DIP:
            case TypedValue.COMPLEX_UNIT_SP:
                width = TypedValue.applyDimension(unit, width_height[0], mActivity.getResources().getDisplayMetrics());
                height = TypedValue.applyDimension(unit, width_height[1], mActivity.getResources().getDisplayMetrics());
                break;
        }

        mLayoutParams = new LinearLayout.LayoutParams((int) width, (int) height);


    }

    public static final int WRAP = -1;
    public static final int PX = TypedValue.COMPLEX_UNIT_PX;
    public static final int DP = TypedValue.COMPLEX_UNIT_DIP;
    public static final int SP = TypedValue.COMPLEX_UNIT_SP;


    /**
     * 构造，无需容器【未完善】
     *
     * @param activity
     * @param orient
     * @param x
     * @param y
     * @param size
     */
    public ViewArray(Activity activity, int orient, int x, int y, int size) {

        mActivity = activity;
        SIZE = size;
        mList = new ArrayList<>();

        mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(orient);
        mLayout.setX(x);
        mLayout.setY(y);

        mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        createViews();

        mRootLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mActivity.addContentView(mLayout, mRootLayoutParams);

    }

    /**
     * 创建
     */
    private void createViews() {
        for (int i = 0; i < SIZE; ++i) {
            mView = createView();
            mView.setId(i);
            mList.add(mView);
            mLayout.addView(mView, mLayoutParams);
        }
    }


    /**
     * 子类实现创建实例
     *
     * @return new T(mActivity)      e.g. return new Button(mActivity)
     */
    public abstract T createView();

    public T getItem(int index) {
        if (mList != null) {
            return mList.get(index);
        }
        return null;
    }

    public int getOrient() {
        return mLayout.getOrientation();
    }


    /**
     * 是否行列对换
     *
     * @return
     */
    public boolean isConvertRank() {
        return getOrient() == LinearLayout.HORIZONTAL;
    }

    /**
     * 获取个数
     *
     * @return
     */
    public int getSize() {
        return mList.size();
    }

//    public abstract T initArray(Object... objects);

    /**
     * 恢复View
     */
    public void resumeView() {
        int childCount = mLayout.getChildCount();
        //判断mLayout中是否包含要添加的View
        for (int j = 0; j < childCount; j++) {
            if (mList.contains(mLayout.getChildAt(j))) {
                return;
            }
        }
        for (T v : mList) {
            mLayout.addView(v, mLayoutParams);
        }
    }

    /**
     * 移除View
     */
    public void removeView() {
        for (int i = 0; i < mList.size(); i++) {
            mLayout.removeView(mList.get(i));
        }
    }


    //监听
    private ItemOnClickListener mItemOnClickListener;


    /**
     * 设置监听
     *
     * @param itemOnClickListener
     */
    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {

        mItemOnClickListener = itemOnClickListener;

        mListener = new ViewArrayOnClickListener();

        for (int i = 0; i < SIZE; ++i) {
            mList.get(i).setOnClickListener(mListener);
        }

    }

    /**
     * 监听接口
     */
    public interface ItemOnClickListener {
        void onItemClick(int i);
    }

    /**
     * 点击监听
     */
    private class ViewArrayOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mItemOnClickListener != null) {
                mItemOnClickListener.onItemClick(v.getId());
            }
        }
    }

}
