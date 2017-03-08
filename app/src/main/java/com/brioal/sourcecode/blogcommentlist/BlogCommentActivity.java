package com.brioal.sourcecode.blogcommentlist;

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
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogCommentBean;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.blogcommentlist.contract.CommentListContract;
import com.brioal.sourcecode.blogcommentlist.presenter.CommentListPresenterImpl;
import com.brioal.sourcecode.interfaces.OnCommentListener;
import com.brioal.sourcecode.util.CopyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BlogCommentActivity extends BaseActivity implements CommentListContract.View {

    @BindView(R.id.comment_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.comment_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.comment_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.comment_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.comment_et_comment)
    EditText mEtComment;
    @BindView(R.id.comment_btn_send)
    TextView mBtnSend;

    private CommentListContract.Presenter mPresenter;
    private boolean isRefreshing = false;
    private BlogBean mBlogBean;
    private BlogCommentBean mParent;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog_comment);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    public static void enterBlogComment(Context context, BlogBean blogBean) {
        Intent intent = new Intent(context, BlogCommentActivity.class);
        intent.putExtra("BlogBean", blogBean);
        context.startActivity(intent);
    }

    private void initData() {
        mBlogBean = (BlogBean) getIntent().getSerializableExtra("BlogBean");
    }

    private void initPresenter() {
        mPresenter = new CommentListPresenterImpl(this);
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
                BlogCommentBean bean = new BlogCommentBean();
                bean.setBlogBean(mBlogBean).setUserBean(userBean).setContent(content);
                if (mParent != null) {
                    bean.setParentBean(mParent.getObjectId());
                }
                mEtComment.setText("");
                mEtComment.setHint("输入回复");
                mParent = null;
                mPresenter.addComment(bean);
            }
        });

    }

    @Override
    public void showLoading() {
        mLayout.autoRefresh();
    }

    @Override
    public void showComment(List<BlogCommentBean> list) {
        mLayout.refreshComplete();
        CommentAdapter adapter = new CommentAdapter(mContext);
        adapter.showList(list);
        adapter.setCommentListener(new OnCommentListener() {
            @Override
            public void click(BlogCommentBean bean) {
                //单击评论或者回复
                mParent = bean;
                mEtComment.setHint("回复:" + mParent.getUserBean().getUsername() + ":");
            }

            @Override
            public void longClickListener(BlogCommentBean bean) {
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
    public BlogBean getBlogBean() {
        return mBlogBean;
    }

    //显示评论操作
    private void showCommentOperator(final BlogCommentBean bean) {
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
