package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.BlogCollectionBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public interface OnBlogCollectListener {
    void success(List<BlogCollectionBean> list);//加载成功

    void failed(String errorMsg);//加载失败
}
