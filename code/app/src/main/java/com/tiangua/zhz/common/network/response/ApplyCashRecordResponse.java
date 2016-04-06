package com.tiangua.zhz.common.network.response;

import android.content.Context;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;
import com.tiangua.zhz.common.network.IRequestCallback;
import com.tiangua.zhz.common.util.JsonObj;
import com.tiangua.zhz.model.GoodsInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class ApplyCashRecordResponse extends IHttpReqTaskListener {

    private PostResponse response = new PostResponse();
    Context context;
    IRequestCallback requestCallback;

    ApplyCashRecordResponse(Context context, IRequestCallback requestCallback) {
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
            if (json.has("goodsList")) {
                JSONArray array = json.getJSONArray("goodsList");
                GoodsInfoModel[] goodsInfoModels = new GoodsInfoModel[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    goodsInfoModels[i] = (GoodsInfoModel) JsonObj.jsonToObject(array.getJSONObject(i), GoodsInfoModel.class);
                }
                response.responseMsg = goodsInfoModels;
                if (requestCallback != null) {
                    requestCallback.onResp(response);
                }
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
