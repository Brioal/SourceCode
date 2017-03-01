package com.brioal.sourcecode.login.contract;

import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public class LoginContract {
    public interface View {
        void showLogining();//显示正在登陆

        void showLoginSuccess();//显示登录成

        void showLoginFailed(String errorMsg);//显示登录失败
    }

    public interface Presenter {
        void start();//默认操作

        void login(String email, String pass);//登陆

    }

    public interface Model {
        //登陆
        void login(String email, String pass, OnNormalOperatorListener listener);
    }


}