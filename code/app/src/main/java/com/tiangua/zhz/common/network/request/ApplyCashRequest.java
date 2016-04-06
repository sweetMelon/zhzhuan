package com.tiangua.zhz.common.network.request;

import com.tiangua.zhz.common.network.IHttpReqTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by adamFeng on 2016/3/15.
 */
public class ApplyCashRequest extends PostRequest {

    int uid;
    int cashAmount;
    int useScore;
    int goodsId;
    String cashAccount;
    String md;

    public ApplyCashRequest(String url, int uid,int cashAmount, int useScore, int goodsId, String cashAccount, String md, IHttpReqTaskListener listener) {
        super(url, listener);
        this.uid = uid;
        this.cashAccount = cashAccount;
        this.cashAmount = cashAmount;
        this.useScore = useScore;
        this.goodsId = goodsId;
        this.md = md;
    }

    @Override
    protected JSONObject writeTo(JSONObject json) throws JSONException {
        json.put("uid", uid);
        json.put("cashAccount", cashAccount);
        json.put("cashAmount", cashAmount);
        json.put("useScore", useScore);
        json.put("goodsId", goodsId);
        json.put("md", md);

        return json;
    }
}
