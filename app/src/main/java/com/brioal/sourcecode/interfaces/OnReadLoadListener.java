package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.ReadBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public interface OnReadLoadListener {
    void success(List<ReadBean> list);

    void failed(String errorMsg);
}
