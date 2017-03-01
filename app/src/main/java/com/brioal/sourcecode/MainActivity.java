package com.brioal.sourcecode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.brioal.sourcecode.api.ApiFragment;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.base.BaseFragment;
import com.brioal.sourcecode.home.HomeFragment;
import com.brioal.sourcecode.lib.LibFragment;
import com.brioal.sourcecode.mine.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_navigation)
    BottomNavigationView mNavigation;

    private BaseFragment mCurrentFragment;
    private final String APPID = "cc06111b6eb97dfa780e455f7230486a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CodeTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initNavigationView();
        changeFragment(HomeFragment.getInstance());
        initSDK();
    }

    private void initSDK() {
        Bmob.initialize(mContext, APPID);
    }

    //初始化导航栏
    private void initNavigationView() {
        mNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                changeFragment(HomeFragment.getInstance());
                return true;
            case R.id.navigation_api:
                changeFragment(ApiFragment.getInstance());
                return true;
            case R.id.navigation_lib:
                changeFragment(LibFragment.getInstance());
                return true;
            case R.id.navigation_mine:
                changeFragment(MineFragment.getInstance());
                return true;
        }
        return false;
    }

    private void changeFragment(BaseFragment fragment) {
        if (mCurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
        }
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
        }
        mCurrentFragment = fragment;
    }
}
