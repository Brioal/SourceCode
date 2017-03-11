package com.brioal.sourcecode.readhistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.ReadBean;
import com.brioal.sourcecode.blogdetail.BlogDetailActivity;
import com.brioal.sourcecode.libdetail.LibDetailActivity;
import com.brioal.sourcecode.util.DateUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class ReadHistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<ReadBean> mList = new ArrayList<>();

    public void showList(List<ReadBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public ReadHistoryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_read, parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.item_read_tv_name)
        TextView mTvName;
        @BindView(R.id.item_read_tv_type)
        TextView mTvType;
        @BindView(R.id.item_read_tv_desc)
        TextView mTvDesc;
        @BindView(R.id.item_read_iv_img)
        ImageView mIvImg;

        View mItemView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final ReadBean bean = (ReadBean) object;
            if (bean.getBlogBean() != null) {
                //博客阅读
                mTvType.setText("博客");
                mTvName.setText(bean.getBlogBean().getTitle());
                if (bean.getBlogBean().getImg() != null) {
                    Glide.with(mContext).load(bean.getBlogBean().getImg().getFileUrl()).into(mIvImg);
                }
                mTvDesc.setText(DateUtil.convertTime(bean.getCreatedAt()));
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BlogDetailActivity.enterBlogDetail(mContext, bean.getBlogBean());
                    }
                });
            } else if (bean.getLibBean() != null) {
                //开源库阅读
                mTvType.setText("开源库");
                mTvName.setText(bean.getLibBean().getTitle());
                mTvDesc.setText(DateUtil.convertTime(bean.getCreatedAt()));
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LibDetailActivity.enterLibDetail(mContext, bean.getLibBean());
                    }
                });
            }
        }
    }
}
