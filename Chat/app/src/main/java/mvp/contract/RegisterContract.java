package mvp.contract;

import android.widget.ImageView;

import com.wdg.chat.project.activity.activity.bean.RespData;
import com.wdg.chat.project.activity.activity.bean.VerCode;
import com.wdg.chat.project.activity.activity.util.HttpRequestCallback;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface RegisterContract {

    interface Model {
        void obtainVerCode(String phone, HttpRequestCallback<RespData<VerCode>> callback);
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code,
                      String user_nick, HttpRequestCallback<RespData> callback);
    }

    interface View {
        void verCodeResp(RespData<VerCode> respData);
        void registerResp(RespData respData);
    }

    interface Presenter {
        void showDialog();
        void dismissDialog();
        void obtainVerCode(String phone);
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code, String user_nick);
    }

}
