package com.brioal.sourcecode.addlib.contract;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/5.
 */

public class AddLibContract {
    public interface View {
        void showAdding();//显示正在添加

        void showAddingDone();//显示添加成功

        void showAddingFailed(String errorMsg);//显示添加失败
    }

    public interface Presenter {
        void start();//默认的初始化

        void add(LibBean bean);//添加开源库
    }

    public interface Model {
        //添加开源库
        void addLib(LibBean bean, OnNormalOperatorListener listener);
    }


}