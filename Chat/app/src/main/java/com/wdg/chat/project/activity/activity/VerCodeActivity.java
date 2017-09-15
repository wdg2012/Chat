package com.wdg.chat.project.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.bean.RegisterBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import mvp.contract.VerCodeContract;
import mvp.presenter.VerCodePresenter;

import static com.wdg.chat.project.R.id.etCountry;
import static com.wdg.chat.project.R.id.etNickName;
import static com.wdg.chat.project.R.id.etPassword;


/**
 * 验证码页面
 * Created by HuangBin on 2017/9/15.
 */
public class VerCodeActivity extends BaseActivity implements VerCodeContract.View {

    private final long TIME = 60 * 1000;
    private final long INTERVAL = 1000;

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

    private CountDownTimer countDownTimer = new CountDownTimer(TIME, INTERVAL) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvVerCodeInfo.setText("接收短信大约需要" + (millisUntilFinished / 1000) + "秒钟");
        }

        @Override
        public void onFinish() {
            tvVerCodeInfo.setText("收不到验证码?");
            initView();
        }

    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0){
                btnRegister.setEnabled(true);
                btnRegister.setBackgroundColor(getResources().getColor(R.color.color_green1));
            }else{
                btnRegister.setEnabled(false);
                btnRegister.setBackgroundColor(getResources().getColor(R.color.color_green2));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        ButterKnife.bind(this);
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
        mPresenter = new VerCodePresenter(this);
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private void initView(){
        tvPhoneNumber.setText(country + " " + phone);
        etVerCode.getText().clear();
        etVerCode.addTextChangedListener(textWatcher);
        btnRegister.setEnabled(false);
        btnRegister.setBackgroundColor(getResources().getColor(R.color.color_green2));
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
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
                mPresenter.obtainVerCode(phone);
                break;
        }
    }

    @Override
    public void showDialog() {
        if(prgDialog != null && !prgDialog.isShowing()){
            prgDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if(prgDialog != null && prgDialog.isShowing()){
            prgDialog.dismiss();
        }
    }

    @Override
    public void verCodeResp(VerCodeBean verCodeBean) {
        if("101".equals(verCodeBean.getCode())){
            Log.d("VerCode", verCodeBean.getObj().getVer_code());
            countDownTimer.cancel();
            countDownTimer.start();
        }else{
            Toast.makeText(this, verCodeBean.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerResp(RegisterBean registerBean) {
        Toast.makeText(this, registerBean.getError(), Toast.LENGTH_SHORT).show();
        if("101".equals(registerBean.getCode())){
            setResult(RESULT_OK);
        }else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
