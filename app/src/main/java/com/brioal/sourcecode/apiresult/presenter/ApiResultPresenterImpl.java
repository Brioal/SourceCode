package com.brioal.sourcecode.apiresult.presenter;

import android.os.Handler;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.apiresult.contract.ApiResultContract;
import com.brioal.sourcecode.apiresult.model.ApiResultModelImpl;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/04
 */

public class ApiResultPresenterImpl implements ApiResultContract.Presenter {
    private ApiResultContract.View mView;
    private ApiResultContract.Model mModel;
    private Handler mHandler = new Handler();

    public ApiResultPresenterImpl(ApiResultContract.View view) {
        mView = view;
        mModel = new ApiResultModelImpl(mView.getContext());
    }

    @Override
    public void start(String key) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.loadResult(key, new OnApiBeanLoadListener() {
            @Override
            public void success(final List<IndexBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadDone(list);
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