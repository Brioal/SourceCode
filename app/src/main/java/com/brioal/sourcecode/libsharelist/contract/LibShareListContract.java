package com.brioal.sourcecode.libsharelist.contract;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class LibShareListContract {
    public interface View {
        void showList(List<LibBean> list);//显示开源库分享列表

        void showListFailed(String errorMsg);//显示加载开源库失败

        UserBean getUserBean();//返回当前用户
    }

    public interface Presenter {
        void start();//默认的初始化

        void refresh();//刷新
    }

    public interface Model {
        //加载开源库列表
        void loadLibList(UserBean userBean, OnLibLoadListener listener);
    }


}