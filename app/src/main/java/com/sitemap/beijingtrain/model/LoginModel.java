package com.sitemap.beijingtrain.model;

/**
 * @desc 登录的实体类
 * Created by chenmeng on 2016/11/14.
 */
public class LoginModel {
    private String result;//结果（1：成功，2：失败）
    private String errorMsg;//登录失败时的错误信息

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
