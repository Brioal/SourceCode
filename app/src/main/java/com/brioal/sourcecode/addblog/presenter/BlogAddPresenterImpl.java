package com.brioal.sourcecode.addblog.presenter;

import android.os.Handler;

import com.brioal.sourcecode.addblog.contract.BlogAddContract;
import com.brioal.sourcecode.addblog.model.BlogAddModelImpl;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogTypeBean;
import com.brioal.sourcecode.interfaces.OnBlogTypeLoadListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/01
 */

public class BlogAddPresenterImpl implements BlogAddContract.Presenter {
    private BlogAddContract.View mView;
    private BlogAddContract.Model mModel;
    private Handler mHandler = new Handler();

    public BlogAddPresenterImpl(BlogAddContract.View view) {
        mView = view;
        mModel = new BlogAddModelImpl();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        mModel.loadBlogType(new OnBlogTypeLoadListener() {
            @Override
            public void success(final List<BlogTypeBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showType(list);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadFailed(errorMsg);
                    }
                });
            }
        });
    }

    @Override
    public void addBlog(String coverUrl, BlogBean blogBean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showAdding();
            }
        });
        mModel.addBlog(coverUrl, blogBean, new OnNormalOperatorListener() {
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