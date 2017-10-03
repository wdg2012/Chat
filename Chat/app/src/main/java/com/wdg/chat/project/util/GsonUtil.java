package com.wdg.chat.project.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by HuangBin on 2017/9/13.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    public static String toJson(Object src){
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException{
        return  gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException{
        return gson.fromJson(json, typeOfT);
    }

}
