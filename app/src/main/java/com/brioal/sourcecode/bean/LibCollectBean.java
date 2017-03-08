package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * 开源库收藏实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public class LibCollectBean extends BmobObject {
    private UserBean mUserBean;//用户
    private LibBean mLibBean;//开源库
    private long mTime;//收藏的时间


    public UserBean getUserBean() {
        return mUserBean;
    }

    public LibCollectBean setUserBean(UserBean userBean) {
        mUserBean = userBean;
        return this;
    }

    public LibBean getLibBean() {
        return mLibBean;
    }

    public LibCollectBean setLibBean(LibBean libBean) {
        mLibBean = libBean;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public LibCollectBean setTime(long time) {
        mTime = time;
        return this;
    }
}
