package com.wdg.chat.project.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.chat.project.R;

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
    @BindView(R.id.rlt_voice_login)
    RelativeLayout mRltVoiceLogin;
    @BindView(R.id.tv_change_auth)
    TextView mTvChangeAuth;
    private LoginContract.Presenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public ImageView getHeadImageView() {
        return mIvHeadImage;
    }

    @Override
    public void setAccount(final String text) {
        mTvAccount.setText(text);
    }

    @Override
    public void onTestResp(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_more, R.id.tv_change_auth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                break;
            case R.id.tv_change_auth:
                mLoginPresenter.test("1000");
                break;
        }
    }
}
