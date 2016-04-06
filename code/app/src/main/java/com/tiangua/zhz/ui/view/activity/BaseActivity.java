package com.tiangua.zhz.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tiangua.zhz.R;
import com.tiangua.zhz.common.util.ActivityManager;


public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取屏幕宽度
     **/
    public int getWindowWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                // 在这里做你想做的事情
                // this.menu.showMenu(true);
                break;

            case KeyEvent.KEYCODE_BACK:
                if (ActivityManager.getActivityManager().activitySize() > 1) {
                    ActivityManager.getActivityManager().popActivity();
                } else {
                    // 最后一个页面了 再退要退出了。
                    return false;
                }

                break;

            default:
                super.onKeyDown(keyCode, event);
                break;
        }
        return true; // 最后，一定要做完以后返回 true，或者在弹出菜单后返回true，其他键返回super，让其他键默认
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void startActivity(Intent intent) {
        // TODO Auto-generated method stub
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}
