package com.brioal.sourcecode.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.ApiBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ApiDetailActivity extends BaseActivity {

    @BindView(R.id.api_detail_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.api_detail_tv_title)
    TextView mTvTitle;
    @BindView(R.id.api_detail_btn_share)
    ImageButton mBtnShare;
    @BindView(R.id.api_detail_webview)
    WebView mWebview;
    @BindView(R.id.api_result_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.api_result_layout)
    PtrFrameLayout mLayout;

    private ApiBean mApiBean;
    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_api_detail);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mApiBean = (ApiBean) getIntent().getSerializableExtra("ApiBean");
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
                mWebview.loadUrl(mApiBean.getUrl());
            }
        });
        mLayout.setOffsetToRefresh(100);
        mLayout.autoRefresh();
        //返回按钮
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //分享按钮
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApiBean == null) {
                    return;
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mApiBean.getUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "选择要分享的方式"));
            }
        });
        if (mApiBean == null) {
            showFailed("数据出错,请稍候再试");
            return;
        }
        //标题
        mTvTitle.setText(mApiBean.getName());
        //加载网页
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLayout.refreshComplete();
            }
        });
    }

    public static void enterApiDetail(Context context, ApiBean apiBean) {
        Intent intent = new Intent(context, ApiDetailActivity.class);
        intent.putExtra("ApiBean", apiBean);
        context.startActivity(intent);
    }
}
