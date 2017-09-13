package com.wdg.chat.project.activity.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wdg.chat.project.R;

/**
 * Created by ${吴登赶} on 2017/8/27.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog prgDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //创建进度条对话框
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("正在加载数据......");
        prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgDialog.setCancelable(false);
        prgDialog.setCanceledOnTouchOutside(false);
    }
}
