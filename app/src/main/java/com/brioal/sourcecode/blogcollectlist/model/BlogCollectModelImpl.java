package com.brioal.sourcecode.blogcollectlist.model;

import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogcollectlist.contract.BlogCollectContract;
import com.brioal.sourcecode.interfaces.OnBlogCollectListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/11
 */

public class BlogCollectModelImpl implements BlogCollectContract.Model {

    @Override
    public void loadBlogCollect(UserBean userBean, final OnBlogCollectListener listener) {
        //加载博客收藏列表
        if (userBean == null) {
            return;
        }
        if (listener == null) {
            return;
        }
        BmobQuery<BlogCollectionBean> query = new BmobQuery<>();
        query.include("mBlogBean.mUserBean");
        query.addWhereEqualTo("mUserBean", userBean);
        query.order("-createdAt");
        query.findObjects(new FindListener<BlogCollectionBean>() {
            @Override
            public void done(List<BlogCollectionBean> list, BmobException e) {

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