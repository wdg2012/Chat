package com.wdg.chat.project.mvp.contract;

import com.wdg.chat.project.bean.VerCodeBean;

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
