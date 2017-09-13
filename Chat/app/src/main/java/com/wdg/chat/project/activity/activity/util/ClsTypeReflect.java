package com.wdg.chat.project.activity.activity.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取泛型类型
 * Created by HuangBin on 2017/9/13.
 */
public class ClsTypeReflect {

    static Type getModelClazz(Class<?> subclass) {
        return getGenericType(0, subclass);
    }

    private static Type getGenericType(int index, Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        return params[index];
    }

}
