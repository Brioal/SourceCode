package com.brioal.sourcecode.lib.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.lib.contract.LibContract;
import com.brioal.sourcecode.lib.model.LibModelImpl;

import java.util.List;

/**
 * Created by Brioal on 2017/03/02
 */

public class LibPresenterImpl implements LibContract.Presenter {
    private LibContract.View mView;
    private LibContract.Model mModel;
    private Handler mHandler = new Handler();

    public LibPresenterImpl(LibContract.View view) {
        mView = view;
        mModel = new LibModelImpl();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRefresh();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadLibList(new OnLibLoadListener() {
            @Override
            public void success(final List<LibBean> libBeen) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshDone();
                        mView.showLib(libBeen);
                    }
                });
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshFailed(msg);
                    }
                });
            }
        });
    }
}