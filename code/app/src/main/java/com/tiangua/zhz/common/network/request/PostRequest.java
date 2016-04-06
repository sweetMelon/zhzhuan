package com.tiangua.zhz.common.network.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by adamFeng on 2016/3/9.
 */
public abstract class PostRequest extends Request<String> {
    protected String PROTOCOL_CHARSET = "utf-8";

    protected JSONObject json = new JSONObject();
    private IHttpReqTaskListener listener ;

    public PostRequest(String url, final IHttpReqTaskListener listener){
        super(Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null){
                    JSONObject err = new JSONObject();
                    try {
                        err.put("errmsg", "网络连接异常，请稍后再试");
                        err.put("retcode",-1);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    listener.onError(err);
                }
            }
        });
        this.listener = listener;
    }
    protected abstract JSONObject writeTo(JSONObject json) throws JSONException;

    public String build() throws JSONException {
        json = writeTo(json);
        Log.d(PostRequest.class.getSimpleName(),"req == " + json.toString());
        return json.toString();
    }

    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public byte[] getBody() {
        String mBody = "";
        try {
            mBody = build();
            return mBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mBody, PROTOCOL_CHARSET);
            return null;
        }catch (JSONException e){
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=UTF-8";
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e)) ;
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        listener.dismissPD();
    }

    @Override
    protected void deliverResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (listener == null){
                return;
            }

            if (obj.has("data") && obj.has("sign")){//如果包含data和sign则认为是加密数据
//                IPaySPOSTParse iPayPOSTParse=new IPaySPOSTParse();
//                JSONObject object=iPayPOSTParse.onPOSTParse(response);
//                if(object != null){
//                    try{
//                        int retCode=iPayPOSTParse.getCode();
//                        if(retCode==0){
//                            listener.onPostExeute(object);
//                        }else {
//                            listener.onError(object);
//                        }
//                    }catch (Exception e){
//                        listener.onError(object);
//                    }
//                }
            }else {
                Log.d(PostRequest.class.getSimpleName(),"resp == " + obj.toString());
                listener.onPostExeute(obj);
            }
        }catch (JSONException e){
            e.printStackTrace();

        }finally {
            listener.dismissPD();
        }
    }

//    @Override
//    public RetryPolicy getRetryPolicy() {
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        return retryPolicy;
//    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "json=" + json +
                '}';
    }
}
