package com.wdg.chat.project.activity.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.bean.UserBean;
import com.wdg.chat.project.activity.activity.util.SMEventHandler;
import com.wdg.chat.project.activity.activity.util.SharedPrfUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import mvp.contract.LoginContract;
import mvp.presenter.LoginPresenter;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.iv_head_image)
    ImageView mIvHeadImage;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private PopupWindow popupWindow;
    private LoginPresenter mPresenter;

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0){
                btnLogin.setEnabled(true);
                btnLogin.setBackground(getResources().getDrawable(R.drawable.comm_btn_enable));
            }else{
                btnLogin.setEnabled(false);
                btnLogin.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
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
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                //Log.d("VerCode", msg);
            } else {
                //获取验证码
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(LoginActivity.this, "收到验证码!", Toast.LENGTH_SHORT).show();
                    //Log.d("VerCode", "收到验证码!");
                }
            }
        }

    };

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        etPassword.addTextChangedListener(textWatcher);
        updateView(null);
        initPopupWindow();
        mPresenter = new LoginPresenter(this);
        SMSSDK.registerEventHandler(eventHandler);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventHandler);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void updateView(UserBean userBean){
        String photo = null;
        String phone = null;

        if(userBean == null) {
            Intent intent = getIntent();
            if (intent != null) {
                photo = intent.getStringExtra("photo");
                phone = intent.getStringExtra("phone");
            }
        }else{
            photo = userBean.getObj().getHead_path();
            phone = userBean.getObj().getUser_phone();
        }

        if (!TextUtils.isEmpty(photo)) {
            Glide.with(this)
                    .load(photo)
                    .placeholder(R.drawable.head_photo)
                    .error(R.drawable.head_photo)
                    .into(mIvHeadImage);
        }
        if (!TextUtils.isEmpty(phone)) {
            mTvAccount.setText(phone);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCallBackMessage(UserBean userBean){
        if(userBean != null) {
            if ("update".equals(userBean.getError())) {
                updateView(userBean);
            } else if ("finish".equals(userBean.getError())) {
                finish();
            }
        }
    }

    @OnClick({R.id.iv_more, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                popupBackgroundAlpha(0.3f);
                popupWindow.showAtLocation(mIvMore, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btnLogin:
                mPresenter.login(mTvAccount.getText().toString(),
                        etPassword.getText().toString());
                break;
        }
    }

    @Override
    public void loginResp(UserBean userBean) {
        if("101".equals(userBean.getCode())){
            //保存用户信息
            SharedPrfUtil.getInstance().setUserBean(userBean);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, userBean.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopupWindow() {
        View content = getLayoutInflater().inflate(R.layout.popup_login, null);
        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        PopButtonClick buttonClick = new PopButtonClick();
        content.findViewById(R.id.tvSwtAccount).setOnClickListener(buttonClick);
        content.findViewById(R.id.tvFindPwd).setOnClickListener(buttonClick);
        content.findViewById(R.id.tvSeyCenter).setOnClickListener(buttonClick);
        content.findViewById(R.id.tvRegister).setOnClickListener(buttonClick);

        popupWindow = new PopupWindow(content,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popupBackgroundAlpha(1.0f);
            }

        });
    }

    private void popupBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //透明度 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private class PopButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (popupWindow!=null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
            Intent intent = null;
            switch (view.getId()) {
                case R.id.tvSwtAccount:
                    intent = new Intent(LoginActivity.this, PhoneLoginFActivity.class);
                    break;
                case R.id.tvFindPwd:
                    SMSSDK.getVerificationCode("86", "15705817983");
                    break;
                case R.id.tvSeyCenter:
                    break;
                case R.id.tvRegister:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }

    }
}
