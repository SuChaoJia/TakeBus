package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.model.utils.AppConstance;
import com.rdc.takebus.presenter.ActivityPresenter.LoginPresenter;
import com.rdc.takebus.presenter.tbinterface.LoginInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class LoginActivity extends BaseActivity<LoginInterface, LoginPresenter> implements LoginInterface {
    private ImageView ivBack;
    private ImageView ivLogin;
    private ImageView ivRegister;
    private ImageView ivZhifubao;
    private EditText etAccount;
    private EditText etPassword;
    private String strAccount;
    private String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        ivRegister = (ImageView) findViewById(R.id.ivRegister);
        ivZhifubao = (ImageView) findViewById(R.id.ivZhifubao);
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivBack.setOnClickListener(this);
        ivLogin.setOnClickListener(this);
        ivRegister.setOnClickListener(this);
        ivZhifubao.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                setPendingTransition();
                break;
            case R.id.ivLogin:
                strAccount = etAccount.getText().toString();
                strPassword = etPassword.getText().toString();
                CustomToast.showToast(this,
                        "账户:" + strAccount + "\n" + "密码:" + strPassword,
                        Toast.LENGTH_LONG);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                setPendingTransition();
                AppConstance.firstActivity.finish();//登录成功，把第一个页面也销毁掉
                finish();
                break;
            case R.id.ivRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                setPendingTransition();
                break;
            case R.id.ivZhifubao:
                CustomToast.showToast(this, "使用支付宝帐号登录", Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
