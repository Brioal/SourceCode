package com.brioal.sourcecode.lib;

import com.brioal.sourcecode.base.BaseFragment;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class LibFragment extends BaseFragment {
    private static LibFragment sFragment;

    public static LibFragment getInstance() {
        if (sFragment == null) {
            sFragment = new LibFragment();
        }
        return sFragment;
    }
}
