package com.brioal.sourcecode.packagelist.contract;

import android.content.Context;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/5.
 */

public class PackageContract {
    public interface View {
        void showLoading();//显示正在加载

        void showLoadingDone(List<IndexBean> list);//显示加载完成

        void showLoadingFailed(String errorMsg);//显示加载失败

        Context getContext();//返回上下文
    }

    public interface Presenter {
        void start();//默认的初始化
    }

    public interface Model {
        //加载Package列表
        void loadPackages(OnApiBeanLoadListener listener);
    }


}