package com.brioal.sourcecode.blogcollectlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.BlogCollectionBean;
import com.brioal.sourcecode.blogdetail.BlogDetailActivity;
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

public class BlogCollectListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<BlogCollectionBean> mList = new ArrayList<>();

    public BlogCollectListAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<BlogCollectionBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BlogCollectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //BlogCollectViewHolder
    class BlogCollectViewHolder extends BaseViewHolder {
        @BindView(R.id.item_blog_collect_tv_name)
        TextView mTvName;
        @BindView(R.id.item_blog_collect_tv_desc)
        TextView mTvDesc;
        @BindView(R.id.item_blog_collect_iv_img)
        ImageView mIvImg;
        View mItemView;

        public BlogCollectViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogCollectionBean bean = (BlogCollectionBean) object;
            mTvName.setText(bean.getBlogBean().getTitle());
            mTvDesc.setText(bean.getBlogBean().getCollectCount() + "人喜欢-" + bean.getBlogBean().getCommentCount() + "条评论- 于" + DateUtil.convertTime(bean.getCreatedAt()) + "收藏");
            if (bean.getBlogBean().getImg() != null) {
                Glide.with(mContext).load(bean.getBlogBean().getImg().getFileUrl()).into(mIvImg);
            }

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean.getBlogBean());
                }
            });
        }
    }
}
