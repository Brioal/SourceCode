package com.brioal.sourcecode.userinfo.contract;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github:https://github.com/Brioal
 * Emalil : brioal@foxmail.com
 * Created by brioal on 17-3-14.
 */

public class UserInfoContract {

    public interface View {
        void showBlogCount(int count);//显示博客数量

        void showLibCount(int count);//显示开源库数量

        void showFailed(String errorMsg);//显示错误信息

        UserBean getUserBean();//返回用户信息
    }

    public interface Presenter {
        void start();//默认的初始化
    }

    public interface Model {
        //加载博客数量
        void loadBlogCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载开源库数量
        void loadLibCount(UserBean userBean, OnNormalOperatorListener listener);
    }


}