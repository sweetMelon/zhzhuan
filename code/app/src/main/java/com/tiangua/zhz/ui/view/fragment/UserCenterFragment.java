package com.tiangua.zhz.ui.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tiangua.zhz.R;
import com.tiangua.zhz.ui.presenter.impl.UserCenterPresenter;
import com.tiangua.zhz.ui.view.activity.ApplyCashActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by adamFeng on 2016/1/13.
 */
public class UserCenterFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = UserCenterFragment.class.getSimpleName();

    private Button btn_apply_cash;
    private RelativeLayout ra_apply_cash_record;
    private RelativeLayout ra_business_opration;

    UserCenterPresenter userCenterPresenter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        userCenterPresenter = new UserCenterPresenter(getActivity(), view);

        btn_apply_cash = (Button) view.findViewById(R.id.btn_apply_cash);
        ra_apply_cash_record = (RelativeLayout) view.findViewById(R.id.ra_apply_cash_record);
        btn_apply_cash.setOnClickListener(this);
        ra_apply_cash_record.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();
        userCenterPresenter.init();
        MobclickAgent.onPageStart("UserCenterFragment");
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UserCenterFragment ");
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
        userCenterPresenter.destory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_apply_cash:
                Intent applyCashIntent = new Intent(getActivity(), ApplyCashActivity.class);
                getActivity().startActivity(applyCashIntent);
                break;

            case R.id.ra_apply_cash_record:
                break;
        }
    }
}
