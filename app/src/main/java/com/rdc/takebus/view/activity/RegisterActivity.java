package com.rdc.takebus.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.presenter.ActivityPresenter.RegisterPresenter;
import com.rdc.takebus.presenter.tbinterface.RegisterInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class RegisterActivity extends BaseActivity<RegisterInterface, RegisterPresenter>
        implements RegisterInterface {
    private EditText etNickname;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private ImageView ivNext;
    private ImageView ivZhifubao;
    private ImageView btn_backtologin;
    private String strNickname;
    private String strEmail;
    private String strPhone;
    private String strPassword;
    private boolean blNickname = false;
    private boolean blEmail = false;
    private boolean blPhone = false;
    private boolean blPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void loadData() {}

    @Override
    protected void initViews(Bundle savedInstanceState) {
        etNickname = (EditText) findViewById(R.id.etNickname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivZhifubao = (ImageView) findViewById(R.id.ivZhifubao);
        btn_backtologin =(ImageView)findViewById(R.id.ivBackToLogin);
        // 监听输入状态
        etNickname.addTextChangedListener(new RegisterWatcher(R.id.etNickname));
        etEmail.addTextChangedListener(new RegisterWatcher(R.id.etEmail));
        etPhone.addTextChangedListener(new RegisterWatcher(R.id.etPhone));
        etPassword.addTextChangedListener(new RegisterWatcher(R.id.etPassword));

        // 点击事件
        ivNext.setOnClickListener(this);
        ivZhifubao.setOnClickListener(this);
        btn_backtologin.setOnClickListener(this);
        setBtnBackGround();
    }

    @Override
    protected void initVariables() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                if (isNext()){
                    // 填写完成，连接后台操作
                    // 后台返回成功后
                    // 销毁本activity，否则留在本activity，吐司失败
                    finish();
                    setPendingTransition();
                }
                break;
            case R.id.ivZhifubao:
                CustomToast.showToast(this, "使用支付宝帐号登录", Toast.LENGTH_LONG);
                break;

            case R.id.ivBackToLogin:
                finish();
                setPendingTransition();
            default:
                break;
        }
    }
    private boolean isNext(){
        if (etNickname.getText().toString().length() != 0 && etEmail.getText().toString().length() != 0
                && etPhone.getText().toString().length() != 0 && etPassword.getText().toString().length() != 0){
                return true;
        }
          return false;
    }

    private void setBtnBackGround(){
        if (isNext()){
            ivNext.setImageResource(R.drawable.btn_next);
        }else{
            ivNext.setImageResource(R.drawable.btn_next_gray);
        }
    }

    class RegisterWatcher implements TextWatcher{
        private int id;

        public RegisterWatcher(int id) {
            this.id = id;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.etNickname:
                    strNickname = etNickname.getText().toString();
                    if (strNickname.length() != 0) {
                        blNickname = true;
                    } else {
                        blNickname = false;
                    }
                    break;
                case R.id.etEmail:
                    strEmail = etEmail.getText().toString();
                    if (strEmail.length() != 0) {
                        blEmail = true;
                    } else {
                        blEmail = false;
                    }
                    break;
                case R.id.etPhone:
                    strPhone = etPhone.getText().toString();
                    if (strPhone.length() != 0) {
                        blPhone = true;
                    } else {
                        blPhone = false;
                    }
                    break;
                case R.id.etPassword:
                    strPassword = etPassword.getText().toString();
                    if (strPassword.length() != 0) {
                        blPassword = true;
                    } else {
                        blPassword = false;
                    }
                    break;
                default:
                    break;
            }
            if (blEmail && blNickname && blPassword && blPhone) {
                ivNext.setImageResource(R.drawable.btn_next);
            } else {
                ivNext.setImageResource(R.drawable.btn_next_gray);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
