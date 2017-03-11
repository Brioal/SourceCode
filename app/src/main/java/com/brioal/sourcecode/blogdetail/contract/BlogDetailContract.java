package com.brioal.sourcecode.blogdetail.contract;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/6.
 */

public class BlogDetailContract {
    public interface View {
        void showIsCollect(boolean isCollect);//显示是否收藏

        void showCollectCount(int count);//显示收藏的信息

        void showCommentCount(int commentCount);//显示评论数量

        BlogBean getBlogBean();//返回博客实体类
    }

    public interface Presenter {
        void start();//默认的初始化

        void collect(boolean isSelected);//是否收藏

        void addReadRecord(ReadBean readBean);//添加博客阅读记录
    }

    public interface Model {
        void checkCollect(UserBean userBean, BlogBean blogBean, OnNormalOperatorListener normalOperatorListener);//检查是否收藏了

        void loadCollectCount(BlogBean bean, OnNormalOperatorListener listener);//加载收藏的数量

        //加载评论的数量
        void loadCommentCount(BlogBean bean, OnNormalOperatorListener listener);

        //添加收藏
        void addCollect(UserBean userBean, BlogBean blogBean, OnNormalOperatorListener listener);

        //删除收藏
        void deleteCollect(UserBean userBean, BlogBean blogBean, OnNormalOperatorListener listener);

        //添加阅读实体类
        void addRead(ReadBean readBean, OnNormalOperatorListener listener);

    }


}