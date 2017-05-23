package com.brioal.sourcecode.addblog.model;

import com.brioal.sourcecode.addblog.contract.BlogAddContract;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogTypeBean;
import com.brioal.sourcecode.interfaces.OnBlogTypeLoadListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Brioal on 2017/03/01
 */

public class BlogAddModelImpl implements BlogAddContract.Model {

    @Override
    public void loadBlogType(final OnBlogTypeLoadListener loadListener) {
        //加载博客类型
        BmobQuery<BlogTypeBean> query = new BmobQuery<>();
        query.findObjects(new FindListener<BlogTypeBean>() {
            @Override
            public void done(List<BlogTypeBean> list, BmobException e) {
                if (loadListener == null) {
                    return;
                }
                if (e != null) {
                    loadListener.failed(e.getMessage());
                    return;
                }
                loadListener.success(list);
            }
        });
    }

    @Override
    public void addBlog(String coverUrl, final BlogBean blogBean, final OnNormalOperatorListener normalOperatorListener) {
        if (coverUrl != null && !coverUrl.isEmpty()) {
            //先上传图片后保存博客
            try {
                final BmobFile file = new BmobFile(new File(coverUrl));
                file.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            KLog.e("图片上传成功");
                            //添加博客
                            blogBean.setImg(file);
                            blogBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (normalOperatorListener == null) {
                                        return;
                                    }
                                    if (e != null) {
                                        normalOperatorListener.failed(e.getMessage());
                                        return;
                                    }
                                    normalOperatorListener.success("");
                                }
                            });
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            blogBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (normalOperatorListener == null) {
                        return;
                    }
                    if (e != null) {
                        normalOperatorListener.failed(e.getMessage());
                        return;
                    }
                    normalOperatorListener.success("");
                }
            });
        }

    }
}