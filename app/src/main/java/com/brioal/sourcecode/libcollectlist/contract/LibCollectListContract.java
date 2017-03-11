package com.brioal.sourcecode.libcollectlist.contract;

import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnLibCollectLoadListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class LibCollectListContract {
    public interface View {
        void showList(List<LibCollectBean> list);//显示开源库收藏列表

        void showListFailed(String errorMsg);//显示开源库收藏失败

        UserBean getUserBean();//返回当前用户
    }

    public interface Presenter {
        void start();//默认的初始化

        void refresh();//刷新
    }

    public interface Model {
        //加载收藏的开源库
        void loadCollectLib(UserBean userBean, OnLibCollectLoadListener listener);
    }


}