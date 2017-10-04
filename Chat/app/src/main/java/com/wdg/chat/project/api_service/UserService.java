package com.wdg.chat.project.api_service;

import com.wdg.chat.project.bean.RegisterBean;
import com.wdg.chat.project.bean.UserBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;


/**
 * 用户请求接口
 * Created by ${wdgan} on 2017/9/15 0015.
 * 邮箱18149542718@163
 */
public interface UserService {

    /**
     * 注册
     * @param body 请求体
     * @return
     */
    @Multipart
    @POST("user/register")
    Observable<RegisterBean> register(@Body RequestBody body);
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserBean> login(@Field("phone") String username,
                               @Field("password") String password);
    /**
     * 短信登录
     * @param phone 手机号
     * @param ver_code 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/sms_login")
    Observable<UserBean> smsLogin(@Field("phone") String phone,
                                  @Field("ver_code") String ver_code);

}
