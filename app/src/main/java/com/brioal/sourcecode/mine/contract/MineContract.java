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
        void showShareCount(int count);//显示分享的数量

        void showCollectCount(int count);//显示收藏的数量

        void showReadCount(int count);//显示阅读的数量

        UserBean getUserBean();//返回当前的用户
    }

    public interface Presenter {
        void start();//默认的开始
    }

    public interface Model {
        //加载分享的数量
        void loadShareCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载收藏的数量
        void loadCollectCount(UserBean userBean, OnNormalOperatorListener listener);

        //加载阅读的数量
        void loadReadCount(UserBean userBean, OnNormalOperatorListener listener);
    }


}