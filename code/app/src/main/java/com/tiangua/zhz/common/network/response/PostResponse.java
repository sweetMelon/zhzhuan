package com.tiangua.zhz.common.network.response;

/**
 * Created by ktt on 2015/12/15.
 */
public class PostResponse {
    private int code;
    private String errMsg;

    public Object responseMsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
