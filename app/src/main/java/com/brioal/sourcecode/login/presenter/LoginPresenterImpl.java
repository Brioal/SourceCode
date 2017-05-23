package com.brioal.sourcecode.login.presenter;

import android.os.Handler;

import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.login.contract.LoginContract;
import com.brioal.sourcecode.login.model.LoginModelImpl;

/**
 * Created by Brioal on 2017/03/01
 */

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View mView;
    private LoginContract.Model mModel;
    private Handler mHandler = new Handler();

    public LoginPresenterImpl(LoginContract.View view) {
        mView = view;
        mModel = new LoginModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String email, String pass) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLogining();
            }
        });
        mModel.login(email, pass, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoginSuccess();
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoginFailed(errorMsg);
                    }
                });
            }
        });
    }
}