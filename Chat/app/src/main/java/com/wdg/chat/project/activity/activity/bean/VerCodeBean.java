package com.wdg.chat.project.activity.activity.bean;

import java.util.List;


/**
 * 验证码信息
 * Created by HuangBin on 2017/9/13.
 */
public class VerCodeBean {


    /**
     * code : 101
     * error : 获取验证码成功
     * list : []
     * obj : {"ver_code":"7170"}
     */

    private String code;
    private String error;
    private ObjBean obj;
    private List<?> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static class ObjBean {
        /**
         * ver_code : 7170
         */

        private String ver_code;

        public String getVer_code() {
            return ver_code;
        }

        public void setVer_code(String ver_code) {
            this.ver_code = ver_code;
        }
    }
}
