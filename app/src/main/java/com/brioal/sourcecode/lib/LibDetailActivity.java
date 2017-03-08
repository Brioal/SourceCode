package com.brioal.sourcecode.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.LibBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LibDetailActivity extends BaseActivity {
    @BindView(R.id.lib_detail_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.lib_detail_tv_title)
    TextView mTvTitle;
    @BindView(R.id.lib_detail_btn_share)
    ImageButton mBtnShare;
    @BindView(R.id.lib_result_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.lib_detail_webview)
    WebView mWebview;
    @BindView(R.id.lib_result_layout)
    PtrFrameLayout mLayout;
    private LibBean mLibBean;
    private boolean isRefreshing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_detail);
        ButterKnife.bind(this);
        initData();
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
                mWebview.loadUrl(mLibBean.getUrl());
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
        if (mLibBean == null) {
            return;
        }
        //标题显示
        mTvTitle.setText(mLibBean.getTitle());
        //连接分享
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLibBean == null) {
                    return;
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mLibBean.getUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "选择要分享的方式"));
            }
        });
        //网页加载
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLayout.refreshComplete();
            }
        });
        mWebview.loadUrl(mLibBean.getUrl());
    }

    private void initData() {
        mLibBean = (LibBean) getIntent().getSerializableExtra("LibBean");
    }

    public static void enterLibDetail(Context context, LibBean libBean) {
        Intent intent = new Intent(context, LibDetailActivity.class);
        intent.putExtra("LibBean", libBean);
        context.startActivity(intent);
    }
}
