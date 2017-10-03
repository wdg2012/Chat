package com.wdg.chat.project.mvp.presenter;

import com.wdg.chat.project.bean.RegisterBean;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import com.wdg.chat.project.mvp.contract.VerCodeContract;
import com.wdg.chat.project.mvp.model.VerCodeModel;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class VerCodePresenter implements VerCodeContract.Presenter {

    private VerCodeContract.View mVerCodeView;
    private VerCodeContract.Model mVerCodeModel;

    public VerCodePresenter(VerCodeContract.View verCodeView){
        mVerCodeView = verCodeView;
        mVerCodeModel = new VerCodeModel();
    }

    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code, String user_nick) {
        if(mVerCodeView != null){
            mVerCodeView.showDialog();
        }
        //注册
        mVerCodeModel.register(phone, password,
                                country, headPhoto,
                                ver_code, user_nick, new BaseHttpRequestCallback<RegisterBean>(){

            @Override
            protected void onSuccess(RegisterBean registerBean) {
                super.onSuccess(registerBean);
                if(mVerCodeView != null){
                    mVerCodeView.dismissDialog();
                    mVerCodeView.registerResp(registerBean);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if(mVerCodeView != null){
                    mVerCodeView.dismissDialog();
                }
            }

        });
    }

}