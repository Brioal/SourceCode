package com.brioal.sourcecode.collect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brioal.sourcecode.blogcollectlist.BlogCollectListFragment;
import com.brioal.sourcecode.libcollectlist.LibCollectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class CollectListViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles = new ArrayList<>();

    public CollectListViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mTitles.add("博客收藏");
        mTitles.add("开源库收藏");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BlogCollectListFragment.getInstance();
            case 1:
                return LibCollectFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
