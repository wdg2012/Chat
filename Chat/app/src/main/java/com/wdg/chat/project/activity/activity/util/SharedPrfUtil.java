package com.wdg.chat.project.activity.activity.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.wdg.chat.project.activity.activity.app.MyApp;
import com.wdg.chat.project.activity.activity.bean.UserBean;


/**
 * 共享文件
 * Created by HuangBin on 2017/9/17.
 */
public class SharedPrfUtil {

    private static String TAG = SharedPrfUtil.class.getSimpleName();
    private static SharedPrfUtil mInstance;

    public static synchronized SharedPrfUtil getInstance(){
        if(mInstance == null){
            mInstance = new SharedPrfUtil();
        }
        return mInstance;
    }

    private SharedPreferences prefs;

    private SharedPrfUtil(){
        prefs = PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance());
    }

    /**
     * 保存用户信息
     * @param userBean
     */
    public void setUserBean(UserBean userBean) {

        try {
            //移除原有数据
            prefs.edit().remove("userBean").commit();
            //判断对象
            if(userBean != null){
                //写入文件中
                prefs.edit().putString("userBean", GsonUtil.toJson(userBean)).commit();
            }
        } catch (Exception e) {
            Log.d(TAG, "保存用户信息失败:", e);
        }

    }

    /**
     * 获取用户信息
     * @return
     */
    public UserBean getuserBean() {
        //创建用户信息
        UserBean userBean = null;

        //获取json字符串
        String json = prefs.getString("userBean", "");
        //判断字符串
        if (!TextUtils.isEmpty(json)) {
            try {
                //获取用户信息
                userBean = GsonUtil.fromJson(json, UserBean.class);
            } catch (Exception e) {
                Log.d(TAG, "解析用户信息[" + json + "]失败:", e);
            }
        }

        //返回值
        return userBean;
    }

}
