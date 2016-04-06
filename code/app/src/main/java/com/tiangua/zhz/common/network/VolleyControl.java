package com.tiangua.zhz.common.network;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tiangua.zhz.common.util.LogCat;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VolleyControl {
    private static final String TAG = VolleyControl.class.getCanonicalName();
    private static VolleyControl instance = null;
    private RequestQueue queue = null;

    private VolleyControl(Context context) {
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

    public synchronized static VolleyControl getVolley(Context context) {
        if (instance == null) {
            synchronized (VolleyControl.class) {
                if (instance == null) {
                    instance = new VolleyControl(context);
                }
            }
        }
        return instance;
    }

    /**
     * post请求
     *
     * @param context
     * @param url           请求地址
     * @param params        请求参数
     * @param listener      请求监听器
     * @param errorListener 失败监听器
     */
    public void requestByPost(Context context, String url, Map<String, String> params,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {

        StringRequest request = null;
        try {
            StringBuilder sb = new StringBuilder(url);
            if (params != null && params.size() > 0) {
                sb.append("?");
                Set<String> keys = params.keySet();
                Iterator iter = keys.iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    sb.append(key + "=" + params.get(key) + "&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            request = new StringRequest(Method.POST, sb.toString(), listener,
                    errorListener);
            if (queue == null) {
                queue = Volley.newRequestQueue(context);
                queue.start();
            }
            queue.add(request);
        } catch (Exception e) {
            LogCat.e(TAG, e);
        }
    }

    /**
     * get请求
     *
     * @param context
     * @param url           请求地址
     * @param params        请求参数
     * @param listener      请求监听器
     * @param errorListener 失败监听器
     */
    public void requestByGet(Context context, String url,
                             Map<String, String> params, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        StringRequest request = null;
        try {
            StringBuilder sb = new StringBuilder(url);
            if (params != null && params.size() > 0) {
                sb.append("?");
                Set<String> keys = params.keySet();
                Iterator iter = keys.iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    sb.append(key + "=" + params.get(key) + "&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }

            request = new StringRequest(Method.GET, sb.toString(), listener,
                    errorListener);
            if (queue == null) {
                queue = Volley.newRequestQueue(context);
                queue.start();
            }
            queue.add(request);
        } catch (Exception e) {
            LogCat.e(TAG, e);
        }

    }

    public void cancelAllRequest(Activity context) {
        queue.cancelAll(context);
    }
}
