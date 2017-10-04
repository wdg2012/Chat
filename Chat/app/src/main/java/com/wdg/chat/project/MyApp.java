package com.wdg.chat.project;

import android.app.Application;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobSDK;
import com.wdg.chat.project.util.GlideImageLoader;


/**
 * Created by ${吴登赶} on 2017/8/27.
 */
public class MyApp extends Application {

    private static MyApp mInstance;

    public static MyApp getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        //Bugly.init(getApplicationContext(), "88380eb943", false);
        //OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        //OkHttpFinal.getInstance().init(builder.build());

        //初始化 图片选择器
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setImageLoader(new GlideImageLoader());
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        MobSDK.init(getApplicationContext(), "210abc1ae1e65", "c4d57dfdb1660ef890a75d7b2a6985b9");
    }

    /**
     * 设置图片单选
     */
    public void setSingleImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置单选
        imagePicker.setMultiMode(false);
        //允许裁剪（单选才有效）
        imagePicker.setCrop(true);
        //是否按矩形区域保存
        imagePicker.setSaveRectangle(false);
        //裁剪框的形状
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setFocusWidth(800);
        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setFocusHeight(800);
        //保存文件的宽度。单位像素
        imagePicker.setOutPutX(200);
        //保存文件的高度。单位像素
        imagePicker.setOutPutY(200);
    }

    /**
     * 设置图片多选
     */
    public void setMultiImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置多选
        imagePicker.setMultiMode(true);
        //选中数量限制
        imagePicker.setSelectLimit(9);
    }

}
