package com.brioal.sourcecode.blogcollectlist.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.blogcollectlist.contract.BlogCollectContract;
import com.brioal.sourcecode.blogcollectlist.model.BlogCollectModelImpl;
import com.brioal.sourcecode.interfaces.OnBlogCollectListener;

import java.util.List;

/**
 * Created by Brioal on 2017/03/11
 */

public class BlogCollectPresenterImpl implements BlogCollectContract.Presenter {
    private BlogCollectContract.View mView;
    private BlogCollectContract.Model mModel;
    private Handler mHandler = new Handler();

    public BlogCollectPresenterImpl(BlogCollectContract.View view) {
        mView = view;
        mModel = new BlogCollectModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadBlogCollect(mView.getUserBean(), new OnBlogCollectListener() {
            @Override
            public void success(final List<BlogCollectionBean> list) {
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