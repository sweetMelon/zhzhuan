package com.tiangua.zhz.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiangua.zhz.R;
import com.tiangua.zhz.model.UserInfoModel;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by adamFeng on 2016/4/7.
 */
public class ApplyCashFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ApplyCashFragment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ApplyCashFragment");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ApplyCashFragment ");
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apply_cash, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {

    }


    @Override
    public void onClick(View v) {

    }
}
