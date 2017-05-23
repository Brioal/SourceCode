package com.brioal.sourcecode.api.presenter;

import android.os.Handler;

import com.brioal.sourcecode.api.contract.ApiContract;
import com.brioal.sourcecode.api.model.ApiModelImpl;
import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/03
 */

public class ApiPresenterImpl implements ApiContract.Presenter {
    private ApiContract.View mView;
    private ApiContract.Model mModel;
    private Handler mHandler = new Handler();

    public ApiPresenterImpl(ApiContract.View view) {
        mView = view;
        mModel = new ApiModelImpl(mView.getContext());
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.transData();
        mModel.loadHistory(new OnHistpryLoadListener() {
            @Override
            public void success(final List<String> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadingDone();
                        mView.showHistory(list);
                    }
                });
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadingFailed(msg);
                    }
                });
            }
        });
    }

    @Override
    public void saveHistory(String key) {
        mModel.saveHistory(key, null);
    }
}