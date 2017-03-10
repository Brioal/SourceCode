package com.brioal.sourcecode.share;

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

public class ShareListActivity extends BaseActivity {

    @BindView(R.id.share_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.share_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.share_list_btn_close)
    ImageButton mBtnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_share_list);
        ButterKnife.bind(this);
        initViewPager();
        initTabLayout();
        initView();
    }

    private void initView() {
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewPager() {
        ShareViewPagerAdapter adapter = new ShareViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
    }


    public static void enterShareList(Context context) {
        Intent intent = new Intent(context, ShareListActivity.class);
        context.startActivity(intent);
    }
}
