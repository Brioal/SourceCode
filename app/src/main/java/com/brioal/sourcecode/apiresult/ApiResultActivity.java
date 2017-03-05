package com.brioal.sourcecode.apiresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.apiresult.contract.ApiResultContract;
import com.brioal.sourcecode.apiresult.presenter.ApiResultPresenterImpl;
import com.brioal.sourcecode.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ApiResultActivity extends BaseActivity implements ApiResultContract.View {

    @BindView(R.id.api_result_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.api_result_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.api_result_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.api_result_layout)
    PtrFrameLayout mLayout;

    private boolean isRefreshing = false;
    private ApiResultPresenterImpl mPresenter;
    private String mKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_api_result);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initData() {
        mKey = getIntent().getStringExtra("Key");
    }

    private void initPresenter() {
        mPresenter = new ApiResultPresenterImpl(this);
        mPresenter.start(mKey);
    }

    private void initView() {
        //标题栏
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mKey + " 的搜索结果");
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
                mPresenter.start(mKey);
            }
        });
        mLayout.setOffsetToRefresh(100);
    }

    @Override
    public void showLoading() {
        mLayout.autoRefresh();
    }

    @Override
    public void showLoadDone(List<IndexBean> list) {
        mLayout.refreshComplete();
        ApiResultAdapter adapter = new ApiResultAdapter(mContext);
        adapter.showList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void enterResult(Context context, String key) {
        Intent intent = new Intent(context, ApiResultActivity.class);
        intent.putExtra("Key", key);
        context.startActivity(intent);
    }
}
