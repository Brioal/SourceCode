package com.brioal.sourcecode.addblog.contract;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogTypeBean;
import com.brioal.sourcecode.interfaces.OnBlogTypeLoadListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/1.
 */

public class BlogAddContract {
    public interface View {
        void showLoading();//显示正在加载

        void showType(List<BlogTypeBean> list);//显示博客类型

        void showLoadFailed(String errorMsg);//显示加载博客类型失败

        void showAdding();//显示正在加载

        void showAddingDone();//显示加载成功

        void showAddingFailed(String errorMsg);//显示添加博客失败

    }

    public interface Presenter {
        void start();//默认的开始 , 加载博客类型


        void addBlog(String coverUrl,BlogBean blogBean);//添加博客
    }

    public interface Model {
        //加载博客类型
        void loadBlogType(OnBlogTypeLoadListener loadListener);

        //添加博客
        void addBlog(String coverUrl,BlogBean blogBean, OnNormalOperatorListener normalOperatorListener);
    }


}