package com.brioal.sourcecode.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.brioal.sourcecode.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    //显示操作成功
    protected void showSuccess(String msg) {
        if (msg.isEmpty()) {
            return;
        }
        StyleableToast st = new StyleableToast(mContext, msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        st.setTextColor(Color.WHITE);
        st.setIcon(R.drawable.ic_done_white);
        st.spinIcon();
        st.setMaxAlpha();
        st.show();
    }

    //显示操作失败
    protected void showFailed(String msg) {
        if (msg.isEmpty()) {
            return;
        }
        StyleableToast st = new StyleableToast(mContext, msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        st.setTextColor(Color.WHITE);
        st.setIcon(R.drawable.ic_error_white);
        st.spinIcon();
        st.setMaxAlpha();
        st.show();
    }
}
