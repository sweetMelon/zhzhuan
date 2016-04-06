package com.tiangua.zhz.common.network.request;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class AskRequest extends PostRequest {

    int uid;
    int askType;
    int appId;
    int type;
    int taskId;
    String askDesc;
    String md;

    public AskRequest(String url, int uid, int askType,int appId, int type, int taskId, String askDesc, String md, IHttpReqTaskListener listener){
        super(url, listener);
        this.md = md;
        this.uid = uid;
        this.askType = askType;
        this.appId = appId;
        this.type = type;
        this.taskId = taskId;
        this.askDesc = askDesc;
    }

    @Override
    protected JSONObject writeTo(JSONObject json) throws JSONException {
        json.put("uid",uid);
        json.put("askType",askType);
        json.put("appId",appId);
        json.put("type",type);
        json.put("taskId",taskId);
        json.put("md",md);
        json.put("askDesc",askDesc);
        return json;
    }
}
