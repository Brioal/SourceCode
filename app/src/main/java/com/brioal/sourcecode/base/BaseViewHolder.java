package com.brioal.sourcecode.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ViewHolder基类
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/27.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(Object object, int position);
}
