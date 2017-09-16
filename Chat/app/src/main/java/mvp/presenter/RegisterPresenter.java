package mvp.presenter;

import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import mvp.contract.RegisterContract;
import mvp.model.RegisterModel;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mRegisterView;
    private RegisterContract.Model mRegisterModel;

    public RegisterPresenter(RegisterContract.View registerView){
        mRegisterView = registerView;
        mRegisterModel = new RegisterModel();
    }

    @Override
    public void obtainVerCode(String phone) {
        if(mRegisterView != null){
            mRegisterView.showDialog();
        }
        //获取验证码
        mRegisterModel.obtainVerCode(phone, new BaseHttpRequestCallback<VerCodeBean>(){

            @Override
            protected void onSuccess(VerCodeBean verCodeBean) {
                super.onSuccess(verCodeBean);
                if(mRegisterView != null){
                    mRegisterView.dismissDialog();
                    mRegisterView.verCodeResp(verCodeBean);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if(mRegisterView != null){
                    mRegisterView.dismissDialog();
                }
            }
        });
    }

}
