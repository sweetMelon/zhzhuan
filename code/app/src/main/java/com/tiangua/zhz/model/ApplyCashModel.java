package com.tiangua.zhz.model;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class ApplyCashModel {

    private int cashId;//提现记录id
    private int cashAmount;//提现金额
    private int useScore;//提现花费了多少元宝
    private int goodsId;//提现商品id
    private String account;//提现的账号
    private int state;//提现状态 0:失败 1:成功 2：审核中
    private int uid; //提现用户
    private String time; //提现时间

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public int getCashId() {
        return cashId;
    }

    public void setCashId(int cashId) {
        this.cashId = cashId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUseScore() {
        return useScore;
    }

    public void setUseScore(int useScore) {
        this.useScore = useScore;
    }
}
