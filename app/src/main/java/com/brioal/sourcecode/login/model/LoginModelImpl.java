package com.brioal.sourcecode.login.model;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.login.contract.LoginContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Brioal on 2017/03/01
 */

public class LoginModelImpl implements LoginContract.Model {

    @Override
    public void login(String email, String pass, final OnNormalOperatorListener listener) {
        BmobUser.loginByAccount(email, pass, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                }
                listener.success("");
            }
        });
    }
}