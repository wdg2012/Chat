package com.wdg.chat.project.mvp.contract;

import com.wdg.chat.project.bean.UserBean;
import com.wdg.chat.project.util.NetSubscriber;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface LoginContract {

    interface Model extends BaseContract.Model{
        void login(String phone, String password,
                   NetSubscriber<UserBean> subscriber);
    }

    interface View extends BaseContract.View{
        void toMainActivity(UserBean userBean);
    }

    interface Presenter extends BaseContract.Presenter{
        void login(String phone, String password);
    }

}
