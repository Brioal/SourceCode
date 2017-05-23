package com.brioal.sourcecode.libsharelist.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.libsharelist.contract.LibShareListContract;
import com.brioal.sourcecode.libsharelist.model.LibShareListModelImpl;

import java.util.List;

/**
 * Created by Brioal on 2017/03/11
 */

public class LibShareListPresenterImpl implements LibShareListContract.Presenter {
    private LibShareListContract.View mView;
    private LibShareListContract.Model mModel;
    private Handler mHandler = new Handler();

    public LibShareListPresenterImpl(LibShareListContract.View view) {
        mView = view;
        mModel = new LibShareListModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadLibList(mView.getUserBean(), new OnLibLoadListener() {
            @Override
            public void success(final List<LibBean> libBeen) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showList(libBeen);
                    }
                });
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showListFailed(msg);
                    }
                });
            }
        });
    }
}