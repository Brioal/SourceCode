package com.brioal.sourcecode.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.login.contract.LoginContract;
import com.brioal.sourcecode.login.presenter.LoginPresenterImpl;
import com.brioal.sourcecode.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.login_et_mail)
    EditText mEtMail;
    @BindView(R.id.login_et_pass)
    EditText mEtPass;
    @BindView(R.id.login_tv_register)
    TextView mTvRegister;
    @BindView(R.id.login_tv_forget)
    TextView mTvForget;
    @BindView(R.id.login_btn_login)
    Button mBtnLogin;
    @BindView(R.id.login_btn_close)
    ImageButton mBtnClose;

    private ProgressDialog mProgressDialog;
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new LoginPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //返回
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        //注册
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.enterRegister(mContext);
            }
        });
        //忘记密码
        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/28 忘记密码
            }
        });
        //登陆
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtMail.getText().toString().trim();
                String pass = mEtPass.getText().toString().trim();
                if (email.isEmpty() || pass.isEmpty()) {
                    showSuccess("请填写完成的用户信息");
                    return;
                }
                mPresenter.login(email, pass);
            }
        });

    }

    @Override
    public void showLogining() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在登陆,请稍候");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showLoginSuccess() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showSuccess("登陆成功,跳转主界面");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, 1500);
    }

    @Override
    public void showLoginFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }
}
