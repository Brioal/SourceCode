package com.brioal.sourcecode.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.login.LoginActivity;
import com.brioal.sourcecode.mine.contract.MineContract;
import com.brioal.sourcecode.mine.presenter.MinePresenterImpl;
import com.brioal.sourcecode.useredit.UserEditActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

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
    @BindView(R.id.mine_tv_share)
    TextView mTvShare;
    @BindView(R.id.mine_tv_collect)
    TextView mTvCollect;
    @BindView(R.id.mine_tv_read)
    TextView mTvRead;
    @BindView(R.id.mine_tv_atten)
    TextView mTvAtten;
    @BindView(R.id.mine_tv_fans)
    TextView mTvFans;
    @BindView(R.id.mine_tv_setting)
    TextView mTvSetting;

    public static MineFragment getInstance() {
        if (sFragment == null) {
            sFragment = new MineFragment();
        }
        return sFragment;
    }

    private UserBean mUserBean;
    private MineContract.Presenter mPresenter;

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
    }

    private void initPresenter() {
        mPresenter = new MinePresenterImpl(this);
        mPresenter.start();
    }

    private void initUser() {
        mUserBean = BmobUser.getCurrentUser(UserBean.class);
        if (mUserBean == null) {
            //未登录
            mTvName.setText("点击头像登陆");
            mTvDesc.setText("点击头像登陆");
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(mIvHead);
            mTvShare.setText("0");
            mTvCollect.setText("0");
            mTvRead.setText("0");
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFailed("当前未登录,请点击头像登陆");
                }
            };
            mTvAtten.setOnClickListener(clickListener);
            mTvFans.setOnClickListener(clickListener);
            mTvShare.setOnClickListener(clickListener);
            mTvCollect.setOnClickListener(clickListener);
            mTvRead.setOnClickListener(clickListener);
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
            mTvShare.setText("0");
            mTvCollect.setText("0");
            mTvRead.setText("0");
            //个人信息设置界面
            mIvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserEditActivity.class);
                    intent.putExtra("UserBean", mUserBean);
                    startActivityForResult(intent, 0);
                }
            });
            //分享界面
            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/2/28 分享界面
                }
            });
            //收藏界面
            mTvCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/2/28 收藏界面
                }
            });
            //阅读界面
            mTvRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/2/28 阅读界面
                }
            });
            //关注界面
            mTvAtten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/2/28 关注界面
                }
            });
            //粉丝界面
            mTvFans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/2/28 粉丝界面
                }
            });
        }
        mTvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/28 设置界面
            }
        });
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
        }
    }

    @Override
    public void showShareCount(int count) {
        mTvShare.setText(count + "");
    }

    @Override
    public void showCollectCount(int count) {
        mTvCollect.setText(count + "");
    }

    @Override
    public void showReadCount(int count) {
        mTvRead.setText(count + "");
    }

    @Override
    public UserBean getUserBean() {
        return mUserBean;
    }
}
