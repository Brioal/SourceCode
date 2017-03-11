package com.brioal.sourcecode.readhistory.contract;

import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnReadLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class ReadHistoryContract {
    public interface View {
        void showList(List<ReadBean> list);//显示阅读记录

        void showListFailed(String errorMsg);//显示阅读失败

        UserBean getUserBean();//返回当前的用户
    }

    public interface Presenter {
        void start();

        void refresh();
    }

    public interface Model {
        //加载阅读记录
        void loadReadHistory(UserBean userBean, OnReadLoadListener listener);
    }


}