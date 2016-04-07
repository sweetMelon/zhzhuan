package com.tiangua.zhz.ui.presenter.impl;

import android.app.Activity;

import com.tiangua.zhz.common.api.ServerApi;
import com.tiangua.zhz.common.network.IRequestCallback;
import com.tiangua.zhz.common.network.QueueManager;
import com.tiangua.zhz.common.network.request.InitRequest;
import com.tiangua.zhz.common.network.response.InitResponse;
import com.tiangua.zhz.common.network.response.PostResponse;
import com.tiangua.zhz.common.util.DevDevice;
import com.tiangua.zhz.common.util.LogCat;
import com.tiangua.zhz.model.UserInfoModel;
import com.tiangua.zhz.ui.presenter.IMainPresenter;
import com.zhenhaozz.DevInit;

import adh.doi.jkd.AdManager;
import adh.doi.jkd.os.OffersManager;
import cn.dow.android.DOW;
import cn.waps.AppConnect;
import cn.waps.AppListener;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class MainActivityPresenter implements IMainPresenter {

    Activity context;
    public MainActivityPresenter(Activity context){
        this.context = context;
    }

    @Override
    public void init() {
        String userId = "";
        if(UserInfoModel.getUser(context) != null && UserInfoModel.getUser(context).getUid() > 0){
            userId = String.valueOf(UserInfoModel.getUser(context).getUid());
            initSdk(userId);
        }else {
            //// TODO: 2016/4/7 测试服务器接口暂无，所以直接initSdk 
            initSdk(userId);
            String md = DevDevice.getInstance().getDeviceInfo(context);
            InitRequest initRequest = new InitRequest(ServerApi.INIT, md, new InitResponse(context, new IRequestCallback(){
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onResp(PostResponse resp) {
                    String userId = String.valueOf(UserInfoModel.getUser(context).getUid());
                    initSdk(userId);
                }
                @Override
                public void onError(PostResponse resp) {
                    if(resp != null && resp.getErrMsg() != null){
                        LogCat.e("MainActivityPresenter", resp.getErrMsg());
                    }
                }
            }));
            QueueManager.getManager(context).addToRequestQueue(initRequest);
        }
    }

    private void initSdk(String userId) {
        //dm
        DOW.getInstance(context).init(userId);
        //dj
        DevInit.initGoogleContext(context, "3eaa9292b1f28a881a9a11b623b1d472");
        DevInit.setCurrentUserID(context, userId);
        //ym
        AdManager.getInstance(context).init("960d246daebd9e7d", "97cd5e1842e42eb5", true);
        OffersManager.getInstance(context).setCustomUserId(userId);
        OffersManager.getInstance(context).setUsingServerCallBack(true);
        OffersManager.getInstance(context).onAppLaunch();
        //wap
        AppConnect.getInstance("6bf5f87f132046d3f782f06826f4c1c2","ceshi",context);
        //監聽wap關閉
        AppConnect.getInstance(context).setOffersCloseListener(new AppListener(){
            @Override
            public void onOffersClose() {
                // TODO close wap
            }
        });
        //awo
    }

    @Override
    public void destory() {
        context = null;
    }
}
