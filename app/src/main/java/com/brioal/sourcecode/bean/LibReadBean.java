package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class LibReadBean extends BmobObject {
    private LibBean mLibBean;//阅读的开源库实体类
    private UserBean mUserBean;//阅读的用户实体类
    private long mTime;//阅读的时间

    public LibBean getLibBean() {
        return mLibBean;
    }

    public void setLibBean(LibBean libBean) {
        mLibBean = libBean;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
