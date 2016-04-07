package com.tiangua.zhz.model;

/**
 * Created by adamFeng on 2016/1/20.
 * 提现商品
 */
public class GoodsInfoModel {

    public static final int GOODS_ALIPAY = 0;
    public static final int GOODS_WECHAT = 1;
    public static final int GOODS_PHONE_CHARGE = 2;

    public static final int[] APPLY_CASH_PHONE_LIMIT = { 10, 30, 50, 100};
    public static final int[] APPLY_CASH_ALIPAY_LIMIT = { 5, 10, 30, 50, 100};
    public static final int[] APPLY_CASH_WECHAT_LIMIT = { 5, 10, 30, 50, 100};

    private int goodsId;
    private String goodsName;
    private int[] goodsScore;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int[] getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(int[] goodsScore) {
        this.goodsScore = goodsScore;
    }
}
