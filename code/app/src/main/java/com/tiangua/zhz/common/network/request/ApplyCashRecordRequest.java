package com.tiangua.zhz.common.network.request;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class ApplyCashRecordRequest extends PostRequest {

    int uid;
    String md;

    public ApplyCashRecordRequest(String url,int uid, String md, IHttpReqTaskListener listener){
        super(url, listener);
        this.md = md;
        this.uid = uid;
    }

    @Override
    protected JSONObject writeTo(JSONObject json) throws JSONException {
        json.put("md",md);
        json.put("uid", uid);
        return json;
    }
}
