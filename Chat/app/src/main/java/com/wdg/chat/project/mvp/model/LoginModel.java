package com.wdg.chat.project.mvp.model;

import com.wdg.chat.project.api_service.UserService;
import com.wdg.chat.project.bean.UserBean;
import com.wdg.chat.project.mvp.contract.LoginContract;
import com.wdg.chat.project.util.NetSubscriber;
import com.wdg.chat.project.util.RetrofitUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginModel implements LoginContract.Model {

    /**
     * 登录
     * @param phone
     * @param password
     * @param subscriber
     */
    @Override
    public void login(String phone, String password,
                      NetSubscriber<UserBean> subscriber) {
        UserService userService = RetrofitUtils.createService(UserService.class);
        //发送请求
        userService.login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
