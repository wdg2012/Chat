package mvp.model;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import mvp.contract.LoginContract;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public void loginInfo() {

    }

    @Override
    public void test(String id, BaseHttpRequestCallback<String> callback) {
        RequestParams params = new RequestParams();
        params.addFormDataPart("id", "1000");
        HttpRequest.get("http://47.93.21.48:8080/ssm/use/list", params, callback);
    }

}
