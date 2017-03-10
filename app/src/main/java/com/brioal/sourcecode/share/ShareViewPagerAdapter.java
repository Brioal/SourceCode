package com.brioal.sourcecode.share;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brioal.sourcecode.blogsharelist.BlogShareListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class ShareViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles = new ArrayList<>();

    public ShareViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mTitles.add("博客分享");
        mTitles.add("开源库分享");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BlogShareListFragment.getInstance();
            case 1:
                return LibShareListFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
