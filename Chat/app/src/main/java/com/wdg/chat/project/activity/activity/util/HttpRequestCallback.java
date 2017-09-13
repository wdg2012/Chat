package com.wdg.chat.project.activity.activity.util;

import java.lang.reflect.Type;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import okhttp3.Headers;

/**
 * 请求回调接口
 * Created by HuangBin on 2017/9/13.
 */
public class HttpRequestCallback<T> extends BaseHttpRequestCallback<String> {

    private Type clsType;

    public HttpRequestCallback(){
        type = ClsTypeReflect.getModelClazz(getClass().getSuperclass());
        clsType = ClsTypeReflect.getModelClazz(getClass());
    }

    @Override
    protected void onSuccess(Headers headers, String s) {
        super.onSuccess(headers, s);
        onRespSuccess(headers, (T) GsonUtil.fromJson(s, clsType));
    }

    @Override
    protected void onSuccess(String s) {
        super.onSuccess(s);
        onRespSuccess((T) GsonUtil.fromJson(s, clsType));
    }

    protected void onRespSuccess(Headers headers, T t) {

    }

    protected void onRespSuccess(T t) {

    }

}
