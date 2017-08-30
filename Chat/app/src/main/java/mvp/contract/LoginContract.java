package mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface LoginContract {

    interface Model {
        void loginInfo();
        void test(String id, BaseHttpRequestCallback<String> callback);
    }

    interface View {
        ImageView getHeadImageView();
        void setAccount(String text);
        void onTestResp(String result);
    }

    interface Presenter {
        void test(String id);
    }

}
