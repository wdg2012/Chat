package mvp.presenter;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import mvp.contract.LoginContract;
import mvp.model.LoginModel;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mLoginView;
    private LoginContract.Model mModel;


    public LoginPresenter(final LoginContract.View loginView) {
        mLoginView = loginView;
        mModel = new LoginModel();
    }

    @Override
    public void test(String id) {
        mModel.test(id, new BaseHttpRequestCallback<String>(){

            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                if(mLoginView != null){
                    mLoginView.onTestResp(s);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

        });
    }

}
