package com.brioal.sourcecode.libcollect.model;

import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnLibCollectLoadListener;
import com.brioal.sourcecode.libcollect.contract.LibCollectListContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/11
 */

public class LibCollectListModelImpl implements LibCollectListContract.Model {

    @Override
    public void loadCollectLib(UserBean userBean, final OnLibCollectLoadListener listener) {
        if (userBean == null) {
            return;
        }
        if (listener == null) {
            return;
        }
        //加载收藏的开源库
        BmobQuery<LibCollectBean> query = new BmobQuery<>();
        query.include("mLibBean.mUserBean");
        query.addWhereEqualTo("mUserBean", userBean);
        query.order("-createdAt");
        query.findObjects(new FindListener<LibCollectBean>() {
            @Override
            public void done(List<LibCollectBean> list, BmobException e) {
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