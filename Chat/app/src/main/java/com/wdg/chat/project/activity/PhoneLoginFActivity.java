package com.wdg.chat.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdg.chat.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机号登录页面
 * Created by HuangBin on 2017/9/16.
 */
public class PhoneLoginFActivity extends BaseActivity {

    @BindView(R.id.etCountry)
    EditText etCountry;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.btnNext)
    Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login_f);
        ButterKnife.bind(this);
        etPhoneNumber.addTextChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                Intent intent = new Intent(this, PhoneLoginSActivity.class);
                //保存数据
                intent.putExtra("country", etCountry.getText().toString());
                intent.putExtra("phone", etPhoneNumber.getText().toString());
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 0){
            btnNext.setEnabled(true);
            btnNext.setBackground(getResources().getDrawable(R.drawable.comm_btn_enable));
        }else{
            btnNext.setEnabled(false);
            btnNext.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
        }
    }

}
