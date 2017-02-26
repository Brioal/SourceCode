package com.brioal.sourcecode.home.contract;

import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class HomeContract {
    public interface View {
        void showRefreshing();//显示正在刷新

        void showRefreshDone();//显示刷新完成

        void showRefreshFailed(String msg);//显示刷新失败

    }

    public interface Presenter {
        void start();//默认的开始

        void refresh();//刷新数据

        void refreshHot();//刷新热门数据

    }

    public interface Model {
        void loadHot(OnBlogLoadListener listener);//加载热门数据

        void loadList(OnBlogLoadListener listener);//加载默认排序的数据
    }
}