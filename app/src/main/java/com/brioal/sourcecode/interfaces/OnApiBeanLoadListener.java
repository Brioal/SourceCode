package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.ApiBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/4.
 */

public interface OnApiBeanLoadListener {
    //加载成功
    void success(List<ApiBean> list);

    //加载失败
    void failed(String errorMsg);
}
