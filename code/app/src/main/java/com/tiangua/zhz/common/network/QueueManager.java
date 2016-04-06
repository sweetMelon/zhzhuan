package com.tiangua.zhz.common.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.Volley;
import com.tiangua.zhz.common.ZhApplication;

/**
 * Created by ktt on 2015/10/23.
 */
public class QueueManager {
    private static RequestQueue queues;
    public static ZhApplication application;
    private static QueueManager manager;


    private QueueManager(Context context) {
        this.application = (ZhApplication) context.getApplicationContext();
        queues = getRequestQueue();
    }

    public static synchronized QueueManager getManager(Context context) {
        if (application == null) {
            application = (ZhApplication) context.getApplicationContext();
        }
        if (manager == null) {
            manager = new QueueManager(context);
        }
        return manager;
    }

    public RequestQueue getRequestQueue() {
        if (queues == null) {
            queues = Volley.newRequestQueue(application);
        }
        return queues;
    }

    //添加请求队列
    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    //取消所有队列通过设置的tag
    public void cancelRequestQueueByTag(Object tag) {
        getRequestQueue().cancelAll(tag);
    }

    //取消所有的请求队列通过设置的filter
    public void cancelRequestQueueByFilter(RequestFilter filter) {
        getRequestQueue().cancelAll(filter);
    }

    //启动某个请求队列，此方法的作用是：确保没有队列在运行的情况下启动请求队列
    public void startRequestQueue() {
        getRequestQueue().start();
    }

    //关闭某个请求队列
    public void stopRequestQueue() {
        getRequestQueue().stop();
    }

}
