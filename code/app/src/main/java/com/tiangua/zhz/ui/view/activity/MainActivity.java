package com.tiangua.zhz.ui.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tiangua.zhz.R;
import com.tiangua.zhz.common.util.ActivityManager;
import com.tiangua.zhz.model.UserInfoModel;
import com.tiangua.zhz.ui.presenter.impl.MainActivityPresenter;
import com.tiangua.zhz.ui.view.fragment.MainFragment;
import com.tiangua.zhz.ui.view.fragment.UserCenterFragment;
import com.tiangua.zhz.ui.view.views.ViewBottom;
import com.umeng.analytics.MobclickAgent;
import com.zhenhaozz.DevInit;
import adh.doi.jkd.AdManager;
import adh.doi.jkd.os.OffersManager;
import cn.dow.android.DOW;
import cn.waps.AppConnect;
import cn.waps.AppListener;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String JUMP_INTENT = "jump_fragment";

    Context context;

    ViewBottom viewBottom = null;
    FragmentManager fm = null;
    MainFragment mainFragment = null;
    UserCenterFragment userCenterFragment = null;

    private MainActivityPresenter mainActivityPresenter;
    private long touchTime = 0l;
    private int lastPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        setContentView(R.layout.activity_main);
        this.context = this;
        mainActivityPresenter = new MainActivityPresenter(this);
        initView();
        initJumpReciver();
        initConfiguration();
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
        if (jumpReciver != null)
            unregisterReceiver(jumpReciver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.destory();
        OffersManager.getInstance(this).onAppExit();
        AppConnect.getInstance(this).close();
    }

    private void initView() {
        viewBottom = (ViewBottom) findViewById(R.id.la_bottom);
        viewBottom.setOnBottomListener(onBottomClickListener);

        fm = getSupportFragmentManager();
        showFragment(0, null);
    }

    private void initJumpReciver() {
        jumpReciver = new JumpReciver();
        registerReceiver(jumpReciver, new IntentFilter(JUMP_INTENT));
    }

    /**
     * 从服务器获取相关基本配置信息
     */
    private void initConfiguration() {
        mainActivityPresenter.init();
    }

    ViewBottom.OnBottomClickListener onBottomClickListener = new ViewBottom.OnBottomClickListener() {

        @Override
        public void onClick(int index) {
            showFragment(index, null);
        }
    };

    private JumpReciver jumpReciver = null;

    private class JumpReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1 != null && arg1.getAction().equals(JUMP_INTENT)) {
                int jumpIndex = arg1.getIntExtra("jumpIndex", 0);
                showFragment(jumpIndex, null);
            }
        }

    }

    private void showFragment(int index, Bundle bundle) {
        viewBottom.select(index);
        FragmentTransaction transaction = fm.beginTransaction();
        switch (index) {
            case 0:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.replace(R.id.ft_content, mainFragment);
                    transaction.commit();
                    lastPage = index;
                    break;
                }
                switchContent(index, mainFragment);

                break;

            case 1:
                if (userCenterFragment == null) {
                    userCenterFragment = new UserCenterFragment();
                    transaction.replace(R.id.ft_content, userCenterFragment);
                    transaction.commit();
                    lastPage = index;
                    break;
                }
                switchContent(index, userCenterFragment);
                break;
        }
    }

    private void switchContent(int nowIndex, Fragment to) {
        if (nowIndex == lastPage)
            return;

        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fromFragmtn = null;
        switch (lastPage) {
            case 0:
                fromFragmtn = mainFragment;
                break;

            case 1:
                fromFragmtn = userCenterFragment;
                break;
        }

        if (to != null && !to.isAdded()) { // 先判断是否被add过
            transaction.hide(fromFragmtn).add(R.id.ft_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(fromFragmtn).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        lastPage = nowIndex;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - touchTime > 1500) {
                toast(R.string.str_quit_tip);
                touchTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void toast(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
