package com.brioal.sourcecode.collect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectListActivity extends BaseActivity {

    @BindView(R.id.collect_list_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.collect_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.collect_viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_collect_list);
        ButterKnife.bind(this);
        initViewPager();
        initTabLayout();
        initView();
    }

    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPager() {
        CollectListViewPagerAdapter adapter = new CollectListViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    private void initView() {
        //关闭
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void enterCollectActivity(Context context) {
        Intent intent = new Intent(context, CollectListActivity.class);
        context.startActivity(intent);
    }
}
