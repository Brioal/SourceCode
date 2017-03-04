package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.LibBean;

import java.util.List;

/**
 * 加载开源库的监听器
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/2.
 */

public interface OnLibLoadListener {
    void success(List<LibBean> libBeen);

    void failed(String msg);
}
