package com.wdg.chat.project.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.wdg.chat.project.MyApp;
import com.wdg.chat.project.activity.VerCodeActivity;
import com.wdg.chat.project.bean.RegisterBean;

import java.io.File;
import com.wdg.chat.project.mvp.contract.VerCodeContract;
import com.wdg.chat.project.mvp.model.VerCodeModel;
import com.wdg.chat.project.util.NetSubscriber;
import com.wdg.chat.project.util.SMEventHandler;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class VerCodePresenter implements VerCodeContract.Presenter {

    private final long TIME = 60 * 1000;
    private final long INTERVAL = 1000;

    private VerCodeContract.View mVerCodeView;
    private VerCodeContract.Model mVerCodeModel;
    private Context mContext;
    private CountDownTimer mCountDownTimer;

    private EventHandler eventHandler = new SMEventHandler() {

        public void ui_onAfterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable)data;
                String msg = throwable.getMessage();
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            } else {
                //获取验证码
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    startTimer();
                }
            }
        }

    };

    public VerCodePresenter(VerCodeContract.View verCodeView){
        mVerCodeView = verCodeView;
        checkViewNull();
        mVerCodeModel = new VerCodeModel();
        mContext = MyApp.getInstance();
    }

    @Override
    public void register(String phone,
                         String password,
                         String country,
                         File headPhoto,
                         String ver_code, String user_nick) {
        mVerCodeView.showDialog();
        //注册
        mVerCodeModel.register(phone, password,
                country, headPhoto,
                ver_code, user_nick,
                new NetSubscriber<RegisterBean>(){

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        super.onNext(registerBean);
                        mVerCodeView.dismissDialog();
                        Toast.makeText(mContext, registerBean.getError(), Toast.LENGTH_SHORT).show();
                        mVerCodeView.setActivityResult("101".equals(registerBean.getCode())
                                ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mVerCodeView.dismissDialog();
                    }

                });
    }

    @Override
    public void getVerCode(String country, String phone) {
        mVerCodeModel.getVerCode(country, phone);
    }

    @Override
    public void startTimer() {
        if(mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(TIME, INTERVAL) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mVerCodeView.onTickTime(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    mVerCodeView.onFinishTime();
                }

            };
        }
        else {
            cancelTimer();
        }
        //启动倒计时
        mCountDownTimer.start();
    }

    @Override
    public void cancelTimer() {
        //取消倒计时
        mCountDownTimer.cancel();
    }

    @Override
    public void checkViewNull() {
        if(mVerCodeView == null){
            throw new RuntimeException("VerCodeContract.View 不能为空!");
        }
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

}
