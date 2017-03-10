package com.brioal.sourcecode.blogsharelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.blogdetail.BlogDetailActivity;
import com.brioal.sourcecode.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/10.
 */

public class BlogShareAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<BlogBean> mList = new ArrayList<>();

    public BlogShareAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<BlogBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_blog_share, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //收藏Item
    class ShareViewHolder extends BaseViewHolder {
        @BindView(R.id.item_blog_share_tv_name)
        TextView mTvName;
        @BindView(R.id.item_blog_share_tv_desc)
        TextView mTvDesc;
        View mItemView;

        public ShareViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogBean bean = (BlogBean) object;
            mTvName.setText(bean.getTitle());
            mTvDesc.setText(bean.getCommentCount() + "人喜欢-" + bean.getUserBean().getUsername() + "-" + DateUtil.convertTime(bean.getCreatedAt()));
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean);
                }
            });
        }
    }
}
