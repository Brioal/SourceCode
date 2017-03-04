package com.brioal.sourcecode.interfaces;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/3.
 */

public interface OnHistpryLoadListener {
    void success(List<String> list);//加载成功

    void failed(String msg);//加载失败
}
