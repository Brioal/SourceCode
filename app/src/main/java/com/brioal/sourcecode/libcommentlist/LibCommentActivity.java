package com.brioal.sourcecode.libcommentlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.LibCommentBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnLibCommentClickListener;
import com.brioal.sourcecode.libcommentlist.contract.LibCommentContract;
import com.brioal.sourcecode.libcommentlist.presenter.LibCommentPresenterImpl;
import com.brioal.sourcecode.util.CopyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LibCommentActivity extends BaseActivity implements LibCommentContract.View {

    @BindView(R.id.lib_comment_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.lib_comment_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.lib_comment_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.lib_comment_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.lib_comment_et_comment)
    EditText mEtComment;
    @BindView(R.id.lib_comment_btn_send)
    TextView mBtnSend;

    private LibCommentContract.Presenter mPresenter;
    private LibBean mLibBean;
    private boolean isRefreshing = false;
    private LibCommentBean mParent;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_comment);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    public static void enterLibComment(Context context, LibBean blogBean) {
        Intent intent = new Intent(context, LibCommentActivity.class);
        intent.putExtra("LibBean", blogBean);
        context.startActivity(intent);
    }

    private void initPresenter() {
        mPresenter = new LibCommentPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //关闭
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        //添加评论
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户检查
                UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
                if (userBean == null) {
                    showFailed("当前用户为空,请登陆后重试");
                    return;
                }
                //内容检查
                String content = mEtComment.getText().toString();
                if (content.isEmpty()) {
                    showFailed("评论内容不能为空");
                    return;
                }
                LibCommentBean bean = new LibCommentBean();
                bean.setLibBean(mLibBean).setUserBean(userBean).setContent(content);
                if (mParent != null) {
                    bean.setParentID(mParent.getObjectId());
                }
                mEtComment.setText("");
                mEtComment.setHint("输入回复");
                mParent = null;
                mPresenter.addComment(bean);
            }
        });
    }

    private void initData() {
        mLibBean = (LibBean) getIntent().getSerializableExtra("LibBean");
    }

    @Override
    public void showLoading() {
        mLayout.autoRefresh();
    }

    @Override
    public void showComment(List<LibCommentBean> list) {
        mLayout.refreshComplete();
        LibCommentAdapter adapter = new LibCommentAdapter(mContext);
        adapter.showList(list);
        adapter.setCommentListener(new OnLibCommentClickListener() {
            @Override
            public void click(LibCommentBean bean) {
                //单击评论或者回复
                mParent = bean;
                mEtComment.setHint("回复:" + mParent.getUserBean().getUsername() + ":");
            }

            @Override
            public void longClickListener(LibCommentBean bean) {
                //显示复制,回复,删除等操作
                showCommentOperator(bean);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showCommentFailed() {
        mLayout.refreshComplete();
        showFailed("该博客暂无评论,快来添加第一条评论吧");
    }

    @Override
    public void showAdding() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在添加评论,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showAddingDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mPresenter.start();
    }

    @Override
    public void showAddingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    @Override
    public void showDeleting() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在删除评论,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showDeletingDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showSuccess("删除成功");
        mPresenter.start();
    }

    @Override
    public void showDeletingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed("删除失败");
    }

    @Override
    public LibBean getLibBean() {
        return mLibBean;
    }

    //显示评论操作
    private void showCommentOperator(final LibCommentBean bean) {
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        String[] operators;
        if (userBean != null && userBean.getEmail().equals(bean.getUserBean().getEmail())) {
            operators = new String[]{
                    "回复",
                    "复制",
                    "删除",
            };
        } else {
            operators = new String[]{
                    "回复",
                    "复制",
            };
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择要进行的操作").setItems(operators, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //回复
                        mParent = bean;
                        mEtComment.setHint("回复:" + mParent.getUserBean().getUsername() + ":");
                        break;
                    case 1:
                        //复制
                        String content = bean.getContent();
                        CopyUtil.copy(mContext, content);
                        showSuccess("内容已复制到阶剪贴板");
                        break;
                    case 2:
                        //删除回复
                        mPresenter.deleteComment(bean);
                        break;
                }
            }
        }).setCancelable(true).create().show();
    }
}
