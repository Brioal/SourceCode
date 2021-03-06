package com.brioal.sourcecode.libsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseViewHolder;
import com.brioal.sourcecode.libsearch.presenter.LibSearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/7.
 */

public class LibHistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private LibSearchPresenterImpl mPresenter;

    public LibHistoryAdapter(Context context, LibSearchPresenterImpl presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    public void showList(List<String> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //ItemViewHolder
    class HistoryViewHolder extends BaseViewHolder {
        @BindView(R.id.item_history_tv_name)
        TextView mTvName;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Object object, int position) {
            String name = (String) object;
            mTvName.setText(name);
            mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = mTvName.getText().toString();
                    mPresenter.search(key);
                }
            });
        }
    }
}
