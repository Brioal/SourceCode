package com.brioal.sourcecode.register.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.register.contract.RegisterContract;
import com.brioal.sourcecode.register.model.RegisterModelImpl;

/**
 * Created by Brioal on 2017/03/01
 */

public class RegisterPresenterImpl implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private RegisterContract.Model mModel;
    private Handler mHandler = new Handler();

    public RegisterPresenterImpl(RegisterContract.View view) {
        mView = view;
        mModel = new RegisterModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(final UserBean userBean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRegistering();
            }
        });
        mModel.register(userBean, new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRegisterDone();
                        userBean.setObjectId(msg);
                        mView.showSignUpedUser(userBean);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRegisterFailed(errorMsg);
                    }
                });
            }
        });
    }
}