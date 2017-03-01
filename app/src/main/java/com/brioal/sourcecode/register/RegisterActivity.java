package com.brioal.sourcecode.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;
import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.register.contract.RegisterContract;
import com.brioal.sourcecode.register.presenter.RegisterPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.register_et_mail)
    EditText mEtMail;
    @BindView(R.id.register_et_name)
    EditText mEtName;
    @BindView(R.id.register_et_pass)
    EditText mEtPass;
    @BindView(R.id.register_et_pass_again)
    EditText mEtPassAgain;
    @BindView(R.id.register_btn_login)
    Button mBtnLogin;
    @BindView(R.id.register_btn_close)
    ImageButton mBtnClose;

    private ProgressDialog mProgressDialog;
    private RegisterContract.Presenter mPresenter;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new RegisterPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //返回
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //注册
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtMail.getText().toString();
                String userName = mEtName.getText().toString();
                String pass = mEtPass.getText().toString();
                String passConfig = mEtPassAgain.getText().toString();
                //检查邮箱
                if (email.isEmpty() || !email.contains("@")) {
                    showFailed("邮箱不能为空");
                    return;
                }
                //检查用户名
                if (userName.isEmpty()) {
                    showFailed("用户名不能为空");
                    return;
                }
                //检查密码
                if (pass.isEmpty() || passConfig.isEmpty() || !pass.equals(passConfig)) {
                    showFailed("密码填写不成功,请确认后再试");
                    return;
                }
                UserBean bean = new UserBean();
                bean.setUsername(userName);
                bean.setEmail(email);
                bean.setPassword(pass);
                mPresenter.register(bean);
            }
        });
    }

    public static void enterRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showRegistering() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在注册,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showRegisterDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showSuccess("注册成功,返回登陆界面");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, 1500);
    }

    @Override
    public void showRegisterFailed(String errorMsg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showFailed(errorMsg);
    }

    @Override
    public void showSignUpedUser(UserBean userBean) {
        mUserBean = userBean;
    }
}
