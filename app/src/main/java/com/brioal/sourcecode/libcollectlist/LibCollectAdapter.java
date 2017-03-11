package com.brioal.sourcecode.libcollectlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.LibCollectBean;
import com.brioal.sourcecode.libdetail.LibDetailActivity;
import com.brioal.sourcecode.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/11.
 */

public class LibCollectAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<LibCollectBean> mList = new ArrayList<>();

    public LibCollectAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<LibCollectBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LibCollectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_no_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LibCollectViewHolder extends BaseViewHolder {
        @BindView(R.id.item_blog_share_tv_name)
        TextView mTvName;
        @BindView(R.id.item_blog_share_tv_desc)
        TextView mTvDesc;

        View mItemView;

        public LibCollectViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final LibCollectBean bean = (LibCollectBean) object;
            mTvName.setText(bean.getLibBean().getTitle());
            mTvDesc.setText(bean.getLibBean().getCollectCount() + "人收藏-" + bean.getLibBean().getCommentCount() + "条评论-于" + DateUtil.convertTime(bean.getCreatedAt()) + "收藏");

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LibDetailActivity.enterLibDetail(mContext, bean.getLibBean());
                }
            });
        }
    }
}
