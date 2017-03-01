package com.brioal.sourcecode.home.presenter;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.home.contract.HomeContract;
import com.brioal.sourcecode.home.model.HomeModelImpl;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

import java.util.List;

/**
 * Created by Brioal on 2017/02/24
 */

public class HomePresenterImpl implements HomeContract.Presenter {
    private HomeContract.View mView;
    private HomeContract.Model mModel;
    private android.os.Handler mHandler = new android.os.Handler();

    public HomePresenterImpl(HomeContract.View view) {
        mView = view;
        mModel = new HomeModelImpl();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRefreshing();
            }
        });
        refresh();
        refreshHot();
    }

    @Override
    public void refresh() {
        //加载列表
        mModel.loadList(mView.getSortType(), new OnBlogLoadListener() {
            @Override
            public void success(final List<BlogBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshDone();
                        mView.showList(list);
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshFailed("加载数据失败");
                    }
                });
            }
        });
        refreshHot();
    }

    @Override
    public void refreshHot() {
        //加载热门
        mModel.loadHot(new OnBlogLoadListener() {
            @Override
            public void success(final List<BlogBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showHot(list);
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshFailed("加载热门数据失败");
                    }
                });
            }
        });
    }

    @Override
    public void getUrlInfo(String url) {
        //加载博客的信息
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        //加载博客数据
        mModel.loadBlog(url, new OnBlogLoadListener() {
            @Override
            public void success(final List<BlogBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshDone();
                        mView.showLoadedBlog(list.get(0));
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