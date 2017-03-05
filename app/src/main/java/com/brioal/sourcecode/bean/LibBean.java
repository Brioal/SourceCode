package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 开源库实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/2.
 */

public class LibBean extends BmobObject {
    private String mTitle;//标题
    private String mDesc;//描述
    private String mUrl;//链接
    private String mLabel;//标签
    private UserBean mUserBean;//分享的用户
    private int mCollectCount = 0;//收藏数量
    private int mCommentCount = 0;//评论数量

    public int getCollectCount() {
        return mCollectCount;
    }

    public LibBean setCollectCount(int collectCount) {
        mCollectCount = collectCount;
        return this;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public LibBean setCommentCount(int commentCount) {
        mCommentCount = commentCount;
        return this;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public LibBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public String getLabel() {
        return mLabel;
    }

    public LibBean setLabel(String label) {
        mLabel = label;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public LibBean setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getDesc() {
        return mDesc;
    }

    public LibBean setDesc(String desc) {
        mDesc = desc;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public LibBean setUrl(String url) {
        mUrl = url;
        return this;
    }

}
