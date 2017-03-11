package com.brioal.sourcecode.interfaces;

import com.brioal.sourcecode.bean.LibCollectBean;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public interface OnLibCollectLoadListener {
    void success(List<LibCollectBean> list);//加载成功

    void failed(String errorMsg);//加载失败
}
