package com.brioal.sourcecode.classlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.brioal.index.IndexAdapter;
import com.brioal.index.IndexBean;
import com.brioal.index.IndexListView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.classlist.contract.ClassListContract;
import com.brioal.sourcecode.classlist.presenter.ClassListPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassListActivity extends BaseActivity implements ClassListContract.View {

    @BindView(R.id.class_list_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.class_list_recyclerView)
    IndexListView mRecyclerView;
    private ClassListPresenterImpl mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_class_list);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new ClassListPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //标题栏
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public static void enterClassList(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在加载数据,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showLoadingDone(List<IndexBean> list) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        IndexAdapter adapter = new ClassListAdapter(mContext);
        mRecyclerView.setData(list).setAdapter(adapter).isIndexBG(true).build();
    }

    @Override
    public void showLoadingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    @Override
    public Context getContext() {
        return mContext;
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
}
