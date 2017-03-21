package com.brioal.sourcecode.home.model;

import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.home.contract.HomeContract;
import com.brioal.sourcecode.interfaces.OnBlogLoadListener;
import com.socks.library.KLog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/02/24
 */

public class HomeModelImpl implements HomeContract.Model {

    @Override
    public void loadHot(final OnBlogLoadListener listener) {
        //加载热门的数据
        //随机选三条
        BmobQuery<BlogBean> query = new BmobQuery<>();
        query.include("mUserBean,mTypeBean");
        query.findObjects(new FindListener<BlogBean>() {
            @Override
            public void done(List<BlogBean> list, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null || list.size() < 3) {
                    listener.failed("加载数据错误");
                    return;
                }
                List<BlogBean> selectedList = new ArrayList<BlogBean>();
                Random random = new Random();
                for (int i = 0; i < 3; i++) {
                    int index = random.nextInt(list.size());
                    BlogBean blogBean = list.get(index);
                    if (!selectedList.contains(blogBean)) {
                        selectedList.add(blogBean);
                    } else {
                        i--;
                    }
                }
                listener.success(selectedList);
            }
        });
    }

    @Override
    public void loadList(int sortType, final OnBlogLoadListener listener) {
        //根据排序方式加载数据
        String sort = "-createdAt";//默认按照创建时间排序
        switch (sortType) {
            case 0:
                sort = "-createdAt";
                break;
            case 1:
                sort = "-mCollectCount";//按照收藏数量排序
                break;
            case 2:
                sort = "-mCommentCount";//按照评论数量排序
                break;
        }
        BmobQuery<BlogBean> query = new BmobQuery<>();
        query.order(sort);
        query.include("mUserBean,mTypeBean");
        query.findObjects(new FindListener<BlogBean>() {
            @Override
            public void done(List<BlogBean> list, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null && list.size() == 0) {
                    listener.failed("查找数据失败");
                    return;
                }
                listener.success(list);
            }
        });
    }

    @Override
    public void loadBlog(final String blogUrl, final OnBlogLoadListener listener) {
        if (listener == null) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect(blogUrl).get();
                    BlogBean blogBean = new BlogBean();
                    blogBean.setUrl(blogUrl);
                    blogBean.setTitle(doc.title());
                    KLog.e("Blog Title:"+doc.title());
                    List<BlogBean> blogList = new ArrayList<>();
                    blogList.add(blogBean);
                    listener.success(blogList);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.failed(e.getMessage());
                }
            }
        }.start();


    }
}