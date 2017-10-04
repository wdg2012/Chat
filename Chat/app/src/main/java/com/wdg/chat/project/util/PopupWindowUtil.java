package com.wdg.chat.project.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 弹窗工具
 * Created by HuangBin on 2017/10/4.
 */
public class PopupWindowUtil {

    public static PopupWindow create(Context context, CreatorListener listener){
        return new Creator().build(context, listener);
    }

    public static void popupBackgroundAlpha(Window window, float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        //透明度 0.0-1.0
        lp.alpha = bgAlpha;
        window.setAttributes(lp);
    }

    public static class Creator
            implements View.OnClickListener,
            PopupWindow.OnDismissListener {

        private CreatorListener mListener;

        public PopupWindow build(Context context, CreatorListener listener){
            PopupWindow popupWindow = null;
            LayoutInflater inflater = LayoutInflater.from(context);
            mListener = listener;
            if(mListener != null) {
                //获取弹窗布局视图
                View content = mListener.createContentView(inflater, this);
                //创建弹窗
                popupWindow = new PopupWindow(content,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                //设置属性
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
                popupWindow.setOnDismissListener(this);
            }
            //返回值
            return popupWindow;
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onMenuClick(view);
            }
        }

        @Override
        public void onDismiss() {
            if(mListener != null){
                mListener.onDismissPopupWindow();
            }
        }

    }

    public interface CreatorListener {
        public View createContentView(LayoutInflater layInflater,
                                      View.OnClickListener menuListener);
        public void onMenuClick(View menu);
        public void onDismissPopupWindow();
    }

}
