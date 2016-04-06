package com.tiangua.zhz.common.network.response;

import android.content.Context;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;
import com.tiangua.zhz.common.network.IRequestCallback;
import com.tiangua.zhz.common.util.JsonObj;
import com.tiangua.zhz.model.GoodsInfoModel;
import com.tiangua.zhz.model.UserInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class ApplyCashResponse extends IHttpReqTaskListener {

    private PostResponse response = new PostResponse();
    Context context;
    IRequestCallback requestCallback;

    ApplyCashResponse(Context context, IRequestCallback requestCallback){
        this.context = context;
        this.requestCallback = requestCallback;
    }

    @Override
    public void onPreExecute() {
        requestCallback.onPreExecute();
    }

    @Override
    public void onPostExeute(JSONObject json) {
        try {
            if (json.has("userinfo")) {
                UserInfoModel userInfoModel = (UserInfoModel) JsonObj.jsonToObject(json.getJSONObject("userinfo"), UserInfoModel.class);
                UserInfoModel.saveUser(context, userInfoModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            response.setErrMsg(e.getMessage());
            requestCallback.onError(response);
        }
    }

    @Override
    public void onPostExeute(JSONArray json) {

    }

    @Override
    public void onError(JSONObject json) {
        try {
            response.setErrMsg(json.getString("message"));
            requestCallback.onError(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismissPD() {
    }
}
