package com.brioal.sourcecode.libsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.api.HistoryAdapter;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.lib.LibAdapter;
import com.brioal.sourcecode.libsearch.contract.LibSearchContract;
import com.brioal.sourcecode.libsearch.presenter.LibSearchPresenterImpl;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LibSearchActivity extends BaseActivity implements LibSearchContract.View {

    @BindView(R.id.lib_search_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.lib_search_et_key)
    EditText mEtKey;
    @BindView(R.id.lib_search_iv_img)
    ImageView mIvImg;
    @BindView(R.id.lib_search_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.lib_search_layout)
    PtrFrameLayout mLayout;

    private boolean isRefreshing = false;
    private String mKey = "";
    private LibSearchPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_search);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new LibSearchPresenterImpl(this);
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
                            mIvImg.startAnimation(animation);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mIvImg.startAnimation(animation);
                if (mKey.isEmpty()) {
                    mPresenter.start();
                } else {
                    mPresenter.search(mKey);
                }
            }
        });
        mLayout.setOffsetToRefresh(100);
        //返回
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //搜索事件
        mEtKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mKey = mEtKey.getText().toString().trim();
                    KLog.e(mKey);
                    if (mKey.isEmpty()) {
                        showFailed("关键字不能为空");
                    } else {
                        mPresenter.search(mKey);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void showHistory(List<String> list) {
        //显示历史记录
        LibHistoryAdapter adapter = new LibHistoryAdapter(mContext, mPresenter);
        adapter.showList(list);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void showSearchResult(List<LibBean> list) {
        //显示搜素记录
        LibAdapter adapter = new LibAdapter(mContext);
        adapter.showList(list);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        mLayout.autoRefresh();
    }

    @Override
    public void showLoadingDone() {
        mLayout.refreshComplete();
    }

    @Override
    public void showLoadingFailed(String errorMsg) {
        mLayout.refreshComplete();
        showFailed(errorMsg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
