package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 博客评论实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/26.
 */

public class BlogCommentBean extends BmobObject {
    private BlogBean mBlogBean;//博客的ID
    private UserBean mUserBean;//用户的ID
    private String mContent;//评论的内容
    private BlogCommentBean mParentBean;//父评论的ID


    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    public BlogCommentBean setBlogBean(BlogBean blogBean) {
        mBlogBean = blogBean;
        return this;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public BlogCommentBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public BlogCommentBean setContent(String content) {
        mContent = content;
        return this;
    }

    public BlogCommentBean getParentBean() {
        return mParentBean;
    }

    public BlogCommentBean setParentBean(BlogCommentBean parentBean) {
        mParentBean = parentBean;
        return this;
    }
}
