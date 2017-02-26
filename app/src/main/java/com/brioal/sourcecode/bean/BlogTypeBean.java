package com.brioal.sourcecode.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 博客类型实体类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class BlogTypeBean extends BmobObject {
    private String mName;//名称
    private BmobFile mIcon;//Icon

    public BlogTypeBean(String name, BmobFile icon) {
        mName = name;
        mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public BlogTypeBean setName(String name) {
        mName = name;
        return this;
    }

    public BmobFile getIcon() {
        return mIcon;
    }

    public BlogTypeBean setIcon(BmobFile icon) {
        mIcon = icon;
        return this;
    }
}
