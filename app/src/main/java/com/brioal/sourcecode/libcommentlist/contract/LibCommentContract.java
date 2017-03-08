package com.brioal.sourcecode.libcommentlist.contract;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.LibCommentBean;
import com.brioal.sourcecode.interfaces.OnLibCommentListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

/**
 * 评论列表
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/7.
 */

public class LibCommentContract {

    public interface View {
        void showLoading();//显示正在加载

        void showComment(List<LibCommentBean> list);//显示评论列表

        void showCommentFailed();//加载评论列表失败

        void showAdding();//显示正在添加评论

        void showAddingDone();//显示添加评论成功

        void showAddingFailed(String errorMsg);//显示添加评论失败

        void showDeleting();//显示正在删除评论

        void showDeletingDone();//显示删除成功

        void showDeletingFailed(String errorMsg);//显示删除失败

        LibBean getLibBean();//返回博客实体类

    }

    public interface Presenter {
        void start();//默认的开始

        void refresh();//刷新

        void addComment(LibCommentBean bean);//添加评论

        void deleteComment(LibCommentBean bean);//删除评论
    }

    public interface Model {
        //加载评论
        void loadComment(LibBean bean, OnLibCommentListener listener);

        //添加评论
        void addComment(LibCommentBean bean, OnNormalOperatorListener listener);

        //删除评论
        void deleteComment(LibCommentBean bean, OnNormalOperatorListener listener);
    }


}