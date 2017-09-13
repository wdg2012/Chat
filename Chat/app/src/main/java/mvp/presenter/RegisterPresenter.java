package mvp.presenter;

import android.app.Dialog;

import com.wdg.chat.project.activity.activity.bean.RespData;
import com.wdg.chat.project.activity.activity.bean.VerCode;
import com.wdg.chat.project.activity.activity.util.HttpRequestCallback;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import mvp.contract.LoginContract;
import mvp.contract.RegisterContract;
import mvp.model.LoginModel;
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
        mRegisterModel.obtainVerCode(phone, new HttpRequestCallback<RespData<VerCode>>(){

            @Override
            protected void onRespSuccess(RespData<VerCode> respData) {
                super.onRespSuccess(respData);
                dismissDialog();
                if(mRegisterView != null){
                    mRegisterView.verCodeResp(respData);
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
                                ver_code, user_nick, new HttpRequestCallback<RespData>(){

            @Override
            protected void onRespSuccess(RespData respData) {
                super.onRespSuccess(respData);
                dismissDialog();
                if(mRegisterView != null){
                    mRegisterView.registerResp(respData);
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
