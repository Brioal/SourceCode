package com.brioal.sourcecode.useredit.contract;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public class UserEditContract {
    public interface View {
        void showSaving();//显示正在保存

        void showSavingDone();//显示保存失败

        void showSavingFailed(String errorMsg);//显示保存失败
    }

    public interface Presenter {
        void start();//默认的初始化

        void save(String headUrl, UserBean userBean);//保存数据
    }

    public interface Model {
        void saveUserBean(String headUrl, UserBean userBean, OnNormalOperatorListener listener);
    }


}