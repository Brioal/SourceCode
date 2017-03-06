package com.brioal.sourcecode.blogdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogdetail.contract.BlogDetailContract;
import com.brioal.sourcecode.blogdetail.presenter.BlogDetailPresenterImpl;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BlogDetailActivity extends BaseActivity implements BlogDetailContract.View {

    @BindView(R.id.blog_detail_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.blog_detail_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.blog_detail_tv_name)
    TextView mTvName;
    @BindView(R.id.blog_detail_btn_share)
    ImageButton mBtnShare;
    @BindView(R.id.blog_detail_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.blog_detail_webview)
    WebView mWebview;
    @BindView(R.id.blog_detail_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.blog_detail_btn_collect)
    CheckBox mBtnCollect;
    @BindView(R.id.blog_detail_tv_collect)
    TextView mTvCollect;
    @BindView(R.id.blog_detail_btn_comment)
    ImageButton mBtnComment;
    @BindView(R.id.blog_detail_tv_comment)
    TextView mTvComment;

    private BlogBean mBlogBean;
    private BlogDetailPresenterImpl mPresenter;
    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initData() {
        mBlogBean = (BlogBean) getIntent().getSerializableExtra("BlogBean");
    }

    private void initPresenter() {
        mPresenter = new BlogDetailPresenterImpl(this);
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
                mWebview.loadUrl(mBlogBean.getUrl());
            }
        });
        mLayout.setOffsetToRefresh(100);
        mLayout.autoRefresh(true);
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
                if (mBlogBean == null) {
                    showFailed("数据出错,请稍候再试");
                    return;
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mBlogBean.getUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "选择要分享的方式"));
            }
        });
        //收藏按钮
        mBtnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BmobUser.getCurrentUser(UserBean.class) == null) {
                    showFailed("登陆之后才能进行收藏操作,请前往个人中心登陆后重试");
                    return;
                }
                mPresenter.collect(mBtnCollect.isChecked());
            }
        });
        //评论按钮
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/3/6 跳转评论
            }
        });
        if (mBlogBean == null) {
            return;
        }
        //头像
        Glide.with(mContext).load(mBlogBean.getUserBean().getHead().getFileUrl()).into(mIvHead);
        //用户名
        mTvName.setText(mBlogBean.getUserBean().getUsername());
        //加载网页
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


    }

    @Override
    public void showIsCollect(boolean isCollect) {
        //显示是否收藏
        mBtnCollect.setChecked(isCollect);
    }

    @Override
    public void showCollectCount(int count) {
        //显示收藏数量
        mTvCollect.setText(count + "");
    }

    @Override
    public void showCommentCount(int commentCount) {
        //显示评论数量
        mTvComment.setText(commentCount + "");
    }

    @Override
    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    public static void enterBlogDetail(Context context, BlogBean blogBean) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("BlogBean", blogBean);
        context.startActivity(intent);
    }
}
