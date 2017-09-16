package com.wdg.chat.project.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.wdg.chat.project.activity.activity.bean.LoginBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
/*    @BindView(R.id.rlt_voice_login)
    RelativeLayout mRltVoiceLogin;
    @BindView(R.id.tv_change_auth)
    TextView mTvChangeAuth;*/

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

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.addTextChangedListener(textWatcher);
        initView();
        initPopupWindow();
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView(){
        Intent intent = getIntent();
        String photo = null;
        String phone = null;
        if(intent != null){
            photo = intent.getStringExtra("photo");
            phone = intent.getStringExtra("phone");
        }
        if(!TextUtils.isEmpty(photo)){
            Glide.with(this)
                    .load(photo)
                    .placeholder(R.drawable.head_photo)
                    .error(R.drawable.head_photo)
                    .into(mIvHeadImage);
        }
        if(!TextUtils.isEmpty(phone)){
            mTvAccount.setText(phone);
        }
    }

    @OnClick({R.id.iv_more, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                popupWindow.showAtLocation(mIvMore, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btnLogin:
                mPresenter.login(mTvAccount.getText().toString(),
                        etPassword.getText().toString());
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
    }

    private class PopButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            Intent intent = null;
            switch (view.getId()) {
                case R.id.tvSwtAccount:
                    intent = new Intent(LoginActivity.this, PhoneLoginFActivity.class);
                    break;
                case R.id.tvFindPwd:
                    break;
                case R.id.tvSeyCenter:
                    break;
                case R.id.tvRegister:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
                finish();
            }
        }

    }
}
