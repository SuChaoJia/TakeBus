package com.rdc.takebus.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.presenter.ActivityPresenter.LoginPresenter;
import com.rdc.takebus.presenter.tbinterface.LoginInterface;
import com.rdc.takebus.view.fragment.SelectBusFirstFragment;
import com.rdc.takebus.view.fragment.SelectBusFragment;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class SelectRouteActivity extends BaseActivity<LoginInterface, LoginPresenter>
   implements LoginInterface {
    private LinearLayout linearlayout_return;
    private TextView tvStartingpoint,tvDestination;
    private RelativeLayout setCommonStation,editCommonStation;
    private ScrollView scrollView;
    private SelectBusFragment selectBusFragment;
    private SelectBusFirstFragment selectBusFirstFragment;
    private View view;
    private Button btn_sure;
    private ImageView img_history;
    private String station, time;
    private TextView tv_time, tv_station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        selectBusFirstFragment = new SelectBusFirstFragment();
        transaction.replace(R.id.frameLayout_content,selectBusFirstFragment);
        transaction.commit();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void loadData() {}

    @Override
    protected void initViews(Bundle savedInstanceState) {
        linearlayout_return = (LinearLayout)findViewById(R.id.linearlayout_return);
        tvStartingpoint =(TextView)findViewById(R.id.tvStartingpoint);
        tvDestination = (TextView)findViewById(R.id.tvDestination);
        editCommonStation =(RelativeLayout)findViewById(R.id.editCommonStation);
        setCommonStation =(RelativeLayout)findViewById(R.id.setCommonStation);
        btn_sure =(Button)findViewById(R.id.btn_sure);
        img_history=(ImageView)findViewById(R.id.img_history);

        linearlayout_return.setOnClickListener(this);
        tvStartingpoint.setOnClickListener(this);
        tvDestination.setOnClickListener(this);
        editCommonStation.setOnClickListener(this);
        setCommonStation.setOnClickListener(this);
        img_history.setOnClickListener(this);

        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    protected void initVariables() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_return:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;

            case R.id.tvStartingpoint:
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                break;

            case R.id.tvDestination:
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                break;

            case R.id.editCommonStation:
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                break;

            case R.id.setCommonStation:
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                break;
            //暂时做测试按钮
//			case R.id.img_history:
//				FragmentManager fm = getFragmentManager();
//				FragmentTransaction transaction = fm.beginTransaction();
//				if (selectBusFragment == null) {
//					selectBusFragment = new SelectBusFragment();
//				}
//				transaction.replace(R.id.frameLayout_content, selectBusFragment);
//				transaction.commit();
//				btn_sure.setBackgroundResource(R.drawable.confirm);
//				img_checkbox.setBackgroundResource(R.drawable.selectalt64px);
//				break;
            default:
                break;

        }
    }
}
