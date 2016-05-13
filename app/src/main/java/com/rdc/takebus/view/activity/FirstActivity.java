package com.rdc.takebus.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.model.utils.AppConstance;
import com.rdc.takebus.view.CustomView.CustomToast;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class FirstActivity extends Activity implements View.OnClickListener{
    private ImageView mIvSubway;
    private ImageView mIvBus;
    private ImageView mIvLogo;
    private AlphaAnimation mAlphaAnimation;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        //状态栏透明化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initVariables();
        initViews();
    }

    private void initViews() {
        mIvSubway = (ImageView) findViewById(R.id.ivSub);
        mIvBus = (ImageView) findViewById(R.id.ivBus);
        mIvLogo = (ImageView) findViewById(R.id.ivLogo);
        mIvSubway.setOnClickListener(this);
        mIvBus.setOnClickListener(this);

        //渐显示动画
        mAlphaAnimation = new AlphaAnimation(0,1);
        mAlphaAnimation.setDuration(2000);
        mIvLogo.startAnimation(mAlphaAnimation);
        mIvBus.startAnimation(mAlphaAnimation);
        mIvSubway.startAnimation(mAlphaAnimation);
    }

    private void initVariables() {
        AppConstance.firstActivity = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSub:
                // 地铁
                CustomToast.showToast(this, "地铁系统尚未开通", Toast.LENGTH_SHORT);
                break;
            case R.id.ivBus:
                // 公交,先进入登录界面，到时候需要判断用户是否已经登录，暂时先这么写
                startActivity(new Intent(FirstActivity.this,
                        LoginActivity.class));
                overridePendingTransition(R.anim.translate_right_in,
                        R.anim.translate_left_out);
                break;
            default:
                break;
        }
    }

    // 再按一次退出程序
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                CustomToast.showToast(FirstActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
