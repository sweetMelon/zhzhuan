package com.tiangua.zhz.common.network;

import com.tiangua.zhz.common.network.response.PostResponse;

/**
 * Created by adamFeng on 2016/3/26.
 */
public interface IRequestCallback {

    public void onPreExecute();

    /**
     * 业务正常回调
     */
    public void onResp(PostResponse resp);

    /**
     * 业务异常回调
     */
    public void onError(PostResponse resp);
}
