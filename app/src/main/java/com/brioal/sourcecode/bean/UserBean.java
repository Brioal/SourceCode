package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class UserBean extends BmobUser {
    private BmobFile mHead;//头像
    private String mDesc;//描述
    private String mBlogUrl;//博客地址
    private String mPro;//职业
    private String mCompany;

    public BmobFile getHead() {
        return mHead;
    }

    public UserBean setHead(BmobFile head) {
        mHead = head;
        return this;
    }



    public String getDesc() {
        return mDesc;
    }

    public UserBean setDesc(String desc) {
        mDesc = desc;
        return this;
    }

    public String getBlogUrl() {
        return mBlogUrl;
    }

    public UserBean setBlogUrl(String blogUrl) {
        mBlogUrl = blogUrl;
        return this;
    }

    public String getPro() {
        return mPro;
    }

    public UserBean setPro(String pro) {
        mPro = pro;
        return this;
    }

    public String getCompany() {
        return mCompany;
    }

    public UserBean setCompany(String company) {
        mCompany = company;
        return this;
    }
}
