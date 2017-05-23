package com.brioal.sourcecode.blogdetail.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogdetail.contract.BlogDetailContract;
import com.brioal.sourcecode.blogdetail.model.BlogDetailModelImpl;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.socks.library.KLog;

import cn.bmob.v3.BmobUser;

/**
 * Created by Brioal on 2017/03/06
 */

public class BlogDetailPresenterImpl implements BlogDetailContract.Presenter {
    private BlogDetailContract.View mView;
    private BlogDetailContract.Model mModel;
    private Handler mHandler = new Handler();

    public BlogDetailPresenterImpl(BlogDetailContract.View view) {
        mView = view;
        mModel = new BlogDetailModelImpl();
    }

    @Override
    public void start() {
        //加载收藏数量
        mModel.loadCollectCount(mView.getBlogBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                KLog.e("CollectCount:" + msg);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCollectCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                KLog.e("CollectCount:" + 0);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCollectCount(0);
                    }
                });
            }
        });
        //加载评论数量
        mModel.loadCommentCount(mView.getBlogBean(), new OnNormalOperatorListener() {
            @Override
            public void success(final String msg) {
                KLog.e("CommentCount:" + msg);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommentCount(Integer.parseInt(msg));
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                KLog.e("CommentCount:" + 0);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommentCount(0);
                    }
                });
            }
        });
        //判断是否收藏
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean == null) {
            return;
        }
        //判断是否收藏
        mModel.checkCollect(userBean, mView.getBlogBean(), new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                KLog.e("CollectDone");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showIsCollect(true);
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                KLog.e("CollectFailed");
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
        //添加或者删除一条收藏
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean == null) {
            return;
        }
        if (isSelected) {
            //添加收藏
            mModel.addCollect(userBean, mView.getBlogBean(), new OnNormalOperatorListener() {
                @Override
                public void success(String msg) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showIsCollect(true);
                        }
                    });
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
                    start();
                }
            });
        } else {
            //删除收藏
            mModel.deleteCollect(userBean, mView.getBlogBean(), new OnNormalOperatorListener() {
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
                    start();
                }
            });
        }
    }

    @Override
    public void addReadRecord(ReadBean readBean) {
        mModel.addRead(readBean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                KLog.e("添加阅读记录成功");
            }

            @Override
            public void failed(String errorMsg) {
                KLog.e("添加博客阅读记录失败");
            }
        });
    }

}