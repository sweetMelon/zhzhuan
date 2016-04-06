package com.tiangua.zhz.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tiangua.zhz.R;
import com.tiangua.zhz.model.UserInfoModel;
import com.umeng.analytics.MobclickAgent;
import com.zhenhaozz.DevInit;

import adh.doi.jkd.os.OffersBrowserConfig;
import adh.doi.jkd.os.OffersManager;
import cn.dow.android.DOW;
import cn.waps.AppConnect;

/**
 * Created by adamFeng on 2016/1/11.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private RelativeLayout ra_task_1;
    private RelativeLayout ra_task_2;
    private RelativeLayout ra_task_3;
    private RelativeLayout ra_task_4;
    private RelativeLayout ra_task_5;
    private RelativeLayout ra_task_6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        setListener();
        return view;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainFragment ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String userId = String.valueOf(UserInfoModel.getUser(getActivity()).getUid());
        switch (v.getId()) {
            case R.id.ra_task_1://dm
                DOW.getInstance(getActivity()).show(getActivity());
                break;
            case R.id.ra_task_2://dj
                DevInit.showOffers(getActivity());
                break;
            case R.id.ra_task_3://youm
                OffersBrowserConfig.setPointsLayoutVisibility(false);
                OffersBrowserConfig.setBrowserTitleText("真好赚通道3");
                OffersManager.getInstance(getActivity()).showOffersWall();
                break;
            case R.id.ra_task_4:
                //wap
                AppConnect.getInstance(getActivity()).showOffers(getActivity(), userId);
                break;
            case R.id.ra_task_5:
                break;
            case R.id.ra_task_6:
                break;
        }
    }

    private void initView(View view) {
        ra_task_1 = (RelativeLayout) view.findViewById(R.id.ra_task_1);
        ra_task_2 = (RelativeLayout) view.findViewById(R.id.ra_task_2);
        ra_task_3 = (RelativeLayout) view.findViewById(R.id.ra_task_3);
        ra_task_4 = (RelativeLayout) view.findViewById(R.id.ra_task_4);
        ra_task_5 = (RelativeLayout) view.findViewById(R.id.ra_task_5);
        ra_task_6 = (RelativeLayout) view.findViewById(R.id.ra_task_6);
    }

    private void setListener() {
        ra_task_1.setOnClickListener(this);
        ra_task_2.setOnClickListener(this);
        ra_task_3.setOnClickListener(this);
        ra_task_4.setOnClickListener(this);
        ra_task_5.setOnClickListener(this);
        ra_task_6.setOnClickListener(this);
    }
}
