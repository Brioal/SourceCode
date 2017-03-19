package com.brioal.sourcecode.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogcollectlist.BlogCollectActivity;
import com.brioal.sourcecode.blogsharelist.BlogShareListActivity;
import com.brioal.sourcecode.libcollect.LibCollectActivity;
import com.brioal.sourcecode.libsharelist.LibShareListActivity;
import com.brioal.sourcecode.login.LoginActivity;
import com.brioal.sourcecode.mine.contract.MineContract;
import com.brioal.sourcecode.mine.presenter.MinePresenterImpl;
import com.brioal.sourcecode.readhistory.ReadHistoryActivity;
import com.brioal.sourcecode.useredit.UserEditActivity;
import com.bumptech.glide.Glide;

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

public class MineFragment extends BaseFragment implements MineContract.View {
    private static MineFragment sFragment;
    @BindView(R.id.mine_tv_name)
    TextView mTvName;
    @BindView(R.id.mine_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.mine_iv_head)
    CircleImageView mIvHead;
//    @BindView(R.id.mine_tv_setting)
//    TextView mTvSetting;
    @BindView(R.id.mine_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.mine_layout)
    PtrFrameLayout mRefreshLayout;
    @BindView(R.id.mine_layout_share_blog)
    TextView mShareBlog;
    @BindView(R.id.mine_layout_share_blog_count)
    TextView mShareBlogCount;
    @BindView(R.id.mine_layout_share_lib)
    TextView mShareLib;
    @BindView(R.id.mine_layout_share_lib_count)
    TextView mShareLibCount;
    @BindView(R.id.mine_layout_collect_blog)
    TextView mCollectBlog;
    @BindView(R.id.mine_layout_collect_blog_count)
    TextView mCollectBlogCount;
    @BindView(R.id.mine_layout_collect_lib)
    TextView mCollectLib;
    @BindView(R.id.mine_layout_collect_lib_count)
    TextView mCollectLibCount;
    @BindView(R.id.mine_layout_read)
    TextView mRead;
    @BindView(R.id.mine_layout_read_count)
    TextView mReadCount;

    public static MineFragment getInstance() {
        if (sFragment == null) {
            sFragment = new MineFragment();
        }
        return sFragment;
    }

    private UserBean mUserBean;
    private MineContract.Presenter mPresenter;
    private boolean isRefreshing = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUser();
        initPresenter();
        initView();
    }

    private void initView() {
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
                mPresenter.start();
            }
        });
        mRefreshLayout.setOffsetToRefresh(100);
        mRefreshLayout.autoRefresh();
    }

    private void initPresenter() {
        mPresenter = new MinePresenterImpl(this);
    }

    private void initUser() {
        mUserBean = BmobUser.getCurrentUser(UserBean.class);
        if (mUserBean == null) {
            //未登录
            mTvName.setText("点击头像登陆");
            mTvDesc.setText("点击头像登陆");
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(mIvHead);
            mShareBlogCount.setText("0");
            mShareLibCount.setText("0");
            mCollectLibCount.setText("0");
            mCollectLibCount.setText("0");
            mReadCount.setText("0");
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFailed("当前未登录,请点击头像登陆");
                }
            };
            mShareBlog.setOnClickListener(clickListener);
            mShareBlogCount.setOnClickListener(clickListener);
            mShareLib.setOnClickListener(clickListener);
            mShareLibCount.setOnClickListener(clickListener);
            mCollectBlog.setOnClickListener(clickListener);
            mCollectBlogCount.setOnClickListener(clickListener);
            mCollectLib.setOnClickListener(clickListener);
            mCollectLibCount.setOnClickListener(clickListener);
            mRead.setOnClickListener(clickListener);
            mReadCount.setOnClickListener(clickListener);
            //点击登陆
            mIvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLogin();
                }
            });
        } else {
            //已登陆
            mTvName.setText(mUserBean.getUsername() + "");
            mTvDesc.setText(mUserBean.getDesc());
            if (mUserBean.getHead() != null) {
                Glide.with(mContext).load(mUserBean.getHead().getFileUrl()).into(mIvHead);
            }
            mShareBlogCount.setText("0");
            mShareLibCount.setText("0");
            mCollectLibCount.setText("0");
            mCollectLibCount.setText("0");
            mReadCount.setText("0");
            //个人信息设置界面
            mIvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserEditActivity.class);
                    intent.putExtra("UserBean", mUserBean);
                    startActivityForResult(intent, 0);
                }
            });
            //博客分享界面
            View.OnClickListener shareClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserBean == null) {
                        showFailed("登陆之后才能查看分享列表,请登陆后再试");
                        return;
                    }
                    BlogShareListActivity.enterBlogShare(mContext, mUserBean);
                }
            };
            mShareBlog.setOnClickListener(shareClickListener);
            mShareBlogCount.setOnClickListener(shareClickListener);
            //开源库分享界面
            View.OnClickListener libShareClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserBean == null) {
                        showFailed("登陆之后才能查看分享列表,请登陆后再试");
                        return;
                    }
                    LibShareListActivity.enterLibShareList(mContext, mUserBean);
                }
            };
            mShareLib.setOnClickListener(libShareClickListener);
            mShareLibCount.setOnClickListener(libShareClickListener);
            //博客收藏界面
            View.OnClickListener blogCollectListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserBean == null) {
                        showFailed("登陆之后才能查看分享列表,请登陆后再试");
                        return;
                    }
                    BlogCollectActivity.enterBlogCollect(mContext);
                }
            };
            mCollectBlog.setOnClickListener(blogCollectListener);
            mCollectBlogCount.setOnClickListener(blogCollectListener);
            //开源库收藏界面
            View.OnClickListener libCollectListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserBean == null) {
                        showFailed("登陆之后才能查看分享列表,请登陆后再试");
                        return;
                    }
                    LibCollectActivity.enterLibCollect(mContext, mUserBean);
                }
            };
            mCollectLib.setOnClickListener(libCollectListener);
            mCollectLibCount.setOnClickListener(libCollectListener);
            //阅读记录界面
            View.OnClickListener readClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserBean == null) {
                        showFailed("登陆之后才能查看分享列表,请登陆后再试");
                        return;
                    }
                    ReadHistoryActivity.enterReadHistory(mContext);
                }
            };
            mRead.setOnClickListener(readClickListener);
            mReadCount.setOnClickListener(readClickListener);
        }
//        mTvSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO: 2017/2/28 设置界面
//            }
//        });
    }

    private void startLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            initUser();
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showBlogShareCount(int count) {
        mShareBlogCount.setText(count + "");
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showLibShareCount(int count) {
        mShareLibCount.setText(count + "");
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showBlogCollectCount(int count) {
        mCollectBlogCount.setText(count + "");
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showLibCollectCount(int count) {
        mCollectLibCount.setText(count + "");
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showReadCount(int count) {
        mReadCount.setText(count + "");
        mRefreshLayout.refreshComplete();
    }

    @Override
    public UserBean getUserBean() {
        return mUserBean;
    }
}
