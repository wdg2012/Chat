package mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ${wdgan} on 2017/8/30 0030.
 * 邮箱18149542718@163
 */
public interface LoginContract {
    interface Model {
        void loginInfo();
    }

    interface View {
        ImageView getHeadImageView();
        void setAccount(String text);

    }

    interface Presenter {
    }
}
