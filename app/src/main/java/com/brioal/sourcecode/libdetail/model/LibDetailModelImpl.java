package com.brioal.sourcecode.libdetail.model;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.bean.LibCommentBean;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.libdetail.contract.LibDetailContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/03/08
 */

public class LibDetailModelImpl implements LibDetailContract.Model {

    @Override
    public void checkCollect(UserBean userBean, LibBean libBean, final OnNormalOperatorListener normalOperatorListener) {
        //判断是否收藏
        BmobQuery<LibCollectBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.addWhereEqualTo("mLibBean", libBean);
        query.findObjects(new FindListener<LibCollectBean>() {
            @Override
            public void done(List<LibCollectBean> list, BmobException e) {
                if (normalOperatorListener == null) {
                    return;
                }
                if (e != null) {
                    normalOperatorListener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    //没有收藏
                    normalOperatorListener.failed("");
                } else {
                    //已收藏
                    normalOperatorListener.success("");
                }
            }
        });
    }

    @Override
    public void loadCollectCount(final LibBean bean, final OnNormalOperatorListener listener) {
        //加载收藏的数量
        BmobQuery<LibCollectBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mLibBean", bean);
        query.count(LibCollectBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                }
                if (integer == null) {
                    listener.failed("");
                    return;
                }
                if (integer == 0) {
                    listener.failed("");
                    return;
                }
                listener.success(integer + "");
                //更新LibBean的收藏数量
                bean.setValue("mCollectCount", integer);
                bean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }

    @Override
    public void loadCommentCount(final LibBean bean, final OnNormalOperatorListener listener) {
        //加载评论的数量
        BmobQuery<LibCommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mLibBean", bean);
        query.count(LibCommentBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (integer == null) {
                    listener.failed("");
                    return;
                }
                if (integer == 0) {
                    listener.failed("");
                    return;
                }
                listener.success(integer + "");
                //更新LibBean的收藏数量
                bean.setValue("mCommentCount", integer);
                bean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }

    @Override
    public void addCollect(UserBean userBean, final LibBean blogBean, final OnNormalOperatorListener listener) {
        //添加收藏
        LibCollectBean bean = new LibCollectBean();
        bean.setUserBean(userBean).setLibBean(blogBean).setTime(System.currentTimeMillis());
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                //添加收藏
                blogBean.increment("mCollectCount");
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
    public void deleteCollect(UserBean userBean, final LibBean blogBean, final OnNormalOperatorListener listener) {
        //删除收藏
        BmobQuery<LibCollectBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserBean", userBean);
        query.addWhereEqualTo("mLibBean", blogBean);
        query.findObjects(new FindListener<LibCollectBean>() {
            @Override
            public void done(List<LibCollectBean> list, BmobException e) {
                if (e == null && list.size() == 1) {
                    //找到了
                    //进行删除
                    LibCollectBean bean = list.get(0);
                    bean.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            //添加收藏
                            blogBean.increment("mCollectCount", -1);
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
            }
        });

    }

    @Override
    public void addReadRecord(ReadBean readBean, final OnNormalOperatorListener listener) {
        readBean.save(new SaveListener<String>() {
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
}