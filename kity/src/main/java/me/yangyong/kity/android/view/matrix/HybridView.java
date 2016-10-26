package me.yangyong.kity.android.view.matrix;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 快速创建组合控件
 * <p/>
 * Created by JOUAV on 2015/6/3.
 */
public abstract class HybridView extends LinearLayout {

    private final HybridView mHybirdView;
    //基本成员
    protected Context mContext;

    protected Activity mActivity;

    protected LinearLayout mLayout;

    protected LayoutParams mLayoutParams;

    protected List<View> mViewList;


    //可选成员
    protected Integer mOrient = LinearLayout.HORIZONTAL;

    protected Integer mGravity = Gravity.CENTER;

    protected Integer mMargin = 0;

    protected Integer mWidth = 0; //dp
    protected Integer mHeight = 0; //dp

    protected Integer mMinWidth = 0; //dp
    protected Integer mMinHeight = 0; //dp


    public HybridView(Context context) {
        this(context, null);
    }


    public HybridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHybirdView = this;
        mContext = context;
        mActivity = (Activity) context;
        mViewList = new ArrayList<>();

        set();
        add();
        listen();

        create();
    }

    /**
     * 加载XML文件布局
     *
     * @param context
     * @param linearlayout
     */
    public HybridView(Context context, int linearlayout) {
        super(context);
        mHybirdView = this;
        mContext = context;
        mActivity = (Activity) context;
        mViewList = new ArrayList<>();

        View view = LayoutInflater.from(context).inflate(linearlayout, this);
        LinearLayout layout = (LinearLayout) view.findViewById(linearlayout);
        for (int i = 0; i < layout.getChildCount(); ++i) {
            mViewList.add(layout.getChildAt(i));
        }
    }

    protected abstract void set();

    protected abstract void add();

    protected abstract void listen();

    /*-------------------方法----------------------*/


    public void addView(View view) {
        view.setId(mViewList.size());
        mViewList.add(view);
    }

    public static final class Listen {
        public static final int CLICK = 1;
        public static final int LONGCLICK = 2;
        public static final int TOUCH = 4;
    }

    /**
     * @param orient
     * @return
     */
    protected void setOrient(int orient) {
        mOrient = orient;
    }

    /**
     * @param margin
     * @return
     */
    protected void setMargin(int margin) {
        mMargin = margin;
    }

    /**
     * @param width_dp
     * @param height_dp
     * @return
     */
    protected void setWidthHeight(int width_dp, int height_dp) {
        mWidth = fromDPtoPX(width_dp);
        mHeight = fromDPtoPX(height_dp);
    }

    /**
     * @param min_width_dp
     * @param min_height_dp
     * @return
     */
    protected void setMinWidthHeight(int min_width_dp, int min_height_dp) {
        mMinWidth = fromDPtoPX(min_width_dp);
        mMinHeight = fromDPtoPX(min_height_dp);
    }

    private int fromDPtoPX(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mActivity.getResources().getDisplayMetrics());
    }

    @Override
    public void setGravity(int gravity) {
        super.setGravity(gravity);
        mGravity = gravity;
    }

    /*--------------------创建-------------------*/
    private HybridView create() {
//        mLayout = new LinearLayout(mContext);
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
        mLayoutParams.gravity = mGravity;

        mHybirdView.setOrientation(mOrient);

        for (int i = 0; i < mViewList.size(); ++i) {
            mHybirdView.addView(mViewList.get(i), mLayoutParams);
        }

//        mActivity.addContentView(mLayout, mLayoutParams);

        return mHybirdView;
    }


    /*--------------------方法-------------------*/

    public View getItem(int index) {
        return mViewList.get(index);
    }

    protected int getSize() {
        return mViewList.size();
    }

    /*--------------------设置资源-------------------*/
    public void setResources(Object... value) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] instanceof String) {
                setText(i, (String) value[i]);
            }
            if (value[i] instanceof Integer) {
                setImage(i, (Integer) value[i]);
            }
        }
    }


    public void setText(int index, String text) {
        TextView v = (TextView) mViewList.get(index);
        v.setText(text);
    }

    public void setImage(int index, int id) {
        ImageView v = (ImageView) mViewList.get(index);
        v.setImageResource(id);
    }


    /*--------------------监听-------------------*/
    private class HybridOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (mOnViewListener != null) {
                mOnViewListener.onClick(v, v.getId(), (String) v.getTag());
            }
        }
    }

    private class HybridOnLongClickListener implements OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            if (mOnViewListener != null) {
                return mOnViewListener.onLongClick(v, v.getId(), (String) v.getTag());
            }
            return false;
        }
    }

    private class HybridOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mOnViewListener.onTouch(v, v.getId(), (String) v.getTag(), event);
        }
    }


    private OnViewListener mOnViewListener;


    /**
     * @param onViewListener 监听
     * @param index          View的索引号
     */
    public void setOnViewListener(OnViewListener onViewListener, int... index) {
        mOnViewListener = onViewListener;

        for (int i = 0; i < index.length; i++) {
            mViewList.get(index[i]).setOnClickListener(new HybridOnClickListener());
            mViewList.get(index[i]).setOnLongClickListener(new HybridOnLongClickListener());
            mViewList.get(index[i]).setOnTouchListener(new HybridOnTouchListener());
        }
    }

    public interface OnViewListener {
        void onClick(View view, int index, String tag);

        boolean onLongClick(View view, int index, String tag);

        boolean onTouch(View view, int index, String tag, MotionEvent event);
    }
}
