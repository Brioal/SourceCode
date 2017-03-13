package com.brioal.sourcecode.mine.contract;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class MineContract {
    public interface View {
        void showBlogShareCount(int count);//显示分享博客的数量

        void showLibShareCount(int count);//显示分享开源库的数量

        void showBlogCollectCount(int count);//显示收藏博客的数量

        void showLibCollectCount(int count);//显示收藏开源库的数量

        void showReadCount(int count);//显示阅读的数量

        UserBean getUserBean();//返回当前的用户
    }

    public interface Presenter {
        void start();//默认的开始
    }

    public interface Model {
        //加载分享的博客数量
        void loadBlogShareCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载分享的开源库数量
        void loadLibShareCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载收藏的博客数量
        void loadBlogCollectCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载收藏的开源库数量
        void loadLibCollectCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载阅读的数量
        void loadReadCount(UserBean userBean, OnNormalOperatorListener listener);
    }


}