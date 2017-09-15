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
    }

    interface View {
        void showDialog();
        void dismissDialog();
        void verCodeResp(VerCodeBean verCodeBean);
    }

    interface Presenter {
        void obtainVerCode(String phone);
    }

}
