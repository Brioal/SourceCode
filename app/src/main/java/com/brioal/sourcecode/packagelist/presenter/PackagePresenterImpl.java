package com.brioal.sourcecode.packagelist.presenter;

import android.os.Handler;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;
import com.brioal.sourcecode.packagelist.contract.PackageContract;
import com.brioal.sourcecode.packagelist.model.PackageModelImpl;

import java.util.List;

/**
 * Created by Brioal on 2017/03/05
 */

public class PackagePresenterImpl implements PackageContract.Presenter {
    private PackageContract.View mView;
    private PackageContract.Model mModel;
    private Handler mHandler = new Handler();

    public PackagePresenterImpl(PackageContract.View view) {
        mView = view;
        mModel = new PackageModelImpl(mView.getContext());
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.loadPackages(new OnApiBeanLoadListener() {
            @Override
            public void success(final List<IndexBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadingDone(list);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadingFailed(errorMsg);
                    }
                });
            }
        });
    }
}