package com.brioal.sourcecode.share;

import com.brioal.sourcecode.base.BaseFragment;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class LibShareListFragment extends BaseFragment {
    private static LibShareListFragment sFragment;

    public static LibShareListFragment getInstance() {
        if (sFragment == null) {
            sFragment = new LibShareListFragment();
        }
        return sFragment;
    }
}
