package com.wdg.chat.project.mvp.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.wdg.chat.project.mvp.contract.RegisterContract;
import cn.smssdk.SMSSDK;


/**
 * 注册模型
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class RegisterModel implements RegisterContract.Model {

    @Override
    public void getVerCode(String country, String phone) {
        SMSSDK.getVerificationCode(country, phone);
    }

    @Override
    public boolean validate(Context context,
                            String nickName, String photo, String phoneNumber, String password) {
        boolean result = true;
        String error = "";

        if(TextUtils.isEmpty(nickName)){
            error = "昵称不能为空!";
            result = false;
        }
        else if(TextUtils.isEmpty(photo)){
            error = "头像不能为空!";
            result = false;
        }
        else if(TextUtils.isEmpty(phoneNumber)){
            error = "手机号不能为空!";
            result = false;
        }
        else if(phoneNumber.length() != 11){
            error = "手机号必须为11位!";
            result = false;
        }
        else if(TextUtils.isEmpty(password)){
            error = "密码不能为空!";
            result = false;
        }
        else if(password.length() < 6 ){
            error = "密码不能小于6位!";
            result = false;
        }

        if(!result){
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }

        return result;
    }

}
