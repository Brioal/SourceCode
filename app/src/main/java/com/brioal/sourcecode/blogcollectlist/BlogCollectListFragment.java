package com.brioal.sourcecode.blogcollectlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogcollectlist.contract.BlogCollectContract;
import com.brioal.sourcecode.blogcollectlist.presenter.BlogCollectPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class BlogCollectListFragment extends BaseFragment implements BlogCollectContract.View {
    private static BlogCollectListFragment sFragment;
    @BindView(R.id.collect_blog_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.collect_blog_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.collect_blog_refresh_layout)
    PtrFrameLayout mRefreshLayout;

    public static BlogCollectListFragment getInstance() {
        if (sFragment == null) {
            sFragment = new BlogCollectListFragment();
        }
        return sFragment;
    }

    private boolean isRefreshing = false;
    private BlogCollectContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_collect_blog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initView();
    }

    private void initView() {
        //下拉刷新
        mRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefreshing = true;
                //刷新显示
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotating);
                animation.setDuration(900);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (isRefreshing) {
                            mIvLoading.startAnimation(animation);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mIvLoading.startAnimation(animation);
                mPresenter.refresh();
            }
        });
        mRefreshLayout.setOffsetToRefresh(100);
        mRefreshLayout.autoRefresh();
    }

    private void initPresenter() {
        mPresenter = new BlogCollectPresenterImpl(this);
        mPresenter.start();
    }

    @Override
    public void showList(List<BlogCollectionBean> list) {
        mRefreshLayout.refreshComplete();
        BlogCollectListAdapter adapter = new BlogCollectListAdapter(mContext);
        adapter.showList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showListFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showFailed(errorMsg);
    }

    @Override
    public UserBean getUserBean() {
        return BmobUser.getCurrentUser(UserBean.class);
    }
}
