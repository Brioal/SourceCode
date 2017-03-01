package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 博客实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class BlogBean extends BmobObject {
    private UserBean mUserBean;//分享者
    private String mTitle;//标题
    private String mDesc;//描述
    private String mUrl;//博客地址
    private long mTime;//发表时间
    private BlogTypeBean mTypeBean;//所属分类
    private BmobFile mImg;//文章配图
    private int mCollectCount = 0;//文章收藏数量
    private int mCommentCount = 0;//文章评论数量

    public int getCollectCount() {
        return mCollectCount;
    }

    public BlogBean setCollectCount(int collectCount) {
        mCollectCount = collectCount;
        return this;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public BlogBean setCommentCount(int commentCount) {
        mCommentCount = commentCount;
        return this;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public BlogBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public BlogBean setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getDesc() {
        return mDesc;
    }

    public BlogBean setDesc(String desc) {
        mDesc = desc;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public BlogBean setUrl(String url) {
        mUrl = url;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public BlogBean setTime(long time) {
        mTime = time;
        return this;
    }

    public BlogTypeBean getTypeBean() {
        return mTypeBean;
    }

    public BlogBean setTypeBean(BlogTypeBean typeBean) {
        mTypeBean = typeBean;
        return this;
    }

    public BmobFile getImg() {
        return mImg;
    }

    public BlogBean setImg(BmobFile img) {
        mImg = img;
        return this;
    }
}
