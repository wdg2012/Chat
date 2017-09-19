package com.wdg.chat.project.activity.activity.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.wdg.chat.project.activity.activity.app.MyApp;
import com.wdg.chat.project.activity.activity.bean.UserBean;


/**
 * 共享文件
 * Created by HuangBin on 2017/9/17.
 */
public class SharedPrfUtil {

    private static final String FILE_NAME = "share_date";

    private static String TAG = SharedPrfUtil.class.getSimpleName();
    private static SharedPrfUtil mInstance;

    public static synchronized SharedPrfUtil getInstance(){
        if(mInstance == null){
            mInstance = new SharedPrfUtil();
        }
        return mInstance;
    }

    private SharedPrfUtil(){  }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public String setParam(Context context, String key, Object object){

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).commit();

        String type = null;
        if(object != null) {
            type = object.getClass().getSimpleName();

            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            }
            else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            }
            else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            }
            else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            }
            else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }

            editor.commit();
        }

        return type;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(Context context, String key, Object defaultObject){

        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        }
        else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        }
        else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        }
        else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        }
        else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 保存用户信息
     * @param userBean
     */
    public void setUserBean(UserBean userBean) {
        try {
            String json = userBean == null ? null : GsonUtil.toJson(userBean);
            //写入文件中
            setParam(MyApp.getInstance(), "userBean", json);
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
        String json = (String) getParam(MyApp.getInstance(), "userBean", "");
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
