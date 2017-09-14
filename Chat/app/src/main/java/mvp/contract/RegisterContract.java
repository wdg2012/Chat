package mvp.contract;

import com.wdg.chat.project.activity.activity.bean.RegisterBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface RegisterContract {

    interface Model {
        void obtainVerCode(String phone, BaseHttpRequestCallback<VerCodeBean> callback);
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code,
                      String user_nick, BaseHttpRequestCallback<RegisterBean> callback);
    }

    interface View {
        void showDialog();
        void dismissDialog();
        void verCodeResp(VerCodeBean verCodeBean);
        void registerResp(RegisterBean registerBean);
    }

    interface Presenter {
        void obtainVerCode(String phone);
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code, String user_nick);
    }

}
