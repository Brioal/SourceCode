package com.brioal.sourcecode.addlib.presenter;

import android.os.Handler;

import com.brioal.sourcecode.addlib.contract.AddLibContract;
import com.brioal.sourcecode.addlib.model.AddLibModelImpl;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Created by Brioal on 2017/03/05
 */

public class AddLibPresenterImpl implements AddLibContract.Presenter {
    private AddLibContract.Model mModel;
    private AddLibContract.View mView;
    private Handler mHandler = new Handler();

    public AddLibPresenterImpl(AddLibContract.View view) {
        mView = view;
        mModel = new AddLibModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void add(LibBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showAdding();
            }
        });
        mModel.addLib(bean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAddingDone();
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAddingFailed(errorMsg);
                    }
                });
            }
        });
    }
}