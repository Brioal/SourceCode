package com.brioal.sourcecode.readhistory.model;

import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnReadLoadListener;
import com.brioal.sourcecode.readhistory.contract.ReadHistoryContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/11
 */

public class ReadHistoryModelImpl implements ReadHistoryContract.Model {

    @Override
    public void loadReadHistory(UserBean userBean, final OnReadLoadListener listener) {
        //加载当前用户的阅读记录
        if (userBean == null) {
            return;
        }
        if (listener == null) {
            return;
        }
        BmobQuery<ReadBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.include("mBlogBean.mUserBean,mLibBean.mUserBean,mUserBean");
        query.order("-createdAt");
        query.findObjects(new FindListener<ReadBean>() {
            @Override
            public void done(List<ReadBean> list, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("");
                    return;
                }
                listener.success(list);
            }
        });
    }
}