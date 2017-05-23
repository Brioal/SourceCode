package com.brioal.sourcecode.lib.model;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.lib.contract.LibContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/02
 */

public class LibModelImpl implements LibContract.Model {

    @Override
    public void loadLibList(final OnLibLoadListener listener) {
        BmobQuery<LibBean> query = new BmobQuery<>();
        query.include("mUserBean");
        query.order("-createdAt");
        query.findObjects(new FindListener<LibBean>() {
            @Override
            public void done(List<LibBean> list, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("找不到对应的数据");
                    return;
                }
                listener.success(list);
            }
        });
    }
}