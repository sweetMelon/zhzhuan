package com.tiangua.zhz.common;

import android.app.Application;
import android.os.Environment;

import com.tiangua.zhz.common.util.UnCatchExceptionHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by adamFeng on 2016/1/11.
 */
public class ZhApplication extends Application{
    public static final String IMG_CACHE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/syso/android/timg";
    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.openActivityDurationTrack(false);
        UnCatchExceptionHandler.getInstance().init(this);
//        Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());
    }
}
