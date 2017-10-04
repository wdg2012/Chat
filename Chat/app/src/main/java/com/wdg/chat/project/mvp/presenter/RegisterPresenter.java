package com.wdg.chat.project.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.wdg.chat.project.MyApp;
import com.wdg.chat.project.activity.RegisterActivity;
import com.wdg.chat.project.mvp.contract.RegisterContract;
import com.wdg.chat.project.mvp.model.RegisterModel;
import com.wdg.chat.project.util.DialogUtil;
import com.wdg.chat.project.util.SMEventHandler;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mRegisterView;
    private RegisterContract.Model mRegisterModel;
    private Context mContext;

    private EventHandler eventHandler = new SMEventHandler() {

        public void ui_onAfterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable) data;
                String msg = throwable.getMessage();
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            } else {
                //获取验证码
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    SMSSDK.unregisterEventHandler(eventHandler);
                    mRegisterView.toVerCodeActivity();
                }
            }
        }

    };

    public RegisterPresenter(RegisterContract.View registerView){
        mRegisterView = registerView;
        checkViewNull();
        mRegisterModel = new RegisterModel();
        mContext = MyApp.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        //拍照 选图
        if (requestCode == RegisterActivity.TAKE_PICKERS
                || requestCode == RegisterActivity.IMAGE_PICKER) {
            //判断结果码
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                mRegisterView.callback_TakePicture_ImagePicker(data);
            }
        }
        //验证码页面返回
        else if(requestCode == RegisterActivity.START_VERCODE){
            if(resultCode == Activity.RESULT_OK){
                mRegisterView.postEventBusMessage();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                SMSSDK.registerEventHandler(eventHandler);
            }
        }
    }

    @Override
    public void showGetVerCodeDialog(Activity context) {
        String nickName = mRegisterView.getNickName();
        String photoPath = mRegisterView.getPhotoPath();
        final String country = mRegisterView.getCountry();
        final String phoneNumber = mRegisterView.getPhoneNumber();
        String password = mRegisterView.getPassword();
        if(mRegisterModel.validate(mContext,
                nickName, photoPath, phoneNumber, password)) {
            //创建对话框
            DialogUtil.builder(context, DialogUtil.TYPE_PROMPT)
                    .setTitle("确认手机号码")
                    .setMessage("我们将发送验证码短信到这个号码:\n" + country + phoneNumber)
                    .setButtons("确认", "取消", new DialogUtil.OnClickListenerAdapter() {

                        @Override
                        public void onOkClick(DialogInterface dialog) {
                            super.onOkClick(dialog);
                            mRegisterView.getVerCode();
                        }

                    }).create().show();
        }
    }

    @Override
    public void showPhotoDialog(Activity context) {
        //创建对话框
        DialogUtil.builder(context, DialogUtil.TYPE_CHOICE)
                .setTitle("请选择图片")
                .setItems(new String[]{"拍照", "相册"},
                        new DialogUtil.OnClickListenerAdapter(){

                            @Override
                            public void onSingleChoiceClick(DialogInterface dialog, int which) {
                                super.onSingleChoiceClick(dialog, which);
                                //拍照
                                if(which == 0){
                                    mRegisterView.toTakePictureActivity();
                                }
                                //相册
                                else{
                                    mRegisterView.toImagePickerActivity();
                                }
                            }
                        })
                .create().show();
    }

    @Override
    public void getVerCode() {
        String country = mRegisterView.getCountry();
        String phoneNumber = mRegisterView.getPhoneNumber();
        //获取验证码
        mRegisterModel.getVerCode(country, phoneNumber);
    }

    @Override
    public void checkViewNull() {
        if(mRegisterView == null){
            throw new RuntimeException("RegisterContract.View 不能为空!");
        }
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

}
