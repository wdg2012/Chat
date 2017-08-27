package com.wdg.chat.project.activity.activity.app;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by ${吴登赶} on 2017/8/27.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), " 88380eb943", false);
//        CrashReport.initCrashReport(getApplicationContext(), "88380eb943", false);
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

    }
}
