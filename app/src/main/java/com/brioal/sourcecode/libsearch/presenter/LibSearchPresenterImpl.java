package com.brioal.sourcecode.libsearch.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.libsearch.contract.LibSearchContract;
import com.brioal.sourcecode.libsearch.model.LibSearchModelImpl;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by Brioal on 2017/03/05
 */

public class LibSearchPresenterImpl implements LibSearchContract.Presenter {
    private LibSearchContract.Model mModel;
    private LibSearchContract.View mView;
    private Handler mHandler = new Handler();

    public LibSearchPresenterImpl(LibSearchContract.View view) {
        mView = view;
        mModel = new LibSearchModelImpl(mView.getContext());
    }

    @Override
    public void start() {
        //加载历史记录
        mModel.loadHistory(new OnHistpryLoadListener() {
            @Override
            public void success(final List<String> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showHistory(list);
                    }
                });
            }

            @Override
            public void failed(String msg) {
                KLog.e(msg);
            }
        });
    }

    @Override
    public void search(String key) {
        //加载搜索结果替换历史记录
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.addHistory(key);
        mModel.loadResult(key, new OnLibLoadListener() {
            @Override
            public void success(final List<LibBean> libBeen) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadingDone();
                        mView.showSearchResult(libBeen);
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
}