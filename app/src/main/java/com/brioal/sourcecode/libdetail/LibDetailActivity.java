package com.brioal.sourcecode.libdetail;

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
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.libcommentlist.LibCommentActivity;
import com.brioal.sourcecode.libdetail.contract.LibDetailContract;
import com.brioal.sourcecode.libdetail.presenter.LibDetailPresenterImpl;
import com.brioal.sourcecode.userinfo.UserInfoActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LibDetailActivity extends BaseActivity implements LibDetailContract.View {
    @BindView(R.id.lib_detail_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.lib_detail_webview)
    WebView mWebview;
    @BindView(R.id.lib_detail_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.blog_detail_tv_name)
    TextView mTvName;
    @BindView(R.id.blog_detail_btn_share)
    ImageButton mBtnShare;
    @BindView(R.id.lib_detail_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.blog_detail_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.lib_detail_btn_collect)
    CheckBox mBtnCollect;
    @BindView(R.id.lib_detail_tv_collect)
    TextView mTvCollect;
    @BindView(R.id.lib_detail_btn_comment)
    ImageButton mBtnComment;
    @BindView(R.id.lib_detail_tv_comment)
    TextView mTvComment;
    private LibBean mLibBean;
    private boolean isRefreshing = false;

    private LibDetailContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
        initRecord();
    }

    private void initRecord() {
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean == null) {
            return;
        }
        if (mLibBean == null) {
            return;
        }
        ReadBean bean = new ReadBean();
        bean.setUserBean(userBean).setLibBean(mLibBean);
        mPresenter.addReadRecord(bean);
    }

    private void initPresenter() {
        mPresenter = new LibDetailPresenterImpl(this);
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
                mWebview.loadUrl(mLibBean.getUrl());
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
        if (mLibBean == null) {
            return;
        }
        if (mLibBean.getUserBean() != null&&mLibBean.getUserBean().getHead()!=null) {
            //头像显示
            Glide.with(mContext).load(mLibBean.getUserBean().getHead().getFileUrl()).into(mIvHead);
            //用户名显示
            mTvName.setText(mLibBean.getUserBean().getUsername());
        }
        //点击收藏
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
        //跳转用户信息
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.enterUserInfo(mContext, mLibBean.getUserBean());
            }
        });
        //跳转评论列表
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibCommentActivity.enterLibComment(mContext, mLibBean);
            }
        });
        mTvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibCommentActivity.enterLibComment(mContext, mLibBean);
            }
        });
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
    }

    private void initData() {
        mLibBean = (LibBean) getIntent().getSerializableExtra("LibBean");
    }

    public static void enterLibDetail(Context context, LibBean libBean) {
        Intent intent = new Intent(context, LibDetailActivity.class);
        intent.putExtra("LibBean", libBean);
        context.startActivity(intent);
    }

    @Override
    public void showIsCollect(boolean isCollect) {
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
    public LibBean getLibBean() {
        return mLibBean;
    }
}
