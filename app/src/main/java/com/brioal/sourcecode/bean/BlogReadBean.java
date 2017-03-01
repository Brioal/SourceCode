package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 博客阅读实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/27.
 */

public class BlogReadBean extends BmobObject {
    private BlogBean mBlogBean;//阅读的博客实体类
    private UserBean mUserBean;//阅读的用户实体类
    private long mTime;//阅读的时间

    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    public BlogReadBean setBlogBean(BlogBean blogBean) {
        mBlogBean = blogBean;
        return this;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public BlogReadBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public BlogReadBean setTime(long time) {
        mTime = time;
        return this;
    }
}
