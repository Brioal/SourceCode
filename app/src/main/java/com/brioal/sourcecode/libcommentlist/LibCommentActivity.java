package com.brioal.sourcecode.libcommentlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.sourcecode.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class LibCommentActivity extends AppCompatActivity {

    @BindView(R.id.lib_comment_btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.lib_comment_iv_loading)
    ImageView mIvLoading;
    @BindView(R.id.lib_comment_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.lib_comment_layout)
    PtrFrameLayout mLayout;
    @BindView(R.id.lib_comment_et_comment)
    EditText mEtComment;
    @BindView(R.id.lib_comment_btn_send)
    TextView mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lib_comment);
        ButterKnife.bind(this);
    }
}
