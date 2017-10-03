package com.wdg.chat.project.mvp.model;

import com.wdg.chat.project.bean.VerCodeBean;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import com.wdg.chat.project.mvp.contract.RegisterContract;


/**
 * 注册模型
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class RegisterModel implements RegisterContract.Model {

    /**
     * 获取验证码
     * @param phone
     * @param callback
     */
    @Override
    public void obtainVerCode(String phone, BaseHttpRequestCallback<VerCodeBean> callback) {
        //创建参数
        RequestParams params = new RequestParams();
        params.addFormDataPart("phone", phone);
        //发送请求
        HttpRequest.post("http://47.93.21.48:8080/ssm_war/user/getvercode", params, callback);
    }

}
