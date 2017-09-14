package com.wdg.chat.project.activity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.wdg.chat.project.activity.activity.bean.RegisterBean;
import com.wdg.chat.project.activity.activity.bean.VerCodeBean;

import java.io.File;
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

    private File photoFile;
    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this, prgDialog);
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

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if(data != null) {
                //拍照 选图
                if (requestCode == TAKE_PICKERS
                        || requestCode == IMAGE_PICKER) {
                    List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    //获取图片路径
                    String path = images.get(0).path;
                    //Log.d(TAG, "path=" + path);
                    if(path != null){
                        photoFile = new File(path);
                        //显示图片
                        Glide.with(this)
                                .load(path)
                                .placeholder(R.color.voice_login)
                                .error(R.color.voice_login)
                                .into(ivPhoto);
                    }
                }
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
                showGetVerCodeDialog();
                break;
        }
    }

    @Override
    public void verCodeResp(VerCodeBean verCodeBean) {
        if("101".equals(verCodeBean.getCode())){
            mPresenter.register(etPhoneNumber.getText().toString(),
                    etPassword.getText().toString(),
                    etCountry.getText().toString(),
                    photoFile,
                    verCodeBean.getObj().getVer_code(),
                    etNickName.getText().toString());
        }else{
            Toast.makeText(this, verCodeBean.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerResp(RegisterBean registerBean) {
        /*if(respData.getCode() == 101){

        }else{

        }*/
        Toast.makeText(this, registerBean.getError(), Toast.LENGTH_SHORT).show();
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
}
