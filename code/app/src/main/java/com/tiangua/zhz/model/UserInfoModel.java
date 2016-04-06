package com.tiangua.zhz.model;

import android.content.Context;
import android.net.Uri;

import com.tiangua.zhz.common.util.PreferenceUtil;

import java.util.Calendar;

/**
 * Created by adamFeng on 2016/1/20.
 * 用户信息
 */
public class UserInfoModel {

    public static String UID = "uid";
    public static String SCORE = "score";
    public static String PHONE = "phone";
    public static String QQ = "qq";
    public static String ALIPAY = "alipay";
    public static String TODAY_SCORE = "todayScore";
    public static String HISTORY_SCORE = "historyScore";
    public static String DAY = "day";//日期

    private int uid; //用户uid
    private int score; //用户当前元宝
    private String phone; //用户手机号
    private String qq; //用户qq号
    private String alipay;//用户支付宝账号
    private int todayScore; //用户当日获取元宝数
    private int historyScore;//用户总获取元宝数


    public static void saveUser(Context context, UserInfoModel userInfoModel) {
        PreferenceUtil shared = PreferenceUtil.getPreferenceUtil(context);
        shared.putTransact(UID,userInfoModel.getUid());
        shared.putTransact(SCORE,userInfoModel.getScore());
        shared.putTransact(PHONE,userInfoModel.getPhone());
        shared.putTransact(QQ,userInfoModel.getQq());
        shared.putTransact(ALIPAY,userInfoModel.getAlipay());
        shared.putTransact(TODAY_SCORE,userInfoModel.getTodayScore(context));
        shared.putTransact(HISTORY_SCORE,userInfoModel.getHistoryScore(context));
        shared.commitTransact();
    }

    public static UserInfoModel getUser(Context context) {
        PreferenceUtil shared = PreferenceUtil.getPreferenceUtil(context);
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUid(shared.getInt(UID,0));
        userInfoModel.setScore(shared.getInt(SCORE,0));
        userInfoModel.setAlipay(shared.getString(ALIPAY,""));
        userInfoModel.setPhone(shared.getString(PHONE,""));
        userInfoModel.setQq(shared.getString(QQ,""));
        userInfoModel.setHistoryScore(context, userInfoModel.getHistoryScore(context));
        userInfoModel.setTodayScore(context, userInfoModel.getTodayScore(context));
        return userInfoModel;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTodayScore(Context context) {
        int todayScores = 0;
        int lastSaveScoreDay = PreferenceUtil.getPreferenceUtil(context).getInt(DAY, 0);
        Calendar c = Calendar.getInstance();
        int nowDay = c.get(Calendar.DAY_OF_MONTH);
        //如果缓存的最近一次获取元宝的日期不等于0并且等于当天的日期，表示用户当天有过获取元宝的操作。
        if (lastSaveScoreDay != 0 && nowDay == lastSaveScoreDay) {
            todayScores = PreferenceUtil.getPreferenceUtil(context).getInt(TODAY_SCORE, 0);
        } else {
            //如果缓存的最近一次获取元宝的日期为0或者不等于当天的日期，表示用户当天还没获取过元宝
            //将缓存中的当天元宝获取清空
            PreferenceUtil.getPreferenceUtil(context).put(TODAY_SCORE, 0);
        }
        return todayScores;
    }

    public void setTodayScore(Context context, int todayScore) {
        int lastSaveScoreDay = PreferenceUtil.getPreferenceUtil(context).getInt(DAY, 0);
        Calendar c = Calendar.getInstance();
        int nowDay = c.get(Calendar.DAY_OF_MONTH);
        //如果缓存的最近一次获取元宝的日期不等于0并且等于当天的日期，表示用户当天有过获取元宝的操作。
        //这时累加元宝到用户的当日获取元宝数和总获取元宝数
        if (lastSaveScoreDay != 0 && nowDay == lastSaveScoreDay) {
            this.todayScore = todayScore + PreferenceUtil.getPreferenceUtil(context).getInt(TODAY_SCORE, 0);
        } else {
            //如果缓存的最近一次获取元宝的日期为0或者不等于当天的日期，表示用户当天还没获取过元宝
            this.todayScore += todayScore;
        }
        PreferenceUtil.getPreferenceUtil(context).put(TODAY_SCORE, this.todayScore);
    }

    public int getHistoryScore(Context context) {
        return PreferenceUtil.getPreferenceUtil(context).getInt(HISTORY_SCORE, 0);
    }

    public void setHistoryScore(Context context, int lastScore) {
        PreferenceUtil.getPreferenceUtil(context).put(HISTORY_SCORE, lastScore);
    }


}
