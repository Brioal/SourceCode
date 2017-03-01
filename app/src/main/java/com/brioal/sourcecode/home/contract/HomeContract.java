package com.brioal.sourcecode.home.contract;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

import java.util.List;

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

        int getSortType();//返回排序方法

        void showHot(List<BlogBean> list);//显示热门内容

        void showList(List<BlogBean> list);//显示数据

        void showLoading();//显示正在加载

        void showLoadingDone();//显示加载完成

        void showLoadingFailed(String msg);//显示加载失败

        void showLoadedBlog(BlogBean blogBean);//显示加载之后的博客信息

    }

    public interface Presenter {
        void start();//默认的开始

        void refresh();//刷新数据

        void refreshHot();//刷新热门数据

        void getUrlInfo(String url);//根据连接或者博客消息

    }

    public interface Model {
        void loadHot(OnBlogLoadListener listener);//加载热门数据

        void loadList(int sortType, OnBlogLoadListener listener);//加载指定排序的数据

        void loadBlog(String blogUrl, OnBlogLoadListener listener);//加载指定博客地址的数据
    }
}