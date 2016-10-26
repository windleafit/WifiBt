package com.yangyong.windleaf.wifi_bt.activity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yangyong.windleaf.wifi_bt.R;
import com.yangyong.windleaf.wifi_bt.bluetooth.BlueToothTest;
import com.yangyong.windleaf.wifi_bt.netty.learn.RxNettySocket;
import com.yangyong.windleaf.wifi_bt.socket.event.Event;
import com.yangyong.windleaf.wifi_bt.socket.link.TcpDataLink;
import com.yangyong.windleaf.wifi_bt.volley.VolleyTest;
import com.yangyong.windleaf.wifi_bt.volley.VolleyTransmit;
import com.yangyong.windleaf.wifi_bt.volley.json.response.ReceivedBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Set;

import me.yangyong.kity.android.base.Initial;
import me.yangyong.kity.android.debug.DebugView;
import me.yangyong.kity.android.toast.Toasty;
import me.yangyong.kity.java.callback.Back;
import me.yangyong.kity.java.event.EventListener;

public class TestActivity extends AppCompatActivity implements Initial {


    private DebugView mDebug;
    private VolleyTest volleyTest;
    private BlueToothTest blueToothTest;
    private TcpDataLink tcpDataLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void init() {
        initView();
        initBean();
        initEvent();
        initDebug();
    }

    private void initDebug() {
        ((RelativeLayout) findViewById(R.id.debug_layout))
                .addView(mDebug, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        assert mDebug != null;
        mDebug.use();
        mDebug.setVisibility(View.VISIBLE);
        mDebug.showScrollLayout();

        mDebug.setDebugClickListener(new DebugView.DebugClickListener() {
            @Override
            public void onClick(View v, int index) {
                switch (index) {
                    case 1:
                        volleyTest.testStringRequest(
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String response) {
                                        Log.d(this.getClass().getName(), "onRespone = " + response);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDebug.setScrollText(response);
                                            }
                                        });
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(this.getClass().getName(), "onErrorResponse = " + error.getMessage());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toasty.holder.show("onErrorResponse");
                                            }
                                        });
                                    }
                                });
                        break;
                    case 2:
                        volleyTest.testJsonRequest(
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        mDebug.setScrollText(response.toString());
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(final VolleyError error) {
                                        Log.d(this.getClass().getName(), "onErrorResponse = " + error.getMessage());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toasty.holder.show("onErrorResponse");
                                                mDebug.setScrollText(error.getMessage());
                                            }
                                        });
                                    }
                                });
                        break;
                    case 3:
                        blueToothTest.setAutoOpenBlueTooth(true);
                        blueToothTest.open();
                        break;
                    case 4:
                        Set<BluetoothDevice> list = blueToothTest.getDevice();
                        if (list != null) {
                            for (BluetoothDevice s : list) {
                                mDebug.setScrollText(new StringBuilder()
                                                             .append(s.getName())
                                                             .append(" , ")
                                                             .append(s.getAddress())
                                                             .append(" , ")
                                                             .append(s.getBondState())
                                                             .toString());

                                if (s.getUuids() != null) {
                                    mDebug.setScrollText(s.getUuids().length + "");
                                    ParcelUuid[] uuids = s.getUuids();
                                    for (ParcelUuid uuid : uuids) {
                                        mDebug.setScrollText(uuid.getUuid().toString());
                                    }
                                }


                            }
                            mDebug.setScrollText("\n");
                        }
                        break;
                }
            }
        }, "Volley", "VolleyJson", "BlueToothServer", "BlueToothClient");

        mDebug.setDebugSubClickListener(new DebugView.DebugSubClickListener() {
            @Override
            public void onClick(View v, int index) {
                switch (index) {
                    case 1:
                        try {
                            mDebug.setScrollText("createService");
                            blueToothTest.createService(new Back() {
                                @Override
                                public void value(final Object object) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDebug.setScrollText((String) object);
                                        }
                                    });
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        Set<BluetoothDevice> list = blueToothTest.getDevice();
                        for (BluetoothDevice bd : list) {
                            if (bd.getName().equalsIgnoreCase("魅蓝NOTE") || bd.getName().equalsIgnoreCase("WINDLEAF")) {
                                try {
                                    blueToothTest.createClient(bd, new Back() {
                                        @Override
                                        public void value(final Object object) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mDebug.setScrollText((String) object);
                                                }
                                            });
                                        }
                                    });
                                    mDebug.setScrollText("createClient:" + bd.getName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        break;
                    case 3:
                        VolleyTransmit volleyTransmit = new VolleyTransmit(TestActivity.this, "http://192.168.0.103:8080/WebApp/JsonServlet");
                        volleyTransmit.register("DEVICE", "name", "123");
                        break;
                    case 4:
                        mDebug.setScrollText("rxnetty");
                        new RxNettySocket().run();
                        break;
                    case 5:
                        mDebug.setScrollText("TcpDataLink");
                        tcpDataLink = new TcpDataLink();
                        tcpDataLink.connect(new InetSocketAddress("192.168.0.103", 8234));
                        tcpDataLink.addListener(new EventListener() {
                            @Override
                            public boolean onNotified(Object type, Object source, final Object message) {
                                switch ((Event) type) {
                                    case Connected:
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDebug.setScrollText("Connected");
                                            }
                                        });
                                        break;
                                    case Disconnected:
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDebug.setScrollText("Disconnected");
                                            }
                                        });
                                        break;
                                    case RecvBuffer:
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDebug.setScrollText("RecvBuffer = " + message.toString());
                                            }
                                        });
                                        break;
                                }
                                return false;
                            }
                        });

                        break;
                    case 6:
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        byteBuffer.put("abcdefg".getBytes());
                        tcpDataLink.setSendBuffer(byteBuffer);
                        break;
                }
            }
        });
    }

    
    @Override
    public void initView() {


    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initBean() {
        mDebug = new DebugView(this);
        volleyTest = new VolleyTest(this);
        blueToothTest = new BlueToothTest(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(ReceivedBean receivedBean) {
        mDebug.setScrollText(receivedBean.toString());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(String str) {
        mDebug.setScrollText(str);

    }


}
