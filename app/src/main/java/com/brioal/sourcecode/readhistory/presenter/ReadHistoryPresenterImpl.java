package com.brioal.sourcecode.readhistory.presenter;
import android.os.Handler;

import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.interfaces.OnReadLoadListener;
import com.brioal.sourcecode.readhistory.contract.ReadHistoryContract;
import com.brioal.sourcecode.readhistory.model.ReadHistoryModelImpl;

import java.util.List;

/**
* Created by Brioal on 2017/03/11
*/

public class ReadHistoryPresenterImpl implements ReadHistoryContract.Presenter{
    private ReadHistoryContract.View mView;
    private ReadHistoryContract.Model mModel;
    private Handler mHandler = new Handler();

    public ReadHistoryPresenterImpl(ReadHistoryContract.View view) {
        mView = view;
        mModel = new ReadHistoryModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadReadHistory(mView.getUserBean(), new OnReadLoadListener() {
            @Override
            public void success(final List<ReadBean> list) {
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