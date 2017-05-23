package com.brioal.sourcecode.useredit.presenter;
import android.os.Handler;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.useredit.contract.UserEditContract;
import com.brioal.sourcecode.useredit.model.UserEditModelImpl;

/**
* Created by Brioal on 2017/03/01
*/

public class UserEditPresenterImpl implements UserEditContract.Presenter{
    private UserEditContract.View mView;
    private UserEditContract.Model mModel;
    private Handler mHandler = new Handler();

    public UserEditPresenterImpl(UserEditContract.View view) {
        mView = view;
        mModel = new UserEditModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void save(String headUrl, UserBean userBean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showSaving();
            }
        });
        mModel.saveUserBean(headUrl, userBean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showSavingDone();
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showSavingFailed(errorMsg);
                    }
                });
            }
        });
    }
}