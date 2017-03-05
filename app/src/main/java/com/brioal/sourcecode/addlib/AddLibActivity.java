package com.brioal.sourcecode.addlib;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.addlib.contract.AddLibContract;
import com.brioal.sourcecode.addlib.presenter.AddLibPresenterImpl;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.bean.UserBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class AddLibActivity extends BaseActivity implements AddLibContract.View {

    @BindView(R.id.add_blog_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.add_lib_et_url)
    EditText mEtUrl;
    @BindView(R.id.add_lib_et_title)
    EditText mEtTitle;
    @BindView(R.id.add_lib_et_desc)
    EditText mEtDesc;
    @BindView(R.id.add_lib_et_label)
    EditText mEtLabel;
    @BindView(R.id.add_lib_btn_login)
    Button mBtnLogin;

    private AddLibPresenterImpl mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_lib);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new AddLibPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //ToolBar
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //添加按钮
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtUrl.getText().toString().trim();
                String title = mEtTitle.getText().toString().trim();
                String desc = mEtDesc.getText().toString().trim();
                String label = mEtLabel.getText().toString().trim();
                UserBean userBean = BmobUser.getCurrentUser(UserBean.class);

                if (url.isEmpty()) {
                    showFailed("链接地址不能为空");
                    return;
                }
                if (title.isEmpty()) {
                    showFailed("标题不能为空");
                    return;
                }
                if (label.isEmpty()) {
                    showFailed("标签不能为空");
                    return;
                }
                LibBean bean = new LibBean();
                bean.setUrl(url).setTitle(title).setDesc(desc).setLabel(label).setUserBean(userBean);
                mPresenter.add(bean);
            }
        });
    }

    @Override
    public void showAdding() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在添加,请稍候");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showAddingDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showAddingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
