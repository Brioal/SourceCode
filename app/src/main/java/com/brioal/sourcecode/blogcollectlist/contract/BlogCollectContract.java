package com.brioal.sourcecode.blogcollectlist.contract;

import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnBlogCollectListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class BlogCollectContract {
    public interface View {
        void showList(List<BlogCollectionBean> list);//显示收藏列表

        void showListFailed(String errorMsg);//显示收藏失败

        UserBean getUserBean();//返回当前的用户
    }

    public interface Presenter {
        void start();//默认的开始

        void refresh();
    }

    public interface Model {
        //加载收藏的博客列表
        void loadBlogCollect(UserBean userBean, OnBlogCollectListener listener);
    }


}