package com.brioal.sourcecode.classlist.presenter;

import android.os.Handler;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.bean.ApiBean;
import com.brioal.sourcecode.classlist.contract.ClassListContract;
import com.brioal.sourcecode.classlist.model.ClassListModelImpl;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/04
 */

public class ClassListPresenterImpl implements ClassListContract.Presenter {
    private ClassListContract.View mView;
    private ClassListContract.Model mModel;
    private Handler mHandler = new Handler();

    public ClassListPresenterImpl(ClassListContract.View view) {
        mView = view;
        mModel = new ClassListModelImpl(mView.getContext());
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.loadClasses(new OnApiBeanLoadListener() {
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