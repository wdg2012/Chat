package com.wdg.chat.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdg.chat.project.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import com.wdg.chat.project.mvp.contract.VerCodeContract;
import com.wdg.chat.project.mvp.presenter.VerCodePresenter;


/**
 * 验证码页面
 * Created by HuangBin on 2017/9/15.
 */
public class VerCodeActivity extends BaseActivity
        implements VerCodeContract.View {

    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.etVerCode)
    EditText etVerCode;
    @BindView(R.id.tvVerCodeInfo)
    TextView tvVerCodeInfo;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private VerCodePresenter mPresenter;
    private String phone, password, country, user_nick;
    private File photoFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        ButterKnife.bind(this);
        mPresenter = new VerCodePresenter(this);
        SMSSDK.registerEventHandler(mPresenter.getEventHandler());
        Intent intent = getIntent();
        //获取数据
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
        country = intent.getStringExtra("country");
        user_nick = intent.getStringExtra("user_nick");
        String headPhoto = intent.getStringExtra("headPhoto");
        if(!TextUtils.isEmpty(headPhoto)){
            photoFile = new File(headPhoto);
        }
        initView();
        mPresenter.startTimer();
    }

    private void initView(){
        tvPhoneNumber.setText(country + " " + phone);
        etVerCode.getText().clear();
        etVerCode.addTextChangedListener(this);
        btnRegister.setEnabled(false);
        btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
    }

    @Override
    protected void onDestroy() {
        mPresenter.cancelTimer();
        SMSSDK.unregisterEventHandler(mPresenter.getEventHandler());
        super.onDestroy();
    }

    @OnClick({R.id.btnRegister, R.id.tvVerCodeInfo})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                mPresenter.register(phone, password,
                        country, photoFile,
                        etVerCode.getText().toString(), user_nick);
                break;
            case R.id.tvVerCodeInfo:
                mPresenter.getVerCode(country, phone);
                break;
        }
    }

    @Override
    public void onTickTime(long secTime) {
        tvVerCodeInfo.setText("接收短信大约需要" + secTime + "秒钟");
    }

    @Override
    public void onFinishTime() {
        tvVerCodeInfo.setText("收不到验证码?");
        initView();
    }

    @Override
    public void setActivityResult(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 0){
            btnRegister.setEnabled(true);
            btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_enable));
        }else{
            btnRegister.setEnabled(false);
            btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
        }
    }

}
