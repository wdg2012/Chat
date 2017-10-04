package com.wdg.chat.project.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.wdg.chat.project.mvp.contract.BaseContract;
import com.wdg.chat.project.util.DialogUtil;

/**
 * Created by ${吴登赶} on 2017/8/27.
 */
public class BaseActivity extends AppCompatActivity
        implements BaseContract.View,
        TextWatcher {

    protected Dialog prgDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //创建进度条对话框
        prgDialog = DialogUtil.builder(this, DialogUtil.TYPE_PROGRESS)
                .setMessage("正在加载....")
                .create();
    }

    @Override
    protected void onDestroy() {
        dismissDialog();
        prgDialog = null;
        super.onDestroy();
    }

    @Override
    public void showDialog(){
        if(prgDialog != null && !prgDialog.isShowing()){
            prgDialog.show();
        }
    }

    @Override
    public void dismissDialog(){
        if(prgDialog != null && prgDialog.isShowing()){
            prgDialog.dismiss();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s,
                                  int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s,
                              int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
