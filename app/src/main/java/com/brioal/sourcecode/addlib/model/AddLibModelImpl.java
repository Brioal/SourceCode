package com.brioal.sourcecode.addlib.model;

import com.brioal.sourcecode.addlib.contract.AddLibContract;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Brioal on 2017/03/05
 */

public class AddLibModelImpl implements AddLibContract.Model {

    @Override
    public void addLib(LibBean bean, final OnNormalOperatorListener listener) {
        //添加LibBean
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                listener.success("");
            }
        });
    }
}