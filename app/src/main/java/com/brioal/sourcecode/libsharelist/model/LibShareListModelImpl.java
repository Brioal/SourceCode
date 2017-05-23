package com.brioal.sourcecode.libsharelist.model;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.libsharelist.contract.LibShareListContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
* Created by Brioal on 2017/03/11
*/

public class LibShareListModelImpl implements LibShareListContract.Model{

    @Override
    public void loadLibList(UserBean userBean, final OnLibLoadListener listener) {
        if (listener == null) {
            return;
        }
        //加载当前用户分享的开源库列表
        BmobQuery<LibBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.include("mUserBean");
        query.order("-createdAt");
        query.findObjects(new FindListener<LibBean>() {
            @Override
            public void done(List<LibBean> list, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("找不到数据");
                    return;
                }
                listener.success(list);
            }
        });
    }
}