package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 博客收藏实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/26.
 */

public class BlogCollectionBean extends BmobObject {
    private String mBlogID;//博客的ID
    private String mUserID;//用户的ID

    public String getBlogID() {
        return mBlogID;
    }

    public BlogCollectionBean setBlogID(String blogID) {
        mBlogID = blogID;
        return this;
    }

    public String getUserID() {
        return mUserID;
    }

    public BlogCollectionBean setUserID(String userID) {
        mUserID = userID;
        return this;
    }
}
