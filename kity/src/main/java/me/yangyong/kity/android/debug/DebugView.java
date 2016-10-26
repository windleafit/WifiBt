package me.yangyong.kity.android.debug;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;

import me.yangyong.kity.R;
import me.yangyong.kity.android.base.Initial;
import me.yangyong.kity.java.bean.SlowRun;

/*
usage:

protected void initDebug() {
        //放在initView中，这样屏蔽initDebug后，其它地方使用debugView的显示功能才不会报NULL
        debugLayout = new DebugView(this);
        addContentView(debugLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        assert debugLayout != null;
        debugLayout.use();
        debugLayout.setVisibility(View.VISIBLE);
//        debugLayout.showScrollLayout();
        debugLayout.setDebugClickListener(new DebugView.DebugClickListener() {
            @Override
            public void onClick(View v, int index) {
                switch (index) {
                    case 1:
                        Toasty.holder.show("DebugView");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }
* */

/**
 * 调试View
 */
public class DebugView extends LinearLayout implements Initial {

    public TextView mTitle;
    public TextView mText;
    public View mSubTextLayout;
    public TextView mSubText1;
    public TextView mSubText2;
    public TextView mSubText3;
    public ScrollView mScrollTextLayout;
    public TextView mScrollText;
    public View mEditLayout;
    public EditText mEdit1;
    public EditText mEdit2;
    public View mButtonLayout;
    public Button mButton1;
    public Button mButton2;
    public Button mButton3;
    public Button mButton4;
    public View mSubButtonLayout;
    public Button mSubButton1;
    public Button mSubButton2;
    public Button mSubButton3;
    public Button mSubButton4;
    public Button mSubButton5;
    public Button mSubButton6;
    public Button mSubButton7;
    public Button mSubButton8;
    public DebugClickListener mDebugClickListener;
    public DebugSubClickListener mDebugSubClickListener;
    private LayoutInflater inflater;
    private boolean isUse = false;
    private SlowRun mReriodRun;//间隔运行
    private StringBuilder mScrollString;

