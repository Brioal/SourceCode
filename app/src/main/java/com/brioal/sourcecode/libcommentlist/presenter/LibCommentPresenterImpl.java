package com.brioal.sourcecode.libcommentlist.presenter;

import android.os.Handler;

import com.brioal.sourcecode.bean.LibCommentBean;
import com.brioal.sourcecode.interfaces.OnLibCommentListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.libcommentlist.contract.LibCommentContract;
import com.brioal.sourcecode.libcommentlist.model.LibCommentModelImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/03/09
 */

public class LibCommentPresenterImpl implements LibCommentContract.Presenter {
    private LibCommentContract.View mView;
    private LibCommentContract.Model mModel;
    private Handler mHandler = new Handler();

    public LibCommentPresenterImpl(LibCommentContract.View view) {
        mView = view;
        mModel = new LibCommentModelImpl();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void refresh() {
        //加载评论
        mModel.loadComment(mView.getLibBean(), new OnLibCommentListener() {
            @Override
            public void success(List<LibCommentBean> list) {
                //开始整理
                final List<LibCommentBean> result = new ArrayList<>();
                List<LibCommentBean> hasParent = new ArrayList<>();
                //分割
                for (int i = 0; i < list.size(); i++) {
                    LibCommentBean bean = list.get(i);
                    if (bean.getParentID() == null) {
                        //没有父类
                        result.add(bean);
                    } else {
                        //存在父类
                        hasParent.add(bean);
                    }
                }
                //合并
                for (int i = hasParent.size() - 1; i >= 0; i--) {
                    String id = hasParent.get(i).getParentID();
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
    public void addComment(LibCommentBean bean) {
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
    public void deleteComment(LibCommentBean bean) {
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