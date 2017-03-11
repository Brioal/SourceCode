package com.brioal.sourcecode.readhistory;

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
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.readhistory.contract.ReadHistoryContract;
import com.brioal.sourcecode.readhistory.presenter.ReadHistoryPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ReadHistoryActivity extends BaseActivity implements ReadHistoryContract.View {

    @BindView(R.id.read_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.read_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.read_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.read_layout)
    PtrFrameLayout mLayout;

    private ReadHistoryContract.Presenter mPresenter;
    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_read_history);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    private void initView() {
        //下拉刷新
        mLayout.setPtrHandler(new PtrHandler() {
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
        mLayout.setOffsetToRefresh(100);
        mLayout.autoRefresh();
        //关闭
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPresenter() {
        mPresenter = new ReadHistoryPresenterImpl(this);
        mPresenter.start();
    }

    @Override
    public void showList(List<ReadBean> list) {
        mLayout.refreshComplete();
        ReadHistoryAdapter adapter = new ReadHistoryAdapter(mContext);
        adapter.showList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showListFailed(String errorMsg) {
        mLayout.refreshComplete();
        showFailed(errorMsg);
    }

    @Override
    public UserBean getUserBean() {
        return BmobUser.getCurrentUser(UserBean.class);
    }


    public static void enterReadHistory(Context context) {
        Intent intent = new Intent(context, ReadHistoryActivity.class);
        context.startActivity(intent);
    }
}
