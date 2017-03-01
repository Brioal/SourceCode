package com.brioal.sourcecode.register.contract;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public class RegisterContract {
    public interface View {
        void showRegistering();//显示正在注册

        void showRegisterDone();//显示注册成功

        void showRegisterFailed(String errorMsg);//显示注册失败

        void showSignUpedUser(UserBean userBean);//传递注册之后的User

    }

    public interface Presenter {
        void start();//默认开始

        void register(UserBean userBean);//注册
    }

    public interface Model {
        void register(UserBean bean, OnNormalOperatorListener listener);//注册
    }


}