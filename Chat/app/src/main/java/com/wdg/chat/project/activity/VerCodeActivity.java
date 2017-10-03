package com.wdg.chat.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.chat.project.R;
import com.wdg.chat.project.bean.RegisterBean;
import com.wdg.chat.project.bean.VerCodeBean;
import com.wdg.chat.project.util.SMEventHandler;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import com.wdg.chat.project.mvp.contract.RegisterContract;
import com.wdg.chat.project.mvp.contract.VerCodeContract;
import com.wdg.chat.project.mvp.presenter.VerCodePresenter;



/**
 * 验证码页面
 * Created by HuangBin on 2017/9/15.
 */
public class VerCodeActivity extends BaseActivity
        implements VerCodeContract.View, RegisterContract.View {

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

    private VerCodePresenter mVerPresenter;
    //private RegisterPresenter mRegPresenter;
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
                btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_enable));
            }else{
                btnRegister.setEnabled(false);
                btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private EventHandler eventHandler = new SMEventHandler() {

        public void ui_onAfterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable)data;
                String msg = throwable.getMessage();
                Toast.makeText(VerCodeActivity.this, msg, Toast.LENGTH_SHORT).show();
                //Log.d("VerCode", msg);
            } else {
                //获取验证码
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //Toast.makeText(RegisterActivity.this, "收到验证码!", Toast.LENGTH_SHORT).show();
                    //Log.d("VerCode", "收到验证码!");
                    countDownTimer.cancel();
                    countDownTimer.start();
                }
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        ButterKnife.bind(this);
        SMSSDK.registerEventHandler(eventHandler);
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
        mVerPresenter = new VerCodePresenter(this);
        //mRegPresenter = new RegisterPresenter(this);
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private void initView(){
        tvPhoneNumber.setText(country + " " + phone);
        etVerCode.getText().clear();
        etVerCode.addTextChangedListener(textWatcher);
        btnRegister.setEnabled(false);
        btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }

    @OnClick({R.id.btnRegister, R.id.tvVerCodeInfo})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                mVerPresenter.register(phone, password,
                        country, photoFile,
                        etVerCode.getText().toString(), user_nick);
                break;
            case R.id.tvVerCodeInfo:
                //mRegPresenter.obtainVerCode(phone);
                SMSSDK.getVerificationCode(country, phone);
                break;
        }
    }

    @Override
    public void verCodeResp(VerCodeBean verCodeBean) {
/*        if("101".equals(verCodeBean.getCode())){
            Log.d("VerCode", verCodeBean.getObj().getVer_code());
            countDownTimer.cancel();
            countDownTimer.start();
        }else{
            Toast.makeText(this, verCodeBean.getError(), Toast.LENGTH_SHORT).show();
        }*/
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
