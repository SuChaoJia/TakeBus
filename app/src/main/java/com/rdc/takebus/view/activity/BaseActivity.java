package com.rdc.takebus.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.presenter.ActivityPresenter.BaseActivityPresenter;
import com.rdc.takebus.view.CustomView.CustomToast;


/**
 * Created by 梦涵 on 2016/5/10.
 * 建立Presenter弱引用
 */
public abstract class BaseActivity<V, T extends BaseActivityPresenter<V>>
        extends Activity implements View.OnClickListener{
  protected T mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //创建Presenter
        mPresenter = createPresenter();
        //View与Presenter建立关联
        mPresenter.attachView((V) this);
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    protected abstract T createPresenter();

    protected abstract void loadData();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initVariables();

    // 返回键触发事件,返回到FirstActivity
    @Override
    public void onBackPressed(){
        finish();
        setPendingTransition();
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放所持有的Activity对象,避免内存泄露
        mPresenter.detachView();
    }

    public void setPendingTransition(){
        overridePendingTransition(R.anim.translate_right_in,
                R.anim.translate_left_out);
    }


    // 再按一次退出程序
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                CustomToast.showToast(BaseActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT);
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
