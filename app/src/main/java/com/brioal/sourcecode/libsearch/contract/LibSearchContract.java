package com.brioal.sourcecode.libsearch.contract;

import android.content.Context;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/5.
 */

public class LibSearchContract {
    public interface View {

        void showHistory(List<String> list);//显示搜索记录

        void showSearchResult(List<LibBean> list);//显示搜索结果

        void showLoading();//显示正在加载

        void showLoadingDone();//显示加载完成

        void showLoadingFailed(String errorMsg);//显示加载完成

        Context getContext();//返回上下文

    }

    public interface Presenter {
        void start();//默认搜索

        void search(String key);//开始搜索
    }

    public interface Model {
        void loadHistory(OnHistpryLoadListener listener);//加载搜索历史

        void loadResult(String key, OnLibLoadListener listener);//加载搜索结果

    }


}