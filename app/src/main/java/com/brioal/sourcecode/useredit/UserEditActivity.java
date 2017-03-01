package com.brioal.sourcecode.useredit;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.useredit.contract.UserEditContract;
import com.brioal.sourcecode.useredit.presenter.UserEditPresenterImpl;
import com.bumptech.glide.Glide;
import com.socks.library.KLog;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserEditActivity extends BaseActivity implements UserEditContract.View {

    @BindView(R.id.user_edit_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.user_edit_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.user_edit_et_desc)
    EditText mEtDesc;
    @BindView(R.id.user_edit_et_blog)
    EditText mEtBlog;
    @BindView(R.id.user_edit_et_pro)
    EditText mEtPro;
    @BindView(R.id.user_edit_et_company)
    EditText mEtCompany;
    @BindView(R.id.user_edit_btn_done)
    Button mBtnDone;
    @BindView(R.id.user_edit_btn_close)
    ImageButton mBtnClose;

    private UserBean mUserBean;
    private ProgressDialog mProgressDialog;
    private UserEditPresenterImpl mPresenter;
    private String mHeadUrl;//头像的地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_edit);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new UserEditPresenterImpl(this);
        mPresenter.start();
    }

    private void initData() {
        //获取传入的UserBean
        mUserBean = (UserBean) getIntent().getSerializableExtra("UserBean");
        if (mUserBean == null) {
            return;
        }
        //显示头像
        if (mUserBean.getHead() != null) {
            Glide.with(mContext).load(mUserBean.getHead().getFileUrl()).into(mIvHead);
        }
        //显示描述
        if (mUserBean.getDesc() != null) {
            mEtDesc.setText(mUserBean.getDesc());
        }
        //显示职位
        if (mUserBean.getPro() != null) {
            mEtPro.setText(mUserBean.getPro());
        }
        //显示博客地址
        if (mUserBean.getBlogUrl() != null) {
            mEtBlog.setText(mUserBean.getBlogUrl());
        }
        //显示公司
        if (mUserBean.getCompany() != null) {
            mEtCompany.setText(mUserBean.getCompany());
        }
    }

    private void initView() {
        //标题栏
        setSupportActionBar(mToolBar);
        //返回
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选择图片
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
        //上传资料
        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = mEtDesc.getText().toString().trim();
                String blogUrl = mEtBlog.getText().toString().trim();
                String pro = mEtPro.getText().toString().trim();
                String company = mEtCompany.getText().toString().trim();
                mUserBean.setDesc(desc).setBlogUrl(blogUrl).setPro(pro).setCompany(company);
                mPresenter.save(mHeadUrl, mUserBean);
            }
        });
    }

    @Override
    public void showSaving() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍候");
        mProgressDialog.setMessage("正在保存信息,请稍候");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showSavingDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        //回到个人中心
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showSavingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

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
                mIvHead.setImageBitmap(bitmap);
                mHeadUrl = getRealPathFromURI(uri);
                KLog.e(mHeadUrl);
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
