package com.yangyong.windleaf.wifi_bt.volley;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yangyong.windleaf.wifi_bt.volley.json.request.LoginBean;
import com.yangyong.windleaf.wifi_bt.volley.json.response.ReceivedBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangYong on 2016/9/27 0027.
 */
public class VolleyTransmit {

    private Context context;

    private RequestQueue queue;


    private String url;

    public VolleyTransmit(Context context, String url) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        this.url = url;
    }

    public VolleyTransmit(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);

    }


    public void register(String device, String account, String pwd) {

        final LoginBean jsonBeanRegister = new LoginBean(device, account, pwd);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ReceivedBean receivedBean = JSON.parseObject(response).toJavaObject(ReceivedBean.class);
                EventBus.getDefault().post(receivedBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("protocol", "10");
                map.put("value", JSON.toJSONString(jsonBeanRegister));
                return map;
            }
        };

        queue.add(request);

    }

}
