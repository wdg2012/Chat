package com.wdg.chat.project.mvp.contract;

import com.wdg.chat.project.bean.RegisterBean;
import com.wdg.chat.project.util.NetSubscriber;

import java.io.File;


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
                      String user_nick, NetSubscriber<RegisterBean> subscriber);
        void getVerCode(String country, String phone);
    }

    interface View extends BaseContract.View{
        void setActivityResult(int resultCode);
        void onTickTime(long secTime);
        void onFinishTime();
    }

    interface Presenter extends BaseContract.Presenter{
        void register(String phone,
                      String password,
                      String country,
                      File headPhoto,
                      String ver_code, String user_nick);
        void getVerCode(String country, String phone);
        void startTimer();
        void cancelTimer();
    }

}
