package com.brioal.sourcecode.blogsharelist.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogsharelist.contract.BlogShareContract;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/10
 */

public class BlogShareModelImpl implements BlogShareContract.Model {

    @Override
    public void loadShareList(UserBean userBean, final OnBlogLoadListener listener) {
        if (listener == null) {
            return;
        }
        if (userBean == null) {
            return;
        }
        //加载用户收藏博客的列表
        BmobQuery<BlogBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.order("-createdAt");
        query.include("mUserBean");
        query.findObjects(new FindListener<BlogBean>() {
            @Override
            public void done(List<BlogBean> list, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("无数据");
                    return;
                }
                listener.success(list);
            }
        });
    }
}