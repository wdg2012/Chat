package com.wdg.chat.project.mvp.presenter;

import android.content.Context;
import android.widget.Toast;

import com.wdg.chat.project.MyApp;
import com.wdg.chat.project.bean.UserBean;

import com.wdg.chat.project.mvp.contract.LoginContract;
import com.wdg.chat.project.mvp.model.LoginModel;
import com.wdg.chat.project.util.NetSubscriber;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private LoginContract.Model mLoginModel;
    private Context mContext;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = loginView;
        checkViewNull();
        mLoginModel = new LoginModel();
        mContext = MyApp.getInstance();
    }

    @Override
    public void login(String phone, String password) {
        mLoginView.showDialog();
        mLoginModel.login(phone, password,
                new NetSubscriber<UserBean>(){

                    @Override
                    public void onNext(UserBean userBean) {
                        super.onNext(userBean);
                        mLoginView.dismissDialog();
                        if("101".equals(userBean.getCode())){
                            mLoginView.toMainActivity(userBean);
                        }else{
                            Toast.makeText(mContext, userBean.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mLoginView.dismissDialog();
                    }

                });

    }

    @Override
    public void checkViewNull() {
        if(mLoginView == null){
            throw new RuntimeException("LoginContract.View 不能为空!");
        }
    }

}
