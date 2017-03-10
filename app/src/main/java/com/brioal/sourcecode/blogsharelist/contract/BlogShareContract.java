package com.brioal.sourcecode.blogsharelist.contract;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class BlogShareContract {
    public interface View {
        void showList(List<BlogBean> list);//显示分享列表

        void showLoadFailed(String errorMsg);//显示加载失败

        UserBean getUserBean();//返回用户
    }

    public interface Presenter {
        void start();//默认的初始化

        void refresh();//刷新
    }

    public interface Model {
        //加载分享的列表
        void loadShareList(UserBean userBean, OnBlogLoadListener listener);
    }


}