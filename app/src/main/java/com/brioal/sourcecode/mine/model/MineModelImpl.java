package com.brioal.sourcecode.mine.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.mine.contract.MineContract;
import com.socks.library.KLog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by Brioal on 2017/03/10
 */

public class MineModelImpl implements MineContract.Model {


    @Override
    public void loadBlogShareCount(final UserBean userBean, final OnNormalOperatorListener listener) {
        //加载收藏数量
        if (listener == null) {
            return;
        }
        if (userBean == null) {
            listener.failed("未登录");
            return;
        }
        //加载博客分享
        BmobQuery<BlogBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.count(BlogBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null && integer != null) {
                    listener.success(integer + "");
                } else {
                    //加载失败
                    listener.failed("");
                }
            }
        });
    }

    @Override
    public void loadLibShareCount(UserBean userBean, final OnNormalOperatorListener listener) {
        //加载收藏数量
        if (listener == null) {
            return;
        }
        if (userBean == null) {
            listener.failed("未登录");
            return;
        }
        BmobQuery<LibBean> libQuery = new BmobQuery<LibBean>();
        libQuery.addWhereEqualTo("mUserBean", userBean);
        libQuery.count(LibBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null && integer != null) {
                    listener.success(integer + "");
                } else {
                    //加载失败
                    listener.failed("");
                }
            }
        });
    }

    @Override
    public void loadBlogCollectCount(final UserBean userBean, final OnNormalOperatorListener listener) {
        //加载收藏数量
        if (userBean == null) {
            listener.failed("未登录");
            return;
        }
        if (userBean == null) {
            return;
        }
        //加载博客收藏数量
        BmobQuery<BlogCollectionBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.count(BlogCollectionBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null && integer != null) {
                    listener.success(integer + "");
                } else {
                    //加载失败
                    listener.failed("");
                }
            }
        });
    }

    @Override
    public void loadLibCollectCount(UserBean userBean, final OnNormalOperatorListener listener) {
        //加载收藏数量
        if (listener == null) {
            return;
        }
        if (userBean == null) {
            listener.failed("未登录");
            return;
        }
        BmobQuery<LibCollectBean> libQuery = new BmobQuery<LibCollectBean>();
        libQuery.addWhereEqualTo("mUserBean", userBean);
        libQuery.count(LibCollectBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null && integer != null) {
                    listener.success(integer + "");
                } else {
                    //加载失败
                    listener.failed("");
                }
            }
        });
    }

    @Override
    public void loadReadCount(final UserBean userBean, final OnNormalOperatorListener listener) {
        //加载阅读数量
        if (listener == null) {
            return;
        }
        if (userBean == null) {
            listener.failed("未登录");
            return;
        }
        //加载阅读记录
        BmobQuery<ReadBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.count(ReadBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                KLog.e(integer);
                if (e == null && integer != null) {
                    listener.success(integer + "");
                } else {
                    //加载失败
                    listener.failed("");
                }
            }
        });
    }
}