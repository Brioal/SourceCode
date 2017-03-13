package com.brioal.sourcecode.libcollect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.libcollect.contract.LibCollectListContract;
import com.brioal.sourcecode.libcollect.presenter.LibCollectListPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LibCollectActivity extends BaseActivity implements LibCollectListContract.View {

    @BindView(R.id.collect_lib_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.collect_lib_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.collect_lib_refresh_layout)
    PtrFrameLayout mRefreshLayout;
    @BindView(R.id.collect_lib_btn_close)
    ImageButton mBtnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_collect);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }


    private boolean isRefreshing = false;
    private LibCollectListContract.Presenter mPresenter;

    private void initView() {
        //关闭事件
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        mPresenter = new LibCollectListPresenterImpl(this);
        mPresenter.start();
    }

    @Override
    public void showList(List<LibCollectBean> list) {
        mRefreshLayout.refreshComplete();
        LibCollectAdapter adapter = new LibCollectAdapter(mContext);
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

    public static void enterLibCollect(Context context) {
        Intent intent = new Intent(context, LibCollectActivity.class);
        context.startActivity(intent);
    }
}
