package com.brioal.sourcecode.mine;

import com.brioal.sourcecode.base.BaseFragment;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class MineFragment extends BaseFragment {
    private static MineFragment sFragment;

    public static MineFragment getInstance() {
        if (sFragment == null) {
            sFragment = new MineFragment();
        }
        return sFragment;
    }
}
