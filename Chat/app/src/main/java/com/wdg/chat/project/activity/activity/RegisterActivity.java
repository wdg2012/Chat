package com.wdg.chat.project.activity.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wdg.chat.project.R;
import com.wdg.chat.project.activity.activity.bean.RespData;

import java.io.File;

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
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            photoFile = new File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
/*            //取得图片uri的列名和此列的详细信息
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                System.out.println(i + "-" + cursor.getColumnName(i) + "-" + cursor.getString(i));
            }*/
        }
    }

    @OnClick({R.id.ivPhoto, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivPhoto:
                Intent intent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //启动系统图片浏览器
                startActivityForResult(intent, 1);
                break;
            case R.id.btnRegister:
                mPresenter.register(etPhoneNumber.getText().toString(),
                        etPassword.getText().toString(),
                        etCountry.getText().toString(),
                        photoFile,
                        "1.0", etNickName.getText().toString());
                break;
        }
    }

    @Override
    public void registerResp(RespData<String> respData) {

    }
}
