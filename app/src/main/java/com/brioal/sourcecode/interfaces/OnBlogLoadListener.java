package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.BlogBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public interface OnBlogLoadListener {
    void success(List<BlogBean> list);//加载成功


    void failed(String errorMsg);//加载失败
}
