package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 博客评论实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/26.
 */

public class BlogCommentBean extends BmobObject {
    private String mBlogID;//博客的ID
    private String mUserID;//用户的ID
    private String mContent;//评论的内容
    private String mParentID;//父评论的ID


    public String getBlogID() {
        return mBlogID;
    }

    public BlogCommentBean setBlogID(String blogID) {
        mBlogID = blogID;
        return this;
    }

    public String getUserID() {
        return mUserID;
    }

    public BlogCommentBean setUserID(String userID) {
        mUserID = userID;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public BlogCommentBean setContent(String content) {
        mContent = content;
        return this;
    }

    public String getParentID() {
        return mParentID;
    }

    public BlogCommentBean setParentID(String parentID) {
        mParentID = parentID;
        return this;
    }
}