    public DebugView(Context context) {
        super(context);
        init();
    }


    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.inflate_debug_view, this);
        initView();
        initEvent();
        initBean();
    }

    @Override
    public void initView() {
        mTitle = (TextView) findViewById(R.id.debug_title);
        mText = (TextView) findViewById(R.id.debug_text);

        mSubTextLayout = findViewById(R.id.debug_sub_text_layout);
        mSubText1 = (TextView) findViewById(R.id.debug_sub_text_1);
        mSubText2 = (TextView) findViewById(R.id.debug_sub_text_2);
        mSubText3 = (TextView) findViewById(R.id.debug_sub_text_3);

        mScrollTextLayout = (ScrollView) findViewById(R.id.debug_scroll_layout);
        mScrollText = (TextView) findViewById(R.id.debug_scroll_text);

        mEditLayout = findViewById(R.id.debug_edit_layout);
        mEdit1 = (EditText) findViewById(R.id.debug_edit_1);
        mEdit2 = (EditText) findViewById(R.id.debug_edit_2);

        mButtonLayout = findViewById(R.id.debug_button_layout);
        mButton1 = (Button) findViewById(R.id.debug_button1);
        mButton2 = (Button) findViewById(R.id.debug_button2);
        mButton3 = (Button) findViewById(R.id.debug_button3);
        mButton4 = (Button) findViewById(R.id.debug_button4);

        mSubButtonLayout = findViewById(R.id.debug_subbutton_layout);
        mSubButton1 = (Button) findViewById(R.id.debug_subbutton1);
        mSubButton2 = (Button) findViewById(R.id.debug_subbutton2);
        mSubButton3 = (Button) findViewById(R.id.debug_subbutton3);
        mSubButton4 = (Button) findViewById(R.id.debug_subbutton4);
        mSubButton5 = (Button) findViewById(R.id.debug_subbutton5);
        mSubButton6 = (Button) findViewById(R.id.debug_subbutton6);
        mSubButton7 = (Button) findViewById(R.id.debug_subbutton7);
        mSubButton8 = (Button) findViewById(R.id.debug_subbutton8);
    }

    @Override
    public void initEvent() {
        //Button
        mButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugClickListener != null) {
                    mDebugClickListener.onClick(v, 1);
                }
            }
        });
        mButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugClickListener != null) {
                    mDebugClickListener.onClick(v, 2);
                }
            }
        });
        mButton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugClickListener != null) {
                    mDebugClickListener.onClick(v, 3);
                }
            }
        });
        mButton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugClickListener != null) {
                    mDebugClickListener.onClick(v, 4);
                }
            }
        });
        //SubButton
        mSubButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 1);
                }
            }
        });
        mSubButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 2);
                }
            }
        });
        mSubButton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 3);
                }
            }
        });
        mSubButton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 4);
                }
            }
        });
        mSubButton5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 5);
                }
            }
        });
        mSubButton6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 6);
                }
            }
        });
        mSubButton7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 7);
                }
            }
        });
        mSubButton8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugSubClickListener != null) {
                    mDebugSubClickListener.onClick(v, 8);
                }
            }
        });
        //长按清空
        mScrollText.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearScrollText();
                return true;
            }
        });
    }

    public void clearScrollText() {
        mScrollString.delete(0, mScrollString.length());
        mScrollText.setText("");
    }

    @Override
    public void initBean() {

    }

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DebugView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void showScrollLayout() {
        if (isUse) {
            mScrollTextLayout.setVisibility(VISIBLE);
            mScrollString = new StringBuilder();
            mReriodRun = new SlowRun(1000);
        }
    }

    public void showEditLayout() {
        if (isUse)
            mEditLayout.setVisibility(VISIBLE);
    }

    public void showSubText() {
        if (isUse)
            mSubTextLayout.setVisibility(VISIBLE);
    }

    /**
     * 设置点击监听
     *
     * @param debugClickListener
     * @return
     */
    public DebugView setDebugClickListener(DebugClickListener debugClickListener, String... texts) {
        if (isUse) {
            mButtonLayout.setVisibility(VISIBLE);
            mDebugClickListener = debugClickListener;
            setButtonText(texts);
        }
        return this;
    }

    /**
     * 设置按钮文字
     */
    private void setButtonText(String... texts) {
        if (texts.length > 4) {
            texts = Arrays.copyOf(texts, 4);
        }
        switch (texts.length) {
            case 4:
                mButton4.setText(texts[3]);
            case 3:
                mButton3.setText(texts[2]);
            case 2:
                mButton2.setText(texts[1]);
            case 1:
                mButton1.setText(texts[0]);
        }
    }

    /**
     * 设置次按钮点击监听
     *
     * @param debugSubClickListener
     * @return
     */
    public DebugView setDebugSubClickListener(DebugSubClickListener debugSubClickListener) {
        if (isUse) {
            mSubButtonLayout.setVisibility(VISIBLE);
            mDebugSubClickListener = debugSubClickListener;
        }
        return this;
    }


    public void setTitle(String text) {
        if (isUse)
            mTitle.setText(text);
    }

    public void setText(String text) {
        if (isUse)
            mText.setText(text);
    }

    /**
     * 设置文字，并减慢打印速度
     *
     * @param text
     */
    public void setScrollTextSlow(final String text) {
        if (isUse) {
            //间隔运行
            if (mReriodRun != null) {
                mReriodRun.execute(new Runnable() {
                    @Override
                    public void run() {
                        setScrollText(text);
                    }
                });
            }
        }
    }

    public void setScrollText(final String text) {
        if (isUse) {
            mScrollTextLayout.post(new Runnable() {
                @Override
                public void run() {
                    mScrollString.append(text + "\n");
                    mScrollText.setText(mScrollString.toString());
                    //滚到底部
                    mScrollTextLayout.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    /**
     * 设置是否使用
     */
    public void use() {
        isUse = true;
    }

    public String getEdit1() {
        return mEdit1.getText().toString();
    }

    public void setEdit1(String edit1) {
        if (isUse)
            mEdit1.setText(edit1);
    }

    public String getEdit2() {
        return mEdit2.getText().toString();
    }

    public void setEdit2(String edit2) {
        if (isUse)
            mEdit2.setText(edit2);
    }

    /**
     * index 1-n
     */
    public interface DebugClickListener {
        void onClick(View v, int index);
    }

    /**
     * index 1-n
     */
    public interface DebugSubClickListener {
        void onClick(View v, int index);
    }
}
