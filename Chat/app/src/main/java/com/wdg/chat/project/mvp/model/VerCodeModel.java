package com.wdg.chat.project.mvp.model;

import com.wdg.chat.project.api_service.UserService;
import com.wdg.chat.project.bean.RegisterBean;

import java.io.File;
import com.wdg.chat.project.mvp.contract.VerCodeContract;
import com.wdg.chat.project.util.NetSubscriber;
import com.wdg.chat.project.util.RetrofitUtils;

import cn.smssdk.SMSSDK;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 验证码模型
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class VerCodeModel implements VerCodeContract.Model {

    /**
     * 注册
     * @param phone
     * @param password
     * @param country
     * @param headPhoto
     * @param ver_code
     * @param user_nick
     * @param subscriber
     */
    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code,
                         String user_nick, NetSubscriber<RegisterBean> subscriber) {
        UserService userService = RetrofitUtils.createService(UserService.class);
        //创建参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), headPhoto);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("phone", phone)
                .addFormDataPart("password", password)
                .addFormDataPart("country", country)
                .addFormDataPart("file", headPhoto.getName(), fileBody)
                .addFormDataPart("ver_code", ver_code)
                .addFormDataPart("user_nick", user_nick)
                .build();
        //发送请求
        userService.register(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getVerCode(String country, String phone) {
        SMSSDK.getVerificationCode(country, phone);
    }

}
