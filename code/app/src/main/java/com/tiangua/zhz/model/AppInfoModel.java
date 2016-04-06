package com.tiangua.zhz.model;

/**
 * Created by adamFeng on 2016/1/20.
 */
public class AppInfoModel {

    private int appId;//appId
    private String appName;//app名称
    private long appSize;//app大小
    private String appPkg;//app包名
    private String icon;//app icon
    private String introduce;//一句话介绍
    private String content;//app详情介绍
    private String downUrl;//下载url
    private int downNum;//下载次数
    private String[] screenShot;//推广app的截屏
    private int score;//推广app的总元宝数
    private int type;//app的元宝任务 0:一次获取全部元宝 1:有留存任务，元宝批次获取 2：所有元宝获取完
    private ActiveTaskMoel[] activeTaskMoel; //app留存任务数组

    public ActiveTaskMoel[] getActiveTaskMoel() {
        return activeTaskMoel;
    }

    public void setActiveTaskMoel(ActiveTaskMoel[] activeTaskMoel) {
        this.activeTaskMoel = activeTaskMoel;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPkg() {
        return appPkg;
    }

    public void setAppPkg(String appPkg) {
        this.appPkg = appPkg;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDownNum() {
        return downNum;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[] getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String[] screenShot) {
        this.screenShot = screenShot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
