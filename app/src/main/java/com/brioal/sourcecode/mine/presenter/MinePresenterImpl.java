package com.brioal.sourcecode.mine.presenter;
import android.os.Handler;

import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.mine.contract.MineContract;
import com.brioal.sourcecode.mine.model.MineModelImpl;

/**
* Created by Brioal on 2017/03/10
*/

public class MinePresenterImpl implements MineContract.Presenter{
    private MineContract.View mView;
    private MineContract.Model mModel;
    private Handler mHandler = new Handler();

    public MinePresenterImpl(MineContract.View view) {
        mView = view;
        mModel = new MineModelImpl();
    }

    @Override
    public void start() {
        //加载博客分享数量
        mModel.loadBlogShareCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogShareCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogShareCount(0);
                    }
                });
            }
        });
        //加载博客收藏数量
        mModel.loadBlogCollectCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogCollectCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBlogCollectCount(0);
                    }
                });
            }
        });
        //加载开源库分享数量
        mModel.loadLibShareCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibShareCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibShareCount(0);
                    }
                });
            }
        });
        //加载开源库收藏数量
        mModel.loadLibCollectCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibCollectCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLibCollectCount(0);
                    }
                });
            }
        });
        //加载阅读数量
        mModel.loadReadCount(mView.getUserBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showReadCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showReadCount(0);
                    }
                });
            }
        });
    }
}