package com.brioal.sourcecode.userinfo.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.userinfo.contract.UserInfoContract;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by brioal on 2017/03/14
 */

public class UserInfoModelImpl implements UserInfoContract.Model {

    @Override
    public void loadBlogCount(UserBean userBean, final OnNormalOperatorListener listener) {
        //加载博客计数
        if (listener == null) {
            return;
        }
        BmobQuery<BlogBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.count(BlogBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (integer == null || integer == 0) {
                    listener.failed("");
                    return;
                }
                listener.success(integer + "");
            }
        });
    }

    @Override
    public void loadLibCount(UserBean userBean, final OnNormalOperatorListener listener) {
        //加载开源库计数
        if (listener == null) {
            return;
        }
        BmobQuery<LibBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.count(LibBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (integer == null || integer == 0) {
                    listener.failed("");
                    return;
                }
                listener.success(integer + "");
            }
        });
    }
}