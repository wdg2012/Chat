package com.wdg.chat.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wdg.chat.project.MyApp;
import com.wdg.chat.project.R;
import com.wdg.chat.project.bean.UserBean;
import com.wdg.chat.project.mvp.presenter.RegisterPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import com.wdg.chat.project.mvp.contract.RegisterContract;


/**
 * 注册页面
 * Created by HuangBin on 2017/9/11.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    private final String TAG = RegisterActivity.class.getSimpleName();

    public static final int TAKE_PICKERS = 0x010;//拍照
    public static final int IMAGE_PICKER = 0x020;//选图
    public static final int START_VERCODE = 0x030;//跳转验证码页面


    @BindView(R.id.etNickName)
    EditText etNickName;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.etCountry)
    EditText etCountry;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private RegisterPresenter mPresenter;
    private String photoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);
        //设置图片为单选模式
        MyApp.getInstance().setSingleImagePicker();
        SMSSDK.registerEventHandler(mPresenter.getEventHandler());
        etNickName.addTextChangedListener(this);
        etCountry.addTextChangedListener(this);
        etPhoneNumber.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(mPresenter.getEventHandler());
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.ivPhoto, R.id.btnRegister, R.id.layout_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPhoto:
                mPresenter.showPhotoDialog(this);
                break;
            case R.id.btnRegister:
                mPresenter.showGetVerCodeDialog(this);
                break;
            case R.id.layout_back:
                finish();
                break;
        }
    }

    @Override
    public void callback_TakePicture_ImagePicker(Intent data) {
        List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        //获取图片路径
        photoPath = images.get(0).path;
        //Log.d(TAG, "path=" + path);
        if (photoPath != null) {
            //显示图片
            Glide.with(this)
                    .load(photoPath)
                    .placeholder(R.color.voice_login)
                    .error(R.color.voice_login)
                    .into(ivPhoto);
        }
    }

    @Override
    public void postEventBusMessage() {
        UserBean userBean = new UserBean();
        userBean.setError("update");
        UserBean.ObjBean objBean = new UserBean.ObjBean();
        objBean.setHead_path(photoPath);
        objBean.setUser_phone(etPhoneNumber.getText().toString());
        userBean.setObj(objBean);
        //发送消息
        EventBus.getDefault().post(userBean);
        //关闭页面
        finish();
    }

    @Override
    public void toTakePictureActivity() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //是否是直接打开相机
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
        startActivityForResult(intent, TAKE_PICKERS);
    }

    @Override
    public void toImagePickerActivity() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    @Override
    public void getVerCode() {
        mPresenter.getVerCode();
    }

    @Override
    public void toVerCodeActivity() {
        Intent intent = new Intent(this, VerCodeActivity.class);
        //保存参数
        intent.putExtra("phone", etPhoneNumber.getText().toString())
                .putExtra("password", etPassword.getText().toString())
                .putExtra("country", etCountry.getText().toString())
                .putExtra("headPhoto", photoPath)
                .putExtra("user_nick", etNickName.getText().toString());
        //启动验证码页面
        startActivityForResult(intent, START_VERCODE);
    }

    @Override
    public String getNickName() {
        return etNickName.getText().toString();
    }

    @Override
    public String getPhotoPath() {
        return photoPath;
    }

    @Override
    public String getCountry() {
        return etCountry.getText().toString();
    }

    @Override
    public String getPhoneNumber() {
        return etPhoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (etNickName.getText().toString().length() > 0
                && etCountry.getText().toString().length() > 0
                && etPhoneNumber.getText().toString().length() > 0
                && etPassword.getText().toString().length() > 0) {
            btnRegister.setEnabled(true);
            btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_enable));
        }else{
            btnRegister.setEnabled(false);
            btnRegister.setBackground(getResources().getDrawable(R.drawable.comm_btn_disenable));
        }
    }

}
