package com.wdg.chat.project.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.wdg.chat.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RequestParams params = new RequestParams();
//        HttpRequest.get("https://www.baidu.com/index.php?tn=02049043_6_pg",params,new BaseHttpRequestCallback<String>(){
//            @Override
//            protected void onSuccess(String s) {
//                super.onSuccess(s);
//                tv.setText(s);
//
//            }
//        });

    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
        Beta.checkUpgrade();
    }
}
