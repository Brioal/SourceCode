package com.brioal.sourcecode.register.model;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.register.contract.RegisterContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Brioal on 2017/03/01
 */

public class RegisterModelImpl implements RegisterContract.Model {

    @Override
    public void register(UserBean bean, final OnNormalOperatorListener listener) {
        //注册
        bean.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean s, BmobException e) {
                if (e == null) {
                    listener.success(s.getObjectId());
                } else {
                    listener.failed(e.getMessage());
                }
            }
        });
    }
}