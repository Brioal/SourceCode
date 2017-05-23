package com.brioal.sourcecode.blogcommentlist.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.BlogCommentBean;
import com.brioal.sourcecode.blogcommentlist.contract.CommentListContract;
import com.brioal.sourcecode.blogcommentlist.model.CommentListModelImpl;
import com.brioal.sourcecode.interfaces.OnLoadCommentListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/03/07
 */

public class CommentListPresenterImpl implements CommentListContract.Presenter {
    private CommentListContract.View mView;
    private CommentListContract.Model mModel;
    private Handler mHandler = new Handler();

    public CommentListPresenterImpl(CommentListContract.View view) {
        mView = view;
        mModel = new CommentListModelImpl();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadComment(mView.getBlogBean(), new OnLoadCommentListener() {
            @Override
            public void success(List<BlogCommentBean> list) {
                //开始整理
                final List<BlogCommentBean> result = new ArrayList<>();
                List<BlogCommentBean> hasParent = new ArrayList<>();
                //分割
                for (int i = 0; i < list.size(); i++) {
                    BlogCommentBean bean = list.get(i);
                    if (bean.getParentBean() == null) {
                        //没有父类
                        result.add(bean);
                    } else {
                        //存在父类
                        hasParent.add(bean);
                    }
                }
                //合并
                for (int i = hasParent.size()-1; i >=0; i--) {
                    String id = hasParent.get(i).getParentBean();
                    int parentIndex = -1;
                    for (int j = 0; j < result.size(); j++) {
                        if (id.equals(result.get(j).getObjectId())) {
                            parentIndex = j;
                            break;
                        }
                    }
                    if (parentIndex != -1) {
                        result.add(parentIndex + 1, hasParent.get(i));
                    }

                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showComment(result);
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommentFailed();
                    }
                });
            }
        });
    }

    @Override
    public void addComment(BlogCommentBean bean) {
        //添加评论
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showAdding();
            }
        });
        mModel.addComment(bean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAddingDone();
                    }
                });
                start();
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAddingFailed(errorMsg);
                    }
                });
            }
        });
    }

    @Override
    public void deleteComment(BlogCommentBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showDeleting();
            }
        });
        mModel.deleteComment(bean, new OnNormalOperatorListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showDeletingDone();
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showDeletingFailed(errorMsg);
                    }
                });
            }
        });
    }
}