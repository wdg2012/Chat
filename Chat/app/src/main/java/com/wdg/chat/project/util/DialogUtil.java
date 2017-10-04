package com.wdg.chat.project.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.lang.ref.WeakReference;


/**
 * 对话框工具
 * Created by HuangBin on 2017/10/3.
 */
public class DialogUtil {

    public static final Class<ProgressBuilder> TYPE_PROGRESS = ProgressBuilder.class;
    public static final Class<PromptBuilder> TYPE_PROMPT = PromptBuilder.class;
    public static final Class<ChoiceBuilder> TYPE_CHOICE = ChoiceBuilder.class;


    public static <T extends Builder> T builder(Activity context, Class<T> type) {
        T result = null;
        if(context != null) {
            if (TYPE_PROGRESS == type) {
                result = (T) new ProgressBuilder(context);
            }
            else if (TYPE_PROMPT == type) {
                result = (T) new PromptBuilder(context);
            }
            else if (TYPE_CHOICE == type) {
                result = (T) new ChoiceBuilder(context);
            }
        }
        return result;
    }

    public static abstract class Builder {

        protected WeakReference<Context> mContext;

        public Builder(Activity context){
            mContext = new WeakReference<Context>(context);
        }

        public abstract Dialog create();
        protected abstract void release();

    }

    public static final class ProgressBuilder extends Builder {

        private String mMessage;

        public ProgressBuilder(Activity context) {
            super(context);
        }

        @Override
        public Dialog create() {
            Context context = mContext.get();
            if(context == null){ return null; }
            //创建进度条对话框
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage(mMessage);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            //释放对象
            release();
            //返回值
            return dialog;
        }

        @Override
        protected void release() {
            mContext.clear();
            mContext = null;
            mMessage = null;
        }

        public ProgressBuilder setMessage(String message) {
            mMessage = message;
            return this;
        }
    }

    public static final class PromptBuilder extends Builder {

        private String mTitle;
        private String mMessage;
        private String mBtnInfo1, mBtnInfo2;
        private DialogInterface.OnClickListener mClickListener;

        public PromptBuilder(Activity context) {
            super(context);
        }

        @Override
        public Dialog create() {
            Context context = mContext.get();
            if(context == null){ return null; }
            //创建对话框
            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle(mTitle)
                    .setMessage(mMessage)
                    .setPositiveButton(mBtnInfo1, mClickListener)
                    .setNegativeButton(mBtnInfo2, mClickListener)
                    .create();
            //释放对象
            release();
            //返回值
            return dialog;
        }

        @Override
        protected void release(){
            mContext.clear();
            mContext = null;
            mTitle = null;
            mMessage = null;
            mBtnInfo1 = null;
            mBtnInfo2 = null;
            mClickListener = null;
        }

        public PromptBuilder setTitle(String title){
            mTitle = title;
            return this;
        }

        public PromptBuilder setMessage(String message){
            mMessage = message;
            return this;
        }

        public PromptBuilder setButtons(String okBtn, String cancelBtn,
                                        OnClickListenerAdapter listenerAdapter){
            mBtnInfo1 = okBtn;
            mBtnInfo2 = cancelBtn;
            mClickListener = listenerAdapter;
            return this;
        }

    }

    public static final class ChoiceBuilder extends Builder {

        private String mTitle;
        private String[] mChoiceItems;
        private DialogInterface.OnClickListener mClickListener;

        public ChoiceBuilder(Activity context) {
            super(context);
        }

        @Override
        public Dialog create() {
            Context context = mContext.get();
            if(context == null){ return null; }
            //创建对话框
            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle(mTitle)
                    .setItems(mChoiceItems, mClickListener)
                    .create();
            //释放对象
            release();
            //返回值
            return dialog;
        }

        @Override
        protected void release() {
            mContext.clear();
            mContext = null;
            mTitle = null;
            mChoiceItems = null;
            mClickListener = null;
        }

        public ChoiceBuilder setTitle(String title){
            mTitle = title;
            return this;
        }

        public ChoiceBuilder setItems(String[] items,
                                      OnClickListenerAdapter listenerAdapter){
            mChoiceItems = items;
            mClickListener = listenerAdapter;
            return this;
        }

    }

    public static class OnClickListenerAdapter
            implements DialogInterface.OnClickListener,
            DialogInterface.OnMultiChoiceClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if(which >= 0){
                onSingleChoiceClick(dialog, which);
            }else{
                switch (which){
                    case -1:
                        onOkClick(dialog);
                        break;
                    case -2:
                        onCancelClick(dialog);
                        break;
                }
            }
        }

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            dialog.dismiss();
            onMultiChoiceClick(dialog, which, isChecked);
        }

        public void onOkClick(DialogInterface dialog){}
        public void onCancelClick(DialogInterface dialog){}
        public void onSingleChoiceClick(DialogInterface dialog, int which){}
        public void onMultiChoiceClick(DialogInterface dialog, int which, boolean isChecked){}

    }
}
