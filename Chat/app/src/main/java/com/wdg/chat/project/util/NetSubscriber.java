package com.wdg.chat.project.util;

import rx.Subscriber;

/**
 * 请求回调监听
 * Created by HuangBin on 2017/10/3.
 */
public class NetSubscriber<T> extends Subscriber<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onCompleted();
    }

}
