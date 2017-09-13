package mvp.model;

import com.wdg.chat.project.activity.activity.bean.RespData;
import com.wdg.chat.project.activity.activity.bean.VerCode;
import com.wdg.chat.project.activity.activity.util.HttpRequestCallback;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import mvp.contract.LoginContract;
import mvp.contract.RegisterContract;


/**
 * 注册模型
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class RegisterModel implements RegisterContract.Model {

    /**
     * 获取验证码
     * @param phone
     * @param callback
     */
    @Override
    public void obtainVerCode(String phone, HttpRequestCallback<RespData<VerCode>> callback) {
        //创建参数
        RequestParams params = new RequestParams();
        params.addFormDataPart("phone", phone);
        //发送请求
        HttpRequest.post("http://47.93.21.48:8080/ssm_war/user/getvercode", params, callback);
    }

    /**
     * 注册
     * @param phone
     * @param password
     * @param country
     * @param headPhoto
     * @param ver_code
     * @param user_nick
     * @param callback
     */
    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code,
                         String user_nick, HttpRequestCallback<RespData> callback) {
        //创建参数
        RequestParams params = new RequestParams();
        params.addFormDataPart("phone", phone);
        params.addFormDataPart("password", password);
        params.addFormDataPart("country", country);
        params.addFormDataPart("headPhoto", headPhoto);
        params.addFormDataPart("ver_code", ver_code);
        params.addFormDataPart("user_nick", user_nick);
        //发送请求
        HttpRequest.post("http://47.93.21.48:8080/ssm_war/user/register", params, callback);
    }

}
