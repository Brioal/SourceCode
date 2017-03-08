package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.BlogCommentBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/7.
 */

public interface OnLoadCommentListener {
    void success(List<BlogCommentBean> list);

    void failed(String errorMsg);
}
