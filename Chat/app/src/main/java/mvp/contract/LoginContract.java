package mvp.contract;

import com.wdg.chat.project.activity.activity.bean.UserBean;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface LoginContract {

    interface Model extends BaseContract.Model{
        void login(String phone, String password,
                   BaseHttpRequestCallback<UserBean> callback);
    }

    interface View extends BaseContract.View{
        void loginResp(UserBean userBean);
    }

    interface Presenter extends BaseContract.Presenter{
        void login(String phone, String password);
    }

}
