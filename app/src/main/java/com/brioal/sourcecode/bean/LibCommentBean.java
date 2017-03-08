package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public class LibCommentBean extends BmobObject {
    private UserBean mUserBean;//评论的用户
    private LibBean mLibBean;//评论的开源库
    private String mContent;//评论内容
    private long mTime;//评论的时间
    private String mParentID;//父评论的ID

    public UserBean getUserBean() {
        return mUserBean;
    }

    public LibCommentBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public LibBean getLibBean() {
        return mLibBean;
    }

    public LibCommentBean setLibBean(LibBean libBean) {
        mLibBean = libBean;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public LibCommentBean setContent(String content) {
        mContent = content;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public LibCommentBean setTime(long time) {
        mTime = time;
        return this;
    }

    public String getParentID() {
        return mParentID;
    }

    public LibCommentBean setParentID(String parentID) {
        mParentID = parentID;
        return this;
    }
}

