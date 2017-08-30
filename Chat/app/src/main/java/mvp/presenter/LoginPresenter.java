package mvp.presenter;

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
}
