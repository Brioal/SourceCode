package com.brioal.sourcecode.addblog;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.brioal.labelview.LabelView;
import com.brioal.labelview.entity.LabelEntity;
import com.brioal.labelview.interfaces.OnLabelSelectedListener;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.addblog.contract.BlogAddContract;
import com.brioal.sourcecode.addblog.presenter.BlogAddPresenterImpl;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.bean.BlogTypeBean;
import com.brioal.sourcecode.bean.UserBean;
import com.socks.library.KLog;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class BlogAddActivity extends BaseActivity implements BlogAddContract.View {
    @BindView(R.id.add_blog_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.add_blog_label)
    LabelView mLabel;
    @BindView(R.id.add_blog_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.add_blog_btn_login)
    Button mBtnLogin;
    @BindView(R.id.add_blog_et_title)
    EditText mEtTitle;
    private BlogBean mBlogBean;
    private ProgressDialog mProgressDialog;
    private BlogTypeBean mTypeBean;
    private BlogAddPresenterImpl mPresenter;
    private String mCoverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog_add);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new BlogAddPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //返回按钮
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //默认的Label
        List<LabelEntity> list = new ArrayList<>();
        list.add(new LabelEntity("...", "..."));
        mLabel.setColorBGNormal(getResources().getColor(R.color.colorGreen));
        mLabel.setColorBGSelect(Color.WHITE);
        mLabel.setColorTextNormal(Color.WHITE);
        mLabel.setColorTextSelect(getResources().getColor(R.color.colorGreen));
        mLabel.setLabels(list);
        //选择图片
        mIvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
        //提交按钮
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlogBean == null) {
                    return;
                }
                if (mTypeBean == null) {
                    showFailed("请先选择一个博客类型");
                    return;
                }
                String title = mEtTitle.getText().toString();
                if (title.isEmpty()) {
                    showFailed("标题不能为空");
                    return;
                }
                mBlogBean.setTitle(title);
                mBlogBean.setTypeBean(mTypeBean);
                mBlogBean.setUserBean(BmobUser.getCurrentUser(UserBean.class));
                mBlogBean.setTime(System.currentTimeMillis());
                mPresenter.addBlog(mCoverUrl, mBlogBean);
            }
        });
        if (mBlogBean == null) {
            return;
        }
        mEtTitle.setText(mBlogBean.getTitle());

    }

    private void initData() {
        mBlogBean = (BlogBean) getIntent().getSerializableExtra("BlogBean");
    }


    public static void enterBlogAddActivity(Context context, BlogBean blogBean) {
        Intent intent = new Intent(context, BlogAddActivity.class);
        intent.putExtra("BlogBean", blogBean);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在加载,请稍候");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showType(final List<BlogTypeBean> list) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        List<LabelEntity> entities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            BlogTypeBean typeBean = list.get(i);
            LabelEntity entity = new LabelEntity(typeBean.getName(), i + "");
            entities.add(entity);
        }
        mLabel.setLabels(entities);
        mLabel.setColorBGNormal(Color.WHITE);
        mLabel.setColorBGSelect(mContext.getResources().getColor(R.color.colorPrimary));
        mLabel.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mTypeBean = list.get(i);
            }
        });
    }

    @Override
    public void showLoadFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    @Override
    public void showAdding() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在分享文章,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showAddingDone() {
        //显示添加成功
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showSuccess("文章分享成功");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, 1500);
    }

    @Override
    public void showAddingFailed(String errorMsg) {
        //显示加载失败
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    //选择图片
    private void selectPic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                mIvCover.setImageBitmap(bitmap);
                mCoverUrl = getRealPathFromURI(uri);
                KLog.e(mCoverUrl);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentUri) { //传入图片uri地址
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
