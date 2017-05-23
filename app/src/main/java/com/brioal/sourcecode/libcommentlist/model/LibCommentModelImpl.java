package com.brioal.sourcecode.libcommentlist.model;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.LibCommentBean;
import com.brioal.sourcecode.interfaces.OnLibCommentListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.libcommentlist.contract.LibCommentContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/03/09
 */

public class LibCommentModelImpl implements LibCommentContract.Model {

    @Override
    public void loadComment(LibBean bean, final OnLibCommentListener listener) {
        //加载评论
        BmobQuery<LibCommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mLibBean", bean);
        query.order("-createdAt");
        query.include("mUserBean");
        query.findObjects(new FindListener<LibCommentBean>() {
            @Override
            public void done(List<LibCommentBean> list, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("没有评论");
                    return;
                }
                listener.success(list);
            }
        });
    }

    @Override
    public void addComment(LibCommentBean bean, final OnNormalOperatorListener listener) {
        //添加评论
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                listener.success("");
            }
        });
    }

    @Override
    public void deleteComment(LibCommentBean bean, final OnNormalOperatorListener listener) {
        //删除评论
        bean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                listener.success("");
            }
        });
    }
}