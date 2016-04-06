package com.tiangua.zhz.common.network.request;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class InitRequest extends PostRequest {

    String md;

    public InitRequest(String url, String md, IHttpReqTaskListener listener){
        super(url, listener);
        this.md = md;
    }

    @Override
    protected JSONObject writeTo(JSONObject json) throws JSONException {
        json.put("md",md);
        return json;
    }
}
