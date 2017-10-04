package com.wdg.chat.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdg.chat.project.R;
import com.wdg.chat.project.bean.UserBean;
import com.wdg.chat.project.util.PopupWindowUtil;
import com.wdg.chat.project.util.SharedPrfUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wdg.chat.project.mvp.contract.LoginContract;
import com.wdg.chat.project.mvp.presenter.LoginPresenter;


/**
 * 登录页面
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class LoginActivity extends BaseActivity
        implements LoginContract.View, PopupWindowUtil.CreatorListener {

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


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
        EventBus.getDefault().register(this);
        etPassword.addTextChangedListener(this);
        popupWindow = PopupWindowUtil.create(this, this);
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void initView(){
        UserBean userBean = SharedPrfUtil.getInstance().getuserBean();
        if(userBean != null){
            String photo = userBean.getObj().getHead_path();
            String phone = userBean.getObj().getUser_phone();
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
    }

    public void updateView(UserBean userBean){
        if(userBean != null) {
            String photo = userBean.getObj().getHead_path();
            String phone = userBean.getObj().getUser_phone();
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCallBackMessage(UserBean userBean){
        if(userBean != null) {
            if ("update".equals(userBean.getError())) {
                updateView(userBean);
            }
            else if ("finish".equals(userBean.getError())) {
                finish();
            }
        }
    }

    @OnClick({R.id.iv_more, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                PopupWindowUtil.popupBackgroundAlpha(getWindow(), 0.3f);
                popupWindow.showAtLocation(mIvMore, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btnLogin:
                mPresenter.login(mTvAccount.getText().toString(),
                        etPassword.getText().toString());
                break;
        }
    }

    @Override
    public void toMainActivity(UserBean userBean) {
        //保存用户信息
        SharedPrfUtil.getInstance().setUserBean(userBean);
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
    public View createContentView(LayoutInflater layInflater,
                                  View.OnClickListener menuListener) {
        View content = layInflater.inflate(R.layout.popup_login, null);

        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        content.findViewById(R.id.tvSwtAccount).setOnClickListener(menuListener);
        content.findViewById(R.id.tvFindPwd).setOnClickListener(menuListener);
        content.findViewById(R.id.tvSeyCenter).setOnClickListener(menuListener);
        content.findViewById(R.id.tvRegister).setOnClickListener(menuListener);

        return content;
    }

    @Override
    public void onMenuClick(View menu) {
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        Intent intent = null;
        switch (menu.getId()) {
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
        }
    }

    @Override
    public void onDismissPopupWindow() {
        PopupWindowUtil.popupBackgroundAlpha(getWindow(), 1.0f);
    }

}
