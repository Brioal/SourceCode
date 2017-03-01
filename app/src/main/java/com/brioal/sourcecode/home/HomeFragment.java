package com.brioal.sourcecode.home;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.addblog.BlogAddActivity;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.home.contract.HomeContract;
import com.brioal.sourcecode.home.presenter.HomePresenterImpl;
import com.brioal.sourcecode.interfaces.OnHotRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    private static HomeFragment sFragment;
    @BindView(R.id.home_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.home_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.home_btn_add)
    ImageButton mBtnAdd;
    @BindView(R.id.home_btn_sort)
    ImageButton mBtnSort;
    private int mSortType = 0;

    public static HomeFragment getInstance() {
        if (sFragment == null) {
            sFragment = new HomeFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private boolean isRefreshing = false;
    private HomeAdapter mAdapter;
    private HomeContract.Presenter mPresenter;
    private AlertDialog mUriEditDialog;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_home, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, mRootView);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new HomePresenterImpl(this);
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
        //添加
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
                if (userBean == null) {
                    showFailed("请先到个人中心登陆后再分享");
                    return;
                }
                showUrlEditDialog();
            }
        });
        //排序
        mBtnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFailed("排序");
            }
        });
        //下拉列表
        mAdapter = new HomeAdapter(mContext);
        mAdapter.setRefreshListener(new OnHotRefreshListener() {
            @Override
            public void refresh() {
                mAdapter.setIsHotShowing(true);
                mPresenter.refreshHot();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showRefreshing() {
        mLayout.autoRefresh(true, 2000);
    }

    @Override
    public void showRefreshDone() {
        mLayout.refreshComplete();
    }

    @Override
    public void showRefreshFailed(String msg) {
        showRefreshDone();
        showFailed(msg);
    }

    @Override
    public int getSortType() {
        return mSortType;
    }

    @Override
    public void showHot(List<BlogBean> list) {
        //显示热门文章
        mAdapter.showHot(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showList(List<BlogBean> list) {
        //显示所有文章
        mAdapter.showNormals(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        //显示正在加载博客数据
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在解析链接信息");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showLoadingDone() {
        //显示博客加载完成
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showLoadingFailed(String msg) {
        //显示博客加载失败
        showLoadingDone();
        showFailed(msg);
    }

    @Override
    public void showLoadedBlog(final BlogBean blogBean) {
        //加载博客信息成功,然后跳转编辑界面
        showLoadingDone();
        showSuccess("解析成功,正在跳转");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BlogAddActivity.enterBlogAddActivity(mContext, blogBean);
            }
        }, 1500);
    }

    //显示设置Url的dialog
    private void showUrlEditDialog() {
        mUriEditDialog = new AlertDialog.Builder(mContext).create();
        mUriEditDialog.show();
        Window window = mUriEditDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(R.layout.layout_url_edit);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        final EditText etUrl = (EditText) window.findViewById(R.id.layout_et_url);
        Button btnContinue = (Button) window.findViewById(R.id.layout_btn_continue);
        WindowManager windowManager = window.getWindowManager();

        Display display = windowManager.getDefaultDisplay();
        window.setLayout(display.getWidth() - 100, display.getWidth() / 2);
        //设置剪贴板的内筒
        etUrl.setText(getUrl());
        //设置按钮的功能
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etUrl.getText().toString();
                if (!content.contains("http")) {
                    showFailed("链接格式不正确,请核对后再试");
                    return;
                }
                mUriEditDialog.dismiss();
                mPresenter.getUrlInfo(content);
            }
        });
    }

    //检测剪贴板,如果是连接就返回,否则返回空
    public String getUrl() {
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if (data == null) {
            return "";
        }
        ClipData.Item item = data.getItemAt(0);
        String content = item.getText().toString();
        if (content.contains("http")) {
            showSuccess("已复制剪贴板的地址");
            return content;
        }
        return "";
    }
}
