package com.brioal.sourcecode.lib.contract;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/2.
 */

public class LibContract {
    public interface View {
        void showRefresh();//显示正在刷新

        void showRefreshDone();//显示刷新完成

        void showLib(List<LibBean> list);//显示开源库列表

        void showRefreshFailed(String errorMsg);//显示刷新失败
    }

    public interface Presenter {
        void start();//默认的开始

        void refresh();//刷新

    }

    public interface Model {
        void loadLibList(OnLibLoadListener listener);//加载开源库列表
    }


}