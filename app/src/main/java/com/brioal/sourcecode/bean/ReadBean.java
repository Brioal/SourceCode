package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 阅读记录实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class ReadBean extends BmobObject {
    private UserBean mUserBean;
    private BlogBean mBlogBean;
    private LibBean mLibBean;


    public UserBean getUserBean() {
        return mUserBean;
    }

    public ReadBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    public ReadBean setBlogBean(BlogBean blogBean) {
        mBlogBean = blogBean;
        return this;
    }

    public LibBean getLibBean() {
        return mLibBean;
    }

    public ReadBean setLibBean(LibBean libBean) {
        mLibBean = libBean;
        return this;
    }
}
