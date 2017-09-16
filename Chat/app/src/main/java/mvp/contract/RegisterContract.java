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

    interface Model extends BaseContract.Model{
        void obtainVerCode(String phone, BaseHttpRequestCallback<VerCodeBean> callback);
    }

    interface View extends BaseContract.View{
        void verCodeResp(VerCodeBean verCodeBean);
    }

    interface Presenter extends BaseContract.Presenter{
        void obtainVerCode(String phone);
    }

}
