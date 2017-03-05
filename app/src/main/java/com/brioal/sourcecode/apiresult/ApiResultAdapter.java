package com.brioal.sourcecode.apiresult;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.ApiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/4.
 */

public class ApiResultAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<IndexBean> mList = new ArrayList<>();


    public ApiResultAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<IndexBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApiItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_api, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //ApiItemViewHolder
    class ApiItemViewHolder extends BaseViewHolder {
        @BindView(R.id.item_result_tv_tag)
        TextView mTvTag;
        @BindView(R.id.item_result_tv_title)
        TextView mTvTitle;

        View mItemView;

        public ApiItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            ApiBean bean = (ApiBean) object;
            mTvTag.setText(bean.getType() == 0 ? "class" : "package");
            mTvTitle.setText(bean.getName());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/3/4 Enter Detail
                }
            });
        }
    }
}
