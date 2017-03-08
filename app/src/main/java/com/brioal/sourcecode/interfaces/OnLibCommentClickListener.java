package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.LibCommentBean;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public interface OnLibCommentClickListener {
    void click(LibCommentBean bean);//点击事件ain

    void longClickListener(LibCommentBean bean);//长按事件
}
