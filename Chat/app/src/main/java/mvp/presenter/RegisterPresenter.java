package mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdg.chat.project.activity.activity.bean.RespData;

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


    public RegisterPresenter(final RegisterContract.View registerView){
        mRegisterView = registerView;
        mRegisterModel = new RegisterModel();
    }

    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code, String user_nick) {

        //注册
        mRegisterModel.register(phone, password,
                                country, headPhoto,
                                ver_code, user_nick, new BaseHttpRequestCallback<String>(){

            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                if(mRegisterView != null){
                    Gson gson = new Gson();
                    RespData respData = gson.fromJson(s, new TypeToken<RespData>(){}.getType());
                    mRegisterView.registerResp(respData);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

        });
    }

}
