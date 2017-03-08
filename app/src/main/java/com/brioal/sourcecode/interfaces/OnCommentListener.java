package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.BlogCommentBean;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public interface OnCommentListener {
    void click(BlogCommentBean bean);//点击事件ain

    void longClickListener(BlogCommentBean bean);//长按事件
}
