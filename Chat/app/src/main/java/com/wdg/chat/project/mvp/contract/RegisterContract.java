package com.wdg.chat.project.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface RegisterContract {

    interface Model extends BaseContract.Model{
        void getVerCode(String country, String phone);
        boolean validate(Context context,
                      String nickName, String photo, String phoneNumber, String password);
    }

    interface View extends BaseContract.View{
        void toVerCodeActivity();
        String getNickName();
        String getPhotoPath();
        String getCountry();
        String getPhoneNumber();
        String getPassword();
        void getVerCode();
        void toTakePictureActivity();
        void toImagePickerActivity();
        void callback_TakePicture_ImagePicker(Intent data);
        void postEventBusMessage();
    }

    interface Presenter extends BaseContract.Presenter{
        void getVerCode();
        void showGetVerCodeDialog(Activity context);
        void showPhotoDialog(Activity context);
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

}
