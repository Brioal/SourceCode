package com.brioal.sourcecode.commentlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.BlogCommentBean;
import com.brioal.sourcecode.interfaces.OnCommentListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

class CommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int TYPE_NORMAL = 1;


    private Context mContext;
    private List<BlogCommentBean> mList = new ArrayList<>();
    private OnCommentListener mCommentListener;

    CommentAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<BlogCommentBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    void setCommentListener(OnCommentListener commentListener) {
        mCommentListener = commentListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment_normal, parent, false));
        }
        return new ParentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment_parent, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        BlogCommentBean bean = mList.get(position);
        if (bean.getParentBean()!=null&&!bean.getParentBean().isEmpty()) {
            return TYPE_NORMAL;
        }
        return 0;
    }

    //父组件ParentViewHolder
    class ParentViewHolder extends BaseViewHolder {
        @BindView(R.id.item_comment_parent_head)
        CircleImageView mIvHead;
        @BindView(R.id.item_comment_parent_name)
        TextView mTvName;
        @BindView(R.id.item_comment_parent_time)
        TextView mTvTime;
        @BindView(R.id.item_comment_parent_content)
        TextView mTvContent;

        View mItemView;

        ParentViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogCommentBean bean = (BlogCommentBean) object;
            //加载头像
            Glide.with(mContext).load(bean.getUserBean().getHead().getFileUrl()).into(mIvHead);
            //加载名称
            mTvName.setText(bean.getUserBean().getUsername());
            //发布时间
            mTvTime.setText(bean.getCreatedAt());
            //评论内容
            mTvContent.setText(bean.getContent());
            //点击事件
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCommentListener != null) {
                        mCommentListener.click(bean);
                    }
                }
            });
            //长按事件ain
            mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mCommentListener != null) {
                        mCommentListener.longClickListener(bean);
                    }
                    return true;
                }
            });
        }
    }

    //子组件 NormalViewHolder
    class NormalViewHolder extends BaseViewHolder {
        @BindView(R.id.item_comment_normal_content)
        TextView mTvContent;

        View mItemView;

        NormalViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogCommentBean bean = (BlogCommentBean) object;
            mTvContent.setText(bean.getUserBean().getUsername() + ":" + bean.getContent());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCommentListener != null) {
                        mCommentListener.click(bean);
                    }
                }
            });
            mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mCommentListener != null) {
                        mCommentListener.longClickListener(bean);
                    }
                    return true;
                }
            });
        }
    }
}
