package com.wdg.chat.project.util;

import android.os.Handler;
import android.os.Looper;

import cn.smssdk.EventHandler;


/**
 * 短信验证回调监听
 * Created by HuangBin on 2017/9/17.
 */
public class SMEventHandler extends EventHandler{

    private Handler handler;
    private CallBackTask task;

    public SMEventHandler(){
        handler = new Handler(Looper.getMainLooper());
        task = new CallBackTask();
    }

    public void onRegister() {
        task.type = 0;
        handler.post(task);
    }

    public void beforeEvent(int var1, Object var2) {
        task.type = 1;
        task.var1 = var1;
        task.var3 = var2;
        handler.post(task);
    }

    public void afterEvent(int var1, int var2, Object var3) {
        task.type = 2;
        task.var1 = var1;
        task.var2 = var2;
        task.var3 = var3;
        handler.post(task);
    }

    public void onUnregister() {
        task.type = 3;
        handler.post(task);
    }

    private class CallBackTask implements Runnable {

        private int type;
        private int var1, var2;
        private Object var3;

        @Override
        public void run() {
            switch (type){
                case 0:
                    ui_onRegister();
                    break;
                case 1:
                    ui_onBeforeEvent(var1, var3);
                    break;
                case 2:
                    ui_onAfterEvent(var1, var2, var3);
                    break;
                case 3:
                    ui_onUnregister();
                    break;
            }
        }

    }

    public void ui_onRegister() {  }

    public void ui_onBeforeEvent(int var1, Object var2) {  }

    public void ui_onAfterEvent(int var1, int var2, Object var3) {  }

    public void ui_onUnregister() {  }

}
