package com.tiangua.zhz.ui.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tiangua.zhz.R;
import com.tiangua.zhz.model.UserInfoModel;
import com.tiangua.zhz.ui.presenter.IUserCenterPresenter;

/**
 * Created by adamFeng on 2016/4/6.
 */
public class UserCenterPresenter implements IUserCenterPresenter {

    private Context context;
    private View rootView;

    public UserCenterPresenter(Activity activity, View view) {
        this.rootView = view;
        this.context = activity;
    }

    @Override
    public void init() {
        try {
            UserInfoModel user = UserInfoModel.getUser(context);
            if (user != null) {
                TextView tv_uid = (TextView) rootView.findViewById(R.id.tv_user_id);
                TextView tv_today_money = (TextView) rootView.findViewById(R.id.tv_user_today_money_value);
                TextView tv_user_balance = (TextView) rootView.findViewById(R.id.tv_user_balance);
                TextView tv_user_history_balance = (TextView) rootView.findViewById(R.id.tv_balance_history);

                tv_uid.setText("uid: "+String.valueOf(user.getUid()));
                if(user.getTodayScore(context) > 0){
                    tv_today_money.setText(user.getTodayScore(context));
                }

                if(user.getHistoryScore(context) > 0){
                    tv_user_history_balance.setText(String.valueOf(user.getHistoryScore(context)));
                }
                else {
                    tv_user_history_balance.setText(context.getString(R.string.str_user_center_balance_histroy)+"0");
                }

                tv_user_balance.setText(context.getString(R.string.str_user_center_balance) + String.valueOf(user.getScore()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destory() {
        rootView = null;
        context = null;
    }
}
