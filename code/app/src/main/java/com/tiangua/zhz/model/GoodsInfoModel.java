package com.tiangua.zhz.model;

/**
 * Created by adamFeng on 2016/1/20.
 * 提现商品
 */
public class GoodsInfoModel {
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
