package com.brioal.sourcecode.interfaces;

/**
 * 通用的操作结果监视器
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public interface OnNormalOperatorListener {
    void success(String msg);//成功

    void failed(String errorMsg);//失败
}
