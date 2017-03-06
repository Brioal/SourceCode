package com.brioal.sourcecode.lib;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.addlib.AddLibActivity;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.lib.contract.LibContract;
import com.brioal.sourcecode.lib.presenter.LibPresenterImpl;
import com.brioal.sourcecode.libsearch.LibSearchActivity;

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
 * Created by Brioal on 2017/2/24.
 */

public class LibFragment extends BaseFragment implements LibContract.View {
    private static LibFragment sFragment;
    @BindView(R.id.lib_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.lib_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.lib_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.lib_btn_add)
    ImageButton mBtnAdd;
    @BindView(R.id.lib_btn_sort)
    ImageButton mBtnSort;

    public static LibFragment getInstance() {
        if (sFragment == null) {
            sFragment = new LibFragment();
        }
        return sFragment;
    }

    private LibPresenterImpl mPresenter;
    private View mRootView;
    private boolean isRefreshing = false;
    private LibAdapter mLibAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, mRootView);
        initPresenter();
        initView();
    }

    private void initPresenter() {
        mPresenter = new LibPresenterImpl(this);
        mPresenter.start();
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
        mLayout.autoRefresh(true);
        //添加
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
                if (userBean == null) {
                    showFailed("登陆之后才能分享开源库,请前往个人中心登陆后重试");
                    return;
                }
                Intent intent = new Intent(mContext, AddLibActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //搜索
        mBtnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LibSearchActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_lib, container, false);
        return mRootView;
    }

    @Override
    public void showRefresh() {
        mLayout.setOffsetToRefresh(100);
    }

    @Override
    public void showRefreshDone() {
        mLayout.refreshComplete();
    }

    @Override
    public void showLib(List<LibBean> list) {
        mLibAdapter = new LibAdapter(mContext);
        mLibAdapter.showList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mLibAdapter);
    }

    @Override
    public void showRefreshFailed(String errorMsg) {
        //刷新失败
        mLayout.refreshComplete();
        showFailed(errorMsg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            mPresenter.refresh();
        }
    }
}
