package com.brioal.sourcecode.bean;

import java.io.Serializable;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/4.
 */

public class ApiBean implements Serializable {
    private String mName;//api的名称
    private String mUrl;//api的地址
    private int mType;//api的类型

    public ApiBean(String name, String url, int type) {
        mName = name;
        mUrl = url;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public ApiBean setName(String name) {
        mName = name;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public ApiBean setUrl(String url) {
        mUrl = url;
        return this;
    }

    public int getType() {
        return mType;
    }

    public ApiBean setType(int type) {
        mType = type;
        return this;
    }
}
