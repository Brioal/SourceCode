package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 博客收藏实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/26.
 */

public class BlogCollectionBean extends BmobObject {
    private BlogBean mBlogBean;//博客的ID
    private UserBean mUserBean;//用户的ID
    private long mTime;//时间

    public long getTime() {
        return mTime;
    }

    public BlogCollectionBean setTime(long time) {
        mTime = time;
        return this;
    }

    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    public BlogCollectionBean setBlogBean(BlogBean blogBean) {
        mBlogBean = blogBean;
        return this;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public BlogCollectionBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }
}
