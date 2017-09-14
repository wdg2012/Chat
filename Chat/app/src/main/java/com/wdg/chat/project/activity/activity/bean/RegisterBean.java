package com.wdg.chat.project.activity.activity.bean;

import java.util.List;


/**
 * 注册信息
 * Created by HuangBin on 2017/9/14.
 */
public class RegisterBean {

    /**
     * code : 101
     * error : 注册成功
     * list : []
     * obj : {}
     */

    private String code;
    private String error;
    private ObjBean obj;
    private List<?> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static class ObjBean {
    }
}
