package com.brioal.sourcecode.libsharelist;

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
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.libsharelist.contract.LibShareListContract;
import com.brioal.sourcecode.libsharelist.presenter.LibShareListPresenterImpl;

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
 * Created by Brioal on 2017/3/10.
 */

public class LibShareListFragment extends BaseFragment implements LibShareListContract.View {
    private static LibShareListFragment sFragment;
    @BindView(R.id.share_lib_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.share_lib_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.share_lib_refresh_layout)
    PtrFrameLayout mRefreshLayout;

    public static LibShareListFragment getInstance() {
        if (sFragment == null) {
            sFragment = new LibShareListFragment();
        }
        return sFragment;
    }

    private LibShareListContract.Presenter mPresenter;
    private boolean isRefreshing = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_share_lib, container, false);
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
        mPresenter = new LibShareListPresenterImpl(this);
        mPresenter.start();
    }

    @Override
    public void showList(List<LibBean> list) {
        mRefreshLayout.refreshComplete();
        LibShareAdapter adapter = new LibShareAdapter(mContext);
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
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        return userBean;
    }
}
