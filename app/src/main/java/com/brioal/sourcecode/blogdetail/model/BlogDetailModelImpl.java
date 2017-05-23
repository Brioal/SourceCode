package com.brioal.sourcecode.blogdetail.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.bean.BlogCommentBean;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogdetail.contract.BlogDetailContract;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/03/06
 */

public class BlogDetailModelImpl implements BlogDetailContract.Model {

    @Override
    public void checkCollect(UserBean userBean, BlogBean blogBean, final OnNormalOperatorListener normalOperatorListener) {
        //检查是否已经收藏
        if (userBean == null) {
            return;
        }
        if (blogBean == null) {
            return;
        }
        BmobQuery<BlogCollectionBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mBlogBean", blogBean);
        query.addWhereEqualTo("mUserBean", userBean);
        query.findObjects(new FindListener<BlogCollectionBean>() {
            @Override
            public void done(List<BlogCollectionBean> list, BmobException e) {
                if (normalOperatorListener == null) {
                    return;
                }
                if (e != null) {
                    normalOperatorListener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    normalOperatorListener.failed("找不到数据");
                    return;
                }
                if (list.size() == 1) {
                    normalOperatorListener.success("");
                }
            }
        });
    }

    @Override
    public void loadCollectCount(final BlogBean bean, final OnNormalOperatorListener listener) {
        //加载收藏数量
        if (bean == null) {
            return;
        }
        BmobQuery<BlogCollectionBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mBlogBean", bean);
        query.count(BlogCollectionBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (integer == 0) {
                    listener.failed("找不到数据");
                    return;
                }
                listener.success(integer + "");
                //更新BlogBean的收藏数量
                bean.setValue("mCollectCount", integer);
                bean.update(bean.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });

    }

    @Override
    public void loadCommentCount(final BlogBean bean, final OnNormalOperatorListener listener) {
        //加载评论的数量
        if (bean == null) {
            return;
        }
        BmobQuery<BlogCommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mBlogBean", bean);
        query.count(BlogCommentBean.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (integer == 0) {
                    listener.failed("找不到数据");
                    return;
                }
                listener.success(integer + "");
                //更新BlogBean的收藏数量
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
    public void addCollect(UserBean userBean, final BlogBean blogBean, final OnNormalOperatorListener listener) {
        //添加收藏
        BlogCollectionBean bean = new BlogCollectionBean();
        bean.setUserBean(userBean).setBlogBean(blogBean);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //成功
                    //BlogBean 自增
                    blogBean.increment("mCollectCount");
                    blogBean.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                listener.success("");
                            } else {
                                listener.failed(e.getMessage());
                            }
                        }
                    });
                } else {
                    //失败
                    listener.failed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteCollect(UserBean userBean, final BlogBean blogBean, final OnNormalOperatorListener listener) {
        //删除收藏
        //先查询是否存在
        BmobQuery<BlogCollectionBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mBlogBean", blogBean);
        query.addWhereEqualTo("mUserBean", userBean);
        query.findObjects(new FindListener<BlogCollectionBean>() {
            @Override
            public void done(List<BlogCollectionBean> list, BmobException e) {
                if (e == null && list.size() == 1) {
                    //成功查询到了
                    BlogCollectionBean bean = list.get(0);
                    bean.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //成功删除
                                //字段递减
                                blogBean.increment("mCollectCount", -1);
                                blogBean.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            //修改成功
                                            listener.success("");
                                        } else {
                                            listener.failed("");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addRead(ReadBean readBean, final OnNormalOperatorListener listener) {
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