package com.brioal.sourcecode.libdetail.contract;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public class LibDetailContract {
    public interface View {
        void showIsCollect(boolean isCollect);//显示是否收藏

        void showCollectCount(int count);//显示收藏的信息

        void showCommentCount(int commentCount);//显示评论数量

        LibBean getLibBean();//返回博客实体类
    }

    public interface Presenter {
        void start();//默认的初始化

        void collect(boolean isSelected);//是否收藏
    }

    public interface Model {
        void checkCollect(UserBean userBean, LibBean blogBean, OnNormalOperatorListener normalOperatorListener);//检查是否收藏了

        void loadCollectCount(LibBean bean, OnNormalOperatorListener listener);//加载收藏的数量

        void loadCommentCount(LibBean bean, OnNormalOperatorListener listener);
        //加载评论的数量

        //添加收藏
        void addCollect(UserBean userBean, LibBean blogBean, OnNormalOperatorListener listener);

        //删除收藏
        void deleteCollect(UserBean userBean, LibBean blogBean, OnNormalOperatorListener listener);

    }


}