package com.wdg.chat.project.activity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.app.MyApp;
import com.wdg.chat.project.activity.activity.bean.UserBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;
import com.wdg.chat.project.activity.activity.util.SMEventHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import mvp.contract.RegisterContract;
import mvp.presenter.RegisterPresenter;


/**
 * 注册页面
 * Created by HuangBin on 2017/9/11.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    private final String TAG = RegisterActivity.class.getSimpleName();

    private final int TAKE_PICKERS = 10;//拍照
    private final int IMAGE_PICKER = 20;//选图
    private final int START_VERCODE = 30;//跳转验证码页面


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

    private String photoPath;
    //private RegisterPresenter mPresenter;

    private EventHandler eventHandler = new SMEventHandler() {

        public void ui_onAfterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable)data;
                String msg = throwable.getMessage();
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            } else {
                //获取验证码
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    SMSSDK.unregisterEventHandler(eventHandler);
                    Intent intent = new Intent(RegisterActivity.this, VerCodeActivity.class);
                    //保存参数
                    intent.putExtra("phone", etPhoneNumber.getText().toString())
                            .putExtra("password", etPassword.getText().toString())
                            .putExtra("country", etCountry.getText().toString())
                            .putExtra("headPhoto", photoPath)
                            .putExtra("user_nick", etNickName.getText().toString());
                    //启动验证码页面
                    startActivityForResult(intent, START_VERCODE);
                }
            }
        }

    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

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

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //mPresenter = new RegisterPresenter(this);
        //设置图片为单选模式
        MyApp.getInstance().setSingleImagePicker();
        SMSSDK.registerEventHandler(eventHandler);
        etNickName.addTextChangedListener(textWatcher);
        etCountry.addTextChangedListener(textWatcher);
        etPhoneNumber.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照 选图
        if (requestCode == TAKE_PICKERS
                || requestCode == IMAGE_PICKER) {
            //判断结果码
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
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
        }
        //验证码页面返回
        else if(requestCode == START_VERCODE){
            if(resultCode == RESULT_OK){
                UserBean userBean = new UserBean();
                userBean.setError("update");
                UserBean.ObjBean objBean = new UserBean.ObjBean();
                objBean.setHead_path(photoPath);
                objBean.setUser_phone(etPhoneNumber.getText().toString());
                userBean.setObj(objBean);
                //发送消息
                EventBus.getDefault().post(userBean);
                finish();
            }
            else if(resultCode == RESULT_CANCELED){
                SMSSDK.registerEventHandler(eventHandler);
            }
        }
    }

    @OnClick({R.id.ivPhoto, R.id.btnRegister, R.id.layout_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPhoto:
                showPhotoDialog();
                break;
            case R.id.btnRegister:
                if(validate()) {
                    showGetVerCodeDialog();
                }
                break;
            case R.id.layout_back:
                finish();
                break;
        }
    }

    @Override
    public void verCodeResp(VerCodeBean verCodeBean) {
    }

    /**
     * 显示获取验证码对话框
     */
    private void showGetVerCodeDialog(){
        //创建对话框
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("确认手机号码")
                .setMessage("我们将发送验证码短信到这个号码:\n"
                        + etCountry.getText().toString() + etPhoneNumber.getText().toString())
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //mPresenter.obtainVerCode(etPhoneNumber.getText().toString());
                        SMSSDK.getVerificationCode(etCountry.getText().toString(),
                                                    etPhoneNumber.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        //显示对话框
        dialog.show();
    }

    /**
     * 显示选图方式对话框
     */
    private void showPhotoDialog() {
        //创建数组
        String[] items = new String[]{ "拍照", "相册" };
        //创建对话框
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("请选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //拍照
                        if(which == 0){
                            Intent intent = new Intent(RegisterActivity.this, ImageGridActivity.class);
                            //是否是直接打开相机
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                            startActivityForResult(intent, TAKE_PICKERS);
                        }
                        //相册
                        else{
                            Intent intent = new Intent(RegisterActivity.this, ImageGridActivity.class);
                            startActivityForResult(intent, IMAGE_PICKER);
                        }
                    }

        }).create();
        //显示对话框
        dialog.show();
    }

    private boolean validate(){
        boolean result = true;
        String error = "";

        if(TextUtils.isEmpty(etNickName.getText().toString())){
            error = "昵称不能为空!";
            result = false;
        }
        else if(TextUtils.isEmpty(etPhoneNumber.getText().toString())){
            error = "手机号不能为空!";
            result = false;
        }
        else if(TextUtils.isEmpty(etPassword.getText().toString())){
            error = "密码不能为空!";
            result = false;
        }

        if(!result){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }

        return result;
    }
}
