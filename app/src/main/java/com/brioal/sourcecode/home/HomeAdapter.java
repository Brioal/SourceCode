package com.brioal.sourcecode.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.bean.BlogBean;
import com.brioal.sourcecode.blogdetail.BlogDetailActivity;
import com.brioal.sourcecode.interfaces.OnHotRefreshListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int TYPE_HOT_NORMAL = 0;//普通热门Item
    private final int TYPE_HOT_HEAD = 1;//顶部热门Item
    private final int TYPE_NORMAL_NO_PIC = 2;//没有图片的Item
    private final int TYPE_NORMAL_PIC = 3;//有图片的Item


    private Context mContext;
    private List<BlogBean> mHots = new ArrayList<>();
    private List<BlogBean> mNormals = new ArrayList<>();
    private List<BlogBean> mList = new ArrayList<>();
    private boolean isHotShowing = false;
    private OnHotRefreshListener mRefreshListener;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setRefreshListener(OnHotRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    //显示热门消息
    public void showHot(List<BlogBean> list) {
        mHots.clear();
        mHots.addAll(list);
        if (isHotShowing) {
            //已经显示了热门分类了
            //删除前三个
            if (mList.size() < 3) {
                return;
            }
            for (int i = 0; i < mHots.size(); i++) {
                mList.set(i, mHots.get(i));
            }
            notifyDataSetChanged();
        } else {
            //没有显示热门分类
            for (int i = 0; i < mHots.size(); i++) {
                mList.add(i, mHots.get(i));//添加到开头
                notifyItemInserted(i);
            }
            isHotShowing = true;
        }
    }

    //显示普通消息
    public void showNormals(List<BlogBean> list) {
        mNormals.clear();
        mNormals.addAll(list);
        if (isHotShowing) {
            //已经显示了热门消息
            mList.clear();
            mList.addAll(mHots);
            mList.addAll(list);
        } else {
            //没有显示热门消息
            mList.clear();
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    //设置是否显示热门消息
    public void setIsHotShowing(boolean isHotShowing) {
        this.isHotShowing = isHotShowing;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HOT_HEAD) {
            return new HotHeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_hot_head, parent, false));
        }
        if (viewType == TYPE_HOT_NORMAL) {
            return new HotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_hot, parent, false));
        }
        if (viewType == TYPE_NORMAL_NO_PIC) {
            return new NoPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_no_pic, parent, false));
        }
        if (viewType == TYPE_NORMAL_PIC) {
            return new PicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_pic, parent, false));
        }
        return null;
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
        if (isHotShowing) {
            //热门数据显示
            if (position == 0) {
                return TYPE_HOT_HEAD;
            }
            if (position == 1 || position == 2) {
                return TYPE_HOT_NORMAL;
            }
            if (mList.get(position).getImg() != null) {
                return TYPE_NORMAL_PIC;
            }
            return TYPE_NORMAL_NO_PIC;
        }
        if (mList.get(position).getImg() != null) {
            return TYPE_NORMAL_PIC;
        }
        return TYPE_NORMAL_NO_PIC;
    }

    //置顶热门ViewHolder
    class HotHeadViewHolder extends BaseViewHolder {
        @BindView(R.id.home_hot_btn_refresh)
        ImageButton mBtnRefresh;
        @BindView(R.id.home_hot_btn_close)
        ImageButton mBtnClose;
        @BindView(R.id.home_hot_tv_title)
        TextView mTvTitle;
        @BindView(R.id.home_hot_tv_collections)
        TextView mTvCollections;
        @BindView(R.id.home_hot_tv_author)
        TextView mTvAuthor;
        @BindView(R.id.home_hot_tv_comment)
        TextView mTvComment;
        @BindView(R.id.home_hot_iv_cover)
        CircleImageView mIvCover;
        View mItemView;

        public HotHeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemView = itemView;
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogBean bean = (BlogBean) object;
            //点击事件
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean);
                }
            });
            //刷新
            mBtnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotating);
                    animation.setDuration(1500);
                    animation.setInterpolator(new AccelerateDecelerateInterpolator());
                    mBtnRefresh.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (mRefreshListener != null) {
                                mRefreshListener.refresh();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            //关闭热门
            mBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isHotShowing = false;
                    mList.clear();
                    mList.addAll(mNormals);
                    notifyDataSetChanged();
                }
            });
            //标题
            mTvTitle.setText(bean.getTitle());
            //收藏数量
            mTvCollections.setText(bean.getCollectCount() + "");
            //分享用户
            mTvAuthor.setText(bean.getUserBean().getUsername() + "");
            //评论数量
            mTvComment.setText(bean.getCommentCount() + "");
            //博客封面
            Glide.with(mContext).load(bean.getImg().getFileUrl()).into(mIvCover);
        }
    }

    //普通热门ViewHolder
    class HotViewHolder extends BaseViewHolder {
        @BindView(R.id.home_hot_normal_tv_title)
        TextView mTvTitle;
        @BindView(R.id.home_hot_normal_tv_collections)
        TextView mTvCollections;
        @BindView(R.id.home_hot_normal_tv_author)
        TextView mTvAuthor;
        @BindView(R.id.home_hot_normal_tv_comment)
        TextView mTvComment;
        @BindView(R.id.home_hot_normal_iv_cover)
        CircleImageView mIvCover;

        View mItemView;

        public HotViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogBean bean = (BlogBean) object;
            //点击事件
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean);
                }
            });
            //标题
            mTvTitle.setText(bean.getTitle());
            //收藏数量
            mTvCollections.setText(bean.getCollectCount() + "");
            //分享用户
            mTvAuthor.setText(bean.getUserBean().getUsername() + "");
            //评论数量
            mTvComment.setText(bean.getCommentCount() + "");
            //博客封面
            Glide.with(mContext).load(bean.getImg().getFileUrl()).into(mIvCover);
        }
    }

    //无图片ViewHolder
    class NoPicViewHolder extends BaseViewHolder {
        @BindView(R.id.home_normal_ic_head)
        CircleImageView mIcHead;
        @BindView(R.id.home_normal_tv_name)
        TextView mTvName;
        @BindView(R.id.home_normal_tv_author)
        TextView mTvAuthor;
        @BindView(R.id.home_normal_tv_type)
        TextView mTvType;
        @BindView(R.id.home_normal_tv_pic)
        ImageView mTvPic;
        @BindView(R.id.home_normal_tv_title)
        TextView mTvTitle;
        @BindView(R.id.home_normal_tv_collect)
        TextView mTvCollect;
        @BindView(R.id.home_normal_tv_comment)
        TextView mTvComment;

        View mItemView;

        public NoPicViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogBean bean = (BlogBean) object;
            //点击事件
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean);
                }
            });
            //标题
            mTvTitle.setText(bean.getTitle());
            //收藏数量
            mTvCollect.setText(bean.getCollectCount() + "");
            //分享用户
            //作者
            mTvAuthor.setText(bean.getUserBean().getPro() + "," + bean.getUserBean().getDesc());
            //评论数量
            mTvComment.setText(bean.getCommentCount() + "");
            //默认封面
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(mTvPic);
            //用户名字
            mTvName.setText(bean.getUserBean().getUsername() + "");
            //作者
            mTvAuthor.setText(bean.getUserBean().getPro() + "," + bean.getUserBean().getDesc());
            //类型
            mTvType.setText(bean.getTypeBean().getName());
            //用户头像
            Glide.with(mContext).load(bean.getUserBean().getHead().getFileUrl()).into(mIcHead);
        }
    }

    //有图片ViewHolder
    class PicViewHolder extends BaseViewHolder {
        @BindView(R.id.home_normal_pic_ic_head)
        CircleImageView mIvHead;
        @BindView(R.id.home_normal_pic_tv_name)
        TextView mTvName;
        @BindView(R.id.home_normal_pic_tv_desc)
        TextView mTvDesc;
        @BindView(R.id.home_normal_pic_tv_type)
        TextView mTvType;
        @BindView(R.id.home_normal_pic_iv_img)
        ImageView mIvImg;
        @BindView(R.id.home_normal_pic_tv_title)
        TextView mTvTitle;
        @BindView(R.id.home_normal_pic_tv_collect)
        TextView mTvCollect;
        @BindView(R.id.home_normal_pic_tv_comment)
        TextView mTvComment;

        View mItemView;

        public PicViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, mItemView);
        }

        @Override
        public void bindView(Object object, int position) {
            final BlogBean bean = (BlogBean) object;
            //点击事件
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogDetailActivity.enterBlogDetail(mContext, bean);
                }
            });
            //标题
            mTvTitle.setText(bean.getTitle());
            //收藏数量
            mTvCollect.setText(bean.getCollectCount() + "");
            //评论数量
            mTvComment.setText(bean.getCommentCount() + "");
            //默认封面
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(mIvImg);
            //用户名字
            mTvName.setText(bean.getUserBean().getUsername() + "");
            //作者
            mTvDesc.setText(bean.getUserBean().getPro() + "," + bean.getUserBean().getDesc());
            //类型
            mTvType.setText(bean.getTypeBean().getName());
            //用户头像
            Glide.with(mContext).load(bean.getUserBean().getHead().getFileUrl()).into(mIvHead);
            //封面
            Glide.with(mContext).load(bean.getImg().getFileUrl()).into(mIvImg);
        }
    }
}
