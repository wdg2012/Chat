package mvp.presenter;

import com.wdg.chat.project.activity.activity.bean.UserBean;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import mvp.contract.LoginContract;
import mvp.model.LoginModel;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private LoginContract.Model mLoginModel;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String phone, String password) {
        if(mLoginView != null){
            mLoginView.showDialog();
        }
        mLoginModel.login(phone, password,
                new BaseHttpRequestCallback<UserBean>(){

            @Override
            protected void onSuccess(UserBean userBean) {
                super.onSuccess(userBean);
                if(mLoginView != null){
                    mLoginView.dismissDialog();
                    mLoginView.loginResp(userBean);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if(mLoginView != null){
                    mLoginView.dismissDialog();
                }
            }

        });
    }

}
