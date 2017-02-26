package com.brioal.sourcecode.api;

import com.brioal.sourcecode.base.BaseFragment;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class ApiFragment extends BaseFragment {
    private static ApiFragment sFragment;

    public static ApiFragment getInstance() {
        if (sFragment == null) {
            sFragment = new ApiFragment();
        }
        return sFragment;
    }
}
