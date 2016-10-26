package me.yangyong.kity.android.view.matrix;


import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 控件矩阵
 * <p/>
 * Created by JOUAV on 2015/6/4.
 */
public abstract class ViewMatrix<T extends HybridView> extends ViewArray<T> {

    /**
     * 行
     */
    protected int mRow;

    /**
     * 列
     */
    protected int mCol;


    public ViewMatrix(Activity activity, int container, int size) {
        super(activity, container, size);
    }

    public ViewMatrix(Activity activity, int container, int size, float[] width_height, int unit) {
        super(activity, container, size, width_height, 0, unit);
    }

    /**
     * 初始化设置，子类实现，外部调用
     *
     * @param objects
     */
    public abstract ViewMatrix init(Object[]... objects);


    /**
     * 设置单行数据
     *
     * @param objects
     */
    public abstract void set(int index, Object... objects);


    /**
     * 创建实例
     *
     * @return
     */
    @Override
    public T createView() {
        return create();
    }

    /**
     * 子类创建实例
     *
     * @return
     */
    protected abstract T create();


    /*-----------------------方法-------------------------*/


    /**
     * 获取单个View
     *
     * @param row
     * @param col
     * @return
     */
    public View getView(int row, int col) {
        if (isConvertRank()) {
            return getItem(col).getItem(row);
        } else {
            return getItem(row).getItem(col);
        }
    }


    public Object getRowLine(int row) {
        if (isConvertRank()) {
            return getCol(row);
        } else {
            return getRow(row);
        }
    }


    /**
     * 获取一整行
     *
     * @return 类型为T
     */
    public T getRow(int row) {
        return getItem(row);
    }


    /**
     * 获取一整列
     *
     * @param col 列号
     * @return 类型为List<View>
     */
    public List<View> getCol(int col) {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            list.add(getItem(i).getItem(col));
        }
        return list;
    }

    /**
     * 获取一整行，当容器方向为horizonial
     *
     * @param row
     * @return
     */
    public List<View> getRowConvert(int row) {
        return getCol(row);
    }

    /**
     * 获取一整列，当容器方向为horizonial
     *
     * @param col
     * @return
     */
    public T getColConvert(int col) {
        return getRow(col);
    }

    /*-----------------------显示隐藏-------------------------*/

    /**
     * 容器中的内容是否可见（适合容器装载单个ViewArray类的时候）
     *
     * @param visibility
     */
    public void setVisibility(int visibility) {
        mLayout.setVisibility(visibility);
    }

    /**
     * 改变容器中的内容数据
     *
     * @param visibility
     */
    public void setVisibility(boolean visibility) {
        if (visibility) {
            resumeView();
        } else {
            removeView();
        }
    }


    /*-----------------------点击监听-------------------------*/
    private OnClickColumnListener mOnClicColumnListener;


    public interface OnClickColumnListener {
        void OnClick(View view, int row, int column);
    }

    public interface OnLongClickColumnListener {
        void OnLongClick(View view, int row, int column);
    }

    public void setOnItemClickListener(OnClickColumnListener columnListener, int... line) {
        mOnClicColumnListener = columnListener;
        for (int i = 0; i < SIZE; i++) {
            final int finalI = i;
            getItem(i).setOnViewListener(new HybridView.OnViewListener() {
                @Override
                public void onClick(View view, int index, String tag) {
                    //是否交换行列
                    if (isConvertRank()) {
                        mRow = index;
                        mCol = finalI;
                    } else {
                        mRow = finalI;
                        mCol = index;
                    }
                    mOnClicColumnListener.OnClick(view, mRow, mCol);
                }

                @Override
                public boolean onLongClick(View view, int index, String tag) {
                    return false;
                }

                @Override
                public boolean onTouch(View view, int index, String tag, MotionEvent event) {
                    return false;
                }

            }, line);
        }
    }


}
