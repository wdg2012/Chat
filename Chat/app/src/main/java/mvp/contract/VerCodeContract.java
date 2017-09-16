package mvp.contract;

import com.wdg.chat.project.activity.activity.bean.RegisterBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by HuangBin on 2017/9/15.
 */
public interface VerCodeContract {

    interface Model extends BaseContract.Model{
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code,
                      String user_nick, BaseHttpRequestCallback<RegisterBean> callback);
    }

    interface View extends BaseContract.View{
        void registerResp(RegisterBean registerBean);
    }

    interface Presenter extends BaseContract.Presenter{
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code, String user_nick);
    }

}
