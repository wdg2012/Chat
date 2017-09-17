package com.wdg.chat.project.activity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.app.MyApp;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);
        //设置图片为单选模式
        MyApp.getInstance().setSingleImagePicker();
    }

    @Override
    protected void onDestroy() {
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
//                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(MyApp.getInstance());
//                Intent intent = new Intent(LoginActivity.UPDATE_ACTIVITY);
//                intent.putExtra("photo", photoPath)
//                        .putExtra("phone", etPhoneNumber.getText().toString());
//                broadcastManager.sendBroadcast(intent);
                finish();
            }
        }
    }

    @OnClick({R.id.ivPhoto, R.id.btnRegister})
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
        }
    }

    @Override
    public void verCodeResp(VerCodeBean verCodeBean) {
        if("101".equals(verCodeBean.getCode())){
            Log.d("VerCode", verCodeBean.getObj().getVer_code());
            Intent intent = new Intent(this, VerCodeActivity.class);
            //保存参数
            intent.putExtra("phone", etPhoneNumber.getText().toString())
                .putExtra("password", etPassword.getText().toString())
                .putExtra("country", etCountry.getText().toString())
                .putExtra("headPhoto", photoPath)
                .putExtra("user_nick", etNickName.getText().toString());
            //启动验证码页面
            startActivityForResult(intent, START_VERCODE);
        }else{
            Toast.makeText(this, verCodeBean.getError(), Toast.LENGTH_SHORT).show();
        }
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
                        mPresenter.obtainVerCode(etPhoneNumber.getText().toString());
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
