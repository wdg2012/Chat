package mvp.contract;

import android.widget.ImageView;

import com.wdg.chat.project.activity.activity.bean.RespData;

import java.io.File;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface RegisterContract {

    interface Model {
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code,
                      String user_nick, BaseHttpRequestCallback<String> callback);
    }

    interface View {
        void registerResp(RespData<String> respData);
    }

    interface Presenter {
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code, String user_nick);
    }

}
