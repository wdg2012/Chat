package com.wdg.chat.project.activity.activity.bean;

import java.util.List;

/**
 * 响应类
 * Created by HuangBin on 2017/9/11.
 */
public class RespData<T> {

    private int code;
    private String error;
    private List<T> list;
    private T obj;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
