package com.wdg.chat.project.mvp.model;

import com.wdg.chat.project.bean.UserBean;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import com.wdg.chat.project.mvp.contract.LoginContract;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginModel implements LoginContract.Model {

    /**
     * 登录
     * @param phone
     * @param password
     * @param callback
     */
    @Override
    public void login(String phone, String password,
                      BaseHttpRequestCallback<UserBean> callback) {
        //创建参数
        RequestParams params = new RequestParams();
        params.addFormDataPart("phone", phone);
        params.addFormDataPart("password", password);
        //发送请求
        HttpRequest.post("http://47.93.21.48:8080/ssm_war/user/login", params, callback);
    }

}
