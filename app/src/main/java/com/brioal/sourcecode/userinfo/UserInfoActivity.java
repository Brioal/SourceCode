package com.brioal.sourcecode.userinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogsharelist.BlogShareListActivity;
import com.brioal.sourcecode.libsharelist.LibShareListActivity;
import com.brioal.sourcecode.userinfo.contract.UserInfoContract;
import com.brioal.sourcecode.userinfo.presenter.UserInfoPresenterImpl;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity implements UserInfoContract.View {

    @BindView(R.id.user_edit_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.user_info_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.user_info_tv_name)
    TextView mTvName;
    @BindView(R.id.user_info_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.user_info_tv_blog_count)
    TextView mTvBlogCount;
    @BindView(R.id.user_info_tv_blog_layout)
    LinearLayout mBlogLayout;
    @BindView(R.id.user_info_tv_lib_count)
    TextView mTvLibCount;
    @BindView(R.id.user_info_tv_lib_layout)
    LinearLayout mLibLayout;
    @BindView(R.id.user_info_tv_blog)
    EditText mTvBlog;
    @BindView(R.id.user_info_tv_pro)
    EditText mTvPro;
    @BindView(R.id.user_info_tv_company)
    EditText mTvCompany;
    @BindView(R.id.user_edit_btn_close)
    ImageButton mBtnClose;
    private UserBean mUserBean;

    private UserInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_info);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new UserInfoPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //关闭事件
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mUserBean == null) {
            showFailed("数据出错，请稍后重试");
            return;
        }
        //加载用户头像
        Glide.with(mContext).load(mUserBean.getHead().getFileUrl()).into(mIvHead);
        //加载用户信息
        mTvName.setText(mUserBean.getUsername());
        //加载用户描述
        mTvName.setText(mUserBean.getDesc());
        //加载用户博客
        mTvBlog.setText(mUserBean.getBlogUrl());
        //加载用户职业
        mTvPro.setText(mUserBean.getPro());
        //加载用户公司
        mTvCompany.setText(mUserBean.getCompany());
        //设置博客链接
        mBlogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlogShareListActivity.enterBlogShare(mContext, mUserBean);
            }
        });
        //设置开源库链接
        mLibLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibShareListActivity.enterLibShareList(mContext, mUserBean);
            }
        });
    }

    private void initData() {
        mUserBean = (UserBean) getIntent().getSerializableExtra("UserBean");
    }

    @Override
    public void showBlogCount(int count) {
        //显示博客计数
        mTvBlogCount.setText(count + "");
    }

    @Override
    public void showLibCount(int count) {
        //限时开源库计数
        mTvLibCount.setText(count + "");
    }

    @Override
    public void showFailed(String errorMsg) {
        showFailed(errorMsg);
    }

    @Override
    public UserBean getUserBean() {
        return mUserBean;
    }

    public static void enterUserInfo(Context context, UserBean userBean) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("UserBean", userBean);
        context.startActivity(intent);
    }
}
