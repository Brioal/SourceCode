package com.brioal.sourcecode.blogsharelist.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.blogsharelist.contract.BlogShareContract;
import com.brioal.sourcecode.blogsharelist.model.BlogShareModelImpl;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/10
 */

public class BlogSharePresenterImpl implements BlogShareContract.Presenter {
    private BlogShareContract.View mView;
    private BlogShareContract.Model mModel;
    private Handler mHandler = new Handler();

    public BlogSharePresenterImpl(BlogShareContract.View view) {
        mView = view;
        mModel = new BlogShareModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadShareList(mView.getUserBean(), new OnBlogLoadListener() {
            @Override
            public void success(final List<BlogBean> list) {
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
                        mView.showLoadFailed(errorMsg);
                    }
                });
            }
        });
    }
}