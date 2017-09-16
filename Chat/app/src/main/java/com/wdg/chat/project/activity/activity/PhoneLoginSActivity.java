package com.wdg.chat.project.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.bean.LoginBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvp.contract.LoginContract;
import mvp.presenter.LoginPresenter;

/**
 * 手机号登录页面
 * Created by HuangBin on 2017/9/16.
 */
public class PhoneLoginSActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private String country, phone;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login_s);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
        //获取数据
        Intent intent = getIntent();
        country = intent.getStringExtra("country");
        phone = intent.getStringExtra("phone");
        etPhoneNumber.setText(country + phone);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnLogin)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                mPresenter.login(phone, etPassword.getText().toString());
                break;
        }
    }

    @Override
    public void loginResp(LoginBean loginBean) {
        if("101".equals(loginBean.getCode())){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, loginBean.getError(), Toast.LENGTH_SHORT).show();
        }
    }

}
