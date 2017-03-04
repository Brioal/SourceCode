package com.brioal.sourcecode.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.LibBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/2.
 */

public class LibAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<LibBean> mList = new ArrayList<>();

    public LibAdapter(Context context) {
        mContext = context;
    }

    //显示开源库列表
    public void showList(List<LibBean> list) {
        mList.clear();
        mList.addAll(list);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LibViewHolder(LayoutInflater.from(mContext).inflate((R.layout.item_lib), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //开源库ViewHolder
    class LibViewHolder extends BaseViewHolder {
        @BindView(R.id.item_lib_tv_title)
        TextView mTvTitle;
        @BindView(R.id.item_lib_tv_desc)
        TextView mTvDesc;
        @BindView(R.id.item_lib_tv_label)
        TextView mTvLabel;
        @BindView(R.id.item_lib_tv_time)
        TextView mTvTime;

        View mItemView;

        public LibViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            LibBean bean = (LibBean) object;
            mTvTitle.setText(bean.getTitle());
            mTvDesc.setText(bean.getDesc());
            mTvLabel.setText(bean.getLabel());
            mTvTime.setText(bean.getCreatedAt());

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/3/2 跳转开源库详情
                }
            });
        }
    }
}
