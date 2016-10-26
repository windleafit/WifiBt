package com.yangyong.windleaf.wifi_bt.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WindLeaf on 2016/9/10.
 */
public class VolleyTest {

    private final RequestQueue queue;
    private Context context;

    private String url = "http://www.baidu.com";

    public VolleyTest(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void testStringRequest(Response.Listener listener, Response.ErrorListener errorListener) {

        StringRequest stringRequest = new StringRequest(url, listener, errorListener);

        queue.add(stringRequest);

    }

    public void testPostStringRequest(Response.Listener listener, Response.ErrorListener errorListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };

        queue.add(stringRequest);

    }

    public void testJsonRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JsonRequest jsonObjectRequest = new JsonObjectRequest(url, null, listener, errorListener);

        queue.add(jsonObjectRequest);

    }

}
