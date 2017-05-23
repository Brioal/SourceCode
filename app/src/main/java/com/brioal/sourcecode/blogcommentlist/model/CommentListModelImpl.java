package com.brioal.sourcecode.blogcommentlist.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogCommentBean;
import com.brioal.sourcecode.blogcommentlist.contract.CommentListContract;
import com.brioal.sourcecode.interfaces.OnLoadCommentListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/03/07
 */

public class CommentListModelImpl implements CommentListContract.Model {

    @Override
    public void loadComment(BlogBean bean, final OnLoadCommentListener listener) {
        if (listener == null) {
            return;
        }
        //加载所有评论
        BmobQuery<BlogCommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mBlogBean", bean);
        query.include("mUserBean");
        query.order("-createdAt");
        query.findObjects(new FindListener<BlogCommentBean>() {
            @Override
            public void done(List<BlogCommentBean> list, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("找不到数据");
                    return;
                }
                //
                listener.success(list);
            }
        });

    }

    @Override
    public void addComment(final BlogCommentBean bean, final OnNormalOperatorListener listener) {
        //添加评论
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                //自增
                BlogBean blogBean = bean.getBlogBean();
                blogBean.increment("mCommentCount");
                blogBean.update(new UpdateListener() {
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
        });
    }

    @Override
    public void deleteComment(BlogCommentBean bean, final OnNormalOperatorListener listener) {
        //删除评论
        bean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e == null) {
                    listener.success("");
                } else {
                    listener.failed("");
                }
            }
        });
    }
}