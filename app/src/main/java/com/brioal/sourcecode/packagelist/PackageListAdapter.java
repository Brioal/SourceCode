package com.brioal.sourcecode.packagelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.index.IndexAdapter;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.api.ApiDetailActivity;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.ApiBean;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/5.
 */

public class PackageListAdapter extends IndexAdapter<BaseViewHolder> {
    public PackageListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListPackageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).bindView(mList.get(position), position);
        }
    }

    //List ItemViewHolder
    class ListPackageViewHolder extends BaseViewHolder {
        @BindView(R.id.item_list_tv_head)
        TextView mTvHead;
        @BindView(R.id.item_list_tv_type)
        TextView mTvType;
        @BindView(R.id.item_list_tv_name)
        TextView mTvName;

        View mItemView;

        public ListPackageViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final ApiBean bean = (ApiBean) object;
            mTvName.setText(bean.getName());
            mTvType.setText("package");
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiDetailActivity.enterApiDetail(mContext, bean);
                }
            });
            if (position == 0) {
                //Header
                mTvHead.setVisibility(View.VISIBLE);
                mTvHead.setText(bean.getIndex() + "");
                return;
            }
            if (mList.get(position).getIndex() == mList.get(position - 1).getIndex()) {
                //没有Header
                mTvHead.setVisibility(GONE);
            } else {
                mTvHead.setVisibility(View.VISIBLE);
                mTvHead.setText(bean.getIndex() + "");
            }
        }
    }
}
