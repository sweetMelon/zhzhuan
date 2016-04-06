package com.tiangua.zhz.common.network.request;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/15.
 */
public class FetchGoodsRequest extends PostRequest {

    int uid;
    String md;

    public FetchGoodsRequest(String url, int uid, String md, IHttpReqTaskListener listener) {
        super(url, listener);
        this.uid = uid;
        this.md = md;
    }

    @Override
    protected JSONObject writeTo(JSONObject json) throws JSONException {
        json.put("uid", uid);
        json.put("md", md);
        return json;
    }
}
