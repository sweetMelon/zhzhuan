package com.tiangua.zhz.ui.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiangua.zhz.R;
import com.tiangua.zhz.common.util.ActivityManager;
import com.tiangua.zhz.model.UserInfoModel;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by adamFeng on 2016/4/7.
 */
public class ApplyCashActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageView iv_return;
    private RelativeLayout ra_alipay;
    private RelativeLayout ra_phone;
    private RelativeLayout ra_wechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        setContentView(R.layout.activity_apply_cash);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_top_value);
        tv_title.setText(R.string.apply_cash);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_return.setVisibility(View.VISIBLE);

        ra_alipay = (RelativeLayout) findViewById(R.id.ra_apply_cash_alipay);
        ra_phone = (RelativeLayout) findViewById(R.id.ra_apply_cash_phone_charge);
        ra_wechat = (RelativeLayout) findViewById(R.id.ra_apply_cash_wechat);

        iv_return.setOnClickListener(this);
        ra_alipay.setOnClickListener(this);
        ra_phone.setOnClickListener(this);
        ra_wechat.setOnClickListener(this);

        TextView tv_apply_cash = (TextView) findViewById(R.id.tv_apply_cash_prompt);
        String apply_cash_prompt = String.format(getResources().getString(R.string.apply_cash_prompt), UserInfoModel.getUser(this).getScore());
        tv_apply_cash.setText(apply_cash_prompt);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ra_apply_cash_alipay:
                break;

            case R.id.ra_apply_cash_phone_charge:
                break;

            case R.id.ra_apply_cash_wechat:
                break;

            case R.id.iv_return:
                finish();
                break;
        }
    }
}
