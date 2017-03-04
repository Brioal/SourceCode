package com.brioal.sourcecode.api.contract;

import android.content.Context;

import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/3.
 */

public class ApiContract {
    public interface View {
        void showLoading();//显示正在加载

        void showHistory(List<String> list);//显示历史记录

        void showLoadingDone();//显示加载完成

        void showLoadingFailed(String errorMsg);//显示加载失败


        Context getContext();//返回上下文
    }

    public interface Presenter {
        void start();//默认的初始化

        void saveHistory(String key);

    }

    public interface Model {
        //判断并且迁移数据
        void transData();

        //加载历史记录
        void loadHistory(OnHistpryLoadListener loadListener);

        //保存历史记录
        void saveHistory(String key, OnNormalOperatorListener listener);
    }


}