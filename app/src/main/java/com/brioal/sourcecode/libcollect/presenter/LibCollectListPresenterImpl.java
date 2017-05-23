package com.brioal.sourcecode.libcollect.presenter;
import android.os.Handler;

import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.interfaces.OnLibCollectLoadListener;
import com.brioal.sourcecode.libcollect.contract.LibCollectListContract;
import com.brioal.sourcecode.libcollect.model.LibCollectListModelImpl;

import java.util.List;

/**
* Created by Brioal on 2017/03/11
*/

public class LibCollectListPresenterImpl implements LibCollectListContract.Presenter{
    private LibCollectListContract.View mView;
    private LibCollectListContract.Model mModel;
    private Handler mHandler = new Handler();

    public LibCollectListPresenterImpl(LibCollectListContract.View view) {
        mView = view;
        mModel = new LibCollectListModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadCollectLib(mView.getUserBean(), new OnLibCollectLoadListener() {
            @Override
            public void success(final List<LibCollectBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showList(list);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showListFailed(errorMsg);
                    }
                });
            }
        });
    }
}