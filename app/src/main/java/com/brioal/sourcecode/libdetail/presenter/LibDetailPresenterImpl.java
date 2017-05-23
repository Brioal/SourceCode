package com.brioal.sourcecode.libdetail.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.libdetail.contract.LibDetailContract;
import com.brioal.sourcecode.libdetail.model.LibDetailModelImpl;

import cn.bmob.v3.BmobUser;

/**
 * Created by Brioal on 2017/03/08
 */

public class LibDetailPresenterImpl implements LibDetailContract.Presenter {
    private LibDetailContract.View mView;
    private LibDetailContract.Model mModel;
    private Handler mHandler = new Handler();

    public LibDetailPresenterImpl(LibDetailContract.View view) {
        mView = view;
        mModel = new LibDetailModelImpl();
    }

    @Override
    public void start() {
        //加载收藏数量
        mModel.loadCollectCount(mView.getLibBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCollectCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCollectCount(0);
                    }
                });
            }
        });
        //加载评论数量
        mModel.loadCommentCount(mView.getLibBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommentCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommentCount(0);
                    }
                });
            }
        });
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean == null) {
            return;
        }
        //判断是否收藏
        mModel.checkCollect(userBean, mView.getLibBean(), new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showIsCollect(true);
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showIsCollect(false);
                    }
                });
            }
        });

    }

    @Override
    public void collect(boolean isSelected) {
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        //添加收藏或者取消收藏
        if (isSelected) {
            //添加收藏
            mModel.addCollect(userBean, mView.getLibBean(), new OnNormalOperatorListener() {
                @Override
                public void success(String msg) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showIsCollect(true);
                        }
                    });
                    //刷新
                    start();
                }

                @Override
                public void failed(String errorMsg) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showIsCollect(false);
                        }
                    });
                }
            });
        } else {
            //取消收藏
            mModel.deleteCollect(userBean, mView.getLibBean(), new OnNormalOperatorListener() {
                @Override
                public void success(String msg) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showIsCollect(false);
                        }
                    });
                    start();
                }

                @Override
                public void failed(String errorMsg) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showIsCollect(true);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void addReadRecord(ReadBean bean) {
        mModel.addReadRecord(bean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {

            }

            @Override
            public void failed(String errorMsg) {

            }
        });
    }
}