package mvp.presenter;

import android.app.Dialog;

import com.wdg.chat.project.activity.activity.bean.RegisterBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.io.File;

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
    private Dialog mDialog;


    public RegisterPresenter(final RegisterContract.View registerView, Dialog dialog){
        mRegisterView = registerView;
        mRegisterModel = new RegisterModel();
        mDialog = dialog;
    }

    @Override
    public void showDialog(){
        if(mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog(){
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void obtainVerCode(String phone) {
        showDialog();
        //获取验证码
        mRegisterModel.obtainVerCode(phone, new BaseHttpRequestCallback<VerCodeBean>(){

            @Override
            protected void onSuccess(VerCodeBean verCodeBean) {
                super.onSuccess(verCodeBean);
                //dismissDialog();
                if(mRegisterView != null){
                    mRegisterView.verCodeResp(verCodeBean);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                dismissDialog();
            }
        });
    }

    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code, String user_nick) {
        showDialog();
        //注册
        mRegisterModel.register(phone, password,
                                country, headPhoto,
                                ver_code, user_nick, new BaseHttpRequestCallback<RegisterBean>(){

            @Override
            protected void onSuccess(RegisterBean registerBean) {
                super.onSuccess(registerBean);
                dismissDialog();
                if(mRegisterView != null){
                    mRegisterView.registerResp(registerBean);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                dismissDialog();
            }

        });
    }

}
