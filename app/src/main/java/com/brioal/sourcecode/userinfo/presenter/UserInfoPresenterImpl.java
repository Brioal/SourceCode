package com.brioal.sourcecode.userinfo.presenter;

import android.os.Handler;

import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.userinfo.contract.UserInfoContract;
import com.brioal.sourcecode.userinfo.model.UserInfoModelImpl;

/**
 * Created by brioal on 2017/03/14
 */

public class UserInfoPresenterImpl implements UserInfoContract.Presenter {
    private UserInfoContract.View mView;
    private UserInfoContract.Model mModel;
    private Handler mHandler = new Handler();

    public UserInfoPresenterImpl(UserInfoContract.View view) {
        mView = view;
        mModel = new UserInfoModelImpl();
    }

    @Override
    public void start() {
        mModel.loadBlogCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogCount(0);
                    }
                });
            }
        });

        mModel.loadLibCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibCount(0);
                    }
                });
            }
        });
    }
}