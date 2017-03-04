package com.brioal.sourcecode.api;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.api.contract.ApiContract;
import com.brioal.sourcecode.api.presenter.ApiPresenterImpl;
import com.brioal.sourcecode.apiresult.ApiResultActivity;
import com.brioal.sourcecode.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class ApiFragment extends BaseFragment implements ApiContract.View {
    private static ApiFragment sFragment;
    @BindView(R.id.api_et_key)
    EditText mEtKey;
    @BindView(R.id.api_btn_search)
    Button mBtnSearch;
    @BindView(R.id.api_tv_class)
    TextView mTvClass;
    @BindView(R.id.api_tv_package)
    TextView mTvPackage;
    @BindView(R.id.api_recyclerView)
    RecyclerView mRecyclerView;

    public static ApiFragment getInstance() {
        if (sFragment == null) {
            sFragment = new ApiFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_api, container, false);
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
        mPresenter = new ApiPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //搜索事件
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mEtKey.getText().toString();
                if (key.isEmpty()) {
                    showFailed("关键字不能为空");
                    return;
                }
                mPresenter.saveHistory(key);
                mPresenter.start();
                ApiResultActivity.enterResult(mContext, key);
            }
        });
        //跳转class
        mTvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/3/3 跳转class列表
            }
        });
        //跳转package
        mTvPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/3/3 跳转class列表
            }
        });
    }

    private ApiPresenterImpl mPresenter;

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在加载数据,请稍候");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showHistory(List<String> list) {
        HistoryAdapter adapter = new HistoryAdapter(mContext);
        adapter.showList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoadingDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showLoadingFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }
}
