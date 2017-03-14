package com.brioal.sourcecode.libsharelist;

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

public class LibShareListActivity extends BaseActivity implements LibShareListContract.View {


    @BindView(R.id.share_lib_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.share_lib_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.share_lib_refresh_layout)
    PtrFrameLayout mRefreshLayout;
    @BindView(R.id.share_lib_btn_close)
    ImageButton mBtnClose;

    private LibShareListContract.Presenter mPresenter;
    private boolean isRefreshing = false;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_share_list);
        initData();
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    private void initData() {
        mUserBean = (UserBean) getIntent().getSerializableExtra("UserBean");
    }

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

    public static void enterLibShareList(Context context, UserBean userBean) {
        Intent intent = new Intent(context, LibShareListActivity.class);
        intent.putExtra("UserBean", userBean);
        context.startActivity(intent);
    }
}
