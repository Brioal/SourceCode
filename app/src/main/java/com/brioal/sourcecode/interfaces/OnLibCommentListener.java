package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.LibCommentBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/9.
 */

public interface OnLibCommentListener {
    void success(List<LibCommentBean> lis);//加载开源库评论列表成功

    void failed(String errorMsg);//加载开源库评论列表失败
}
