package com.wdg.chat.project.util;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${wdgan} on 2017/9/16 0016.
 * 邮箱18149542718@163
 */
public class RetrofitUtils {
    private static final String API_SERVER = "http://47.93.21.48:8080/ssm_war/"; // 服务器的地址
    private static Retrofit sRetrofit;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取 Retrofit 客户端
     * @return
     */
    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            if (null == mOkHttpClient) {
                mOkHttpClient = createOkHttp();
            }
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .client(mOkHttpClient)
                    .build();
        }
        return sRetrofit;
    }

    /**
     * 创建请求的Servcie
     * @param sClass
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> sClass) {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(sClass);
    }

    /**
     * 创建okHttp 客户端
     * @return
     */
    private static OkHttpClient createOkHttp() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        OkHttpClient OkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        lock.unlock();
        return OkHttpClient;
    }
}
