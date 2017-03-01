package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.BlogTypeBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public interface OnBlogTypeLoadListener {
    void success(List<BlogTypeBean> list);//加载成功

    void failed(String errorMsg);//加载失败
}
