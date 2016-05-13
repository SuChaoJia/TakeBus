package com.rdc.takebus.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.rdc.takebus.R;
import com.rdc.takebus.model.utils.AppConstance;
import com.rdc.takebus.model.utils.MyOrientationUtil;
import com.rdc.takebus.presenter.ActivityPresenter.MainPresenter;
import com.rdc.takebus.presenter.tbinterface.MapInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

public class MainActivity extends BaseActivity<MapInterface, MainPresenter> implements
        MapInterface {
    private MapView mMapView = null;
    private ImageView ivSwapSubway;
    private ImageView ivUserMsg;

    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BaiduMap mBaiduMap;
    private Button ivMyLocation;
    private RelativeLayout layoutRoute;
    private Context context;
    private MyOrientationUtil myOrientationUtil;
    private float mCurrentX;
    private LinearLayout linearLayoutRoute;
    // 覆盖物相关
    private BitmapDescriptor mBusMarker;
    private int screenWidth; // 屏幕宽度
    // drawerlayout相关
    private DrawerLayout drawer;
    private RadioButton selfdate, fingerprint, config, ring, about, history, wallet;
    private ScrollView drawerScrollView;
    private HorizontalScrollView station;
    private Button btn_up;
    private TextView[] tv_array_station; // 存放记录位置的数组
    private ImageView[] img_array_station;// 存放记录位置的数组
    private int flagStartBefore; // 记录起点之前位置
    private int flagEndBefore; // 记录终点之前位置
    private LinearLayout linearLayoutImage; // 放入ImageViewStation的LinearLayout
    private  FrameLayout frameLayout;
    private RelativeLayout rl;
    private LinearLayout l1, l2;
    private LayoutInflater inflater;
    private View view;
    // 选择路线相关
    private Button btn_route;
    private String[] stations;
    private String[] routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mPresenter.initLocation(mCurrentMode, mLocationClient, myOrientationUtil ,mCurrentX);
        mPresenter.addBusMarkers(AppConstance.Buses);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void loadData() {
        mPresenter.initData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        selfdate = (RadioButton) findViewById(R.id.selfdate);
        fingerprint = (RadioButton) findViewById(R.id.fingerprint);
        config = (RadioButton) findViewById(R.id.config);
        ring = (RadioButton) findViewById(R.id.ring);
        about = (RadioButton) findViewById(R.id.about);
        history = (RadioButton) findViewById(R.id.history);
        wallet = (RadioButton) findViewById(R.id.wallet);
        drawerScrollView = (ScrollView) findViewById(R.id.drawerScrollView);
        ivUserMsg = (ImageView) findViewById(R.id.ivUserMsg);
        ivMyLocation = (Button) findViewById(R.id.ivMyLocation);
        layoutRoute = (RelativeLayout) findViewById(R.id.layout_route);
        station = (HorizontalScrollView) findViewById(R.id.btStation);
        btn_up = (Button) findViewById(R.id.btn_up);
        linearLayoutRoute = (LinearLayout) findViewById(R.id.layout_more_route);
        ivMyLocation.setOnClickListener(this);
        drawerScrollView.setOnClickListener(this);
        ivUserMsg.setOnClickListener(this);
        selfdate.setOnClickListener(this);
        fingerprint.setOnClickListener(this);
        config.setOnClickListener(this);
        ring.setOnClickListener(this);
        about.setOnClickListener(this);
        history.setOnClickListener(this);
        wallet.setOnClickListener(this);

        btn_route = (Button) findViewById(R.id.btn_route);
        btn_route.setOnClickListener(this);

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_select_route, null);
        l1 = (LinearLayout) view.findViewById(R.id.l1);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        ivSwapSubway = (ImageView) findViewById(R.id.ivSwap);
        ivSwapSubway.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mPresenter.initMap(mMapView);

        linearLayoutImage = (LinearLayout) findViewById(R.id.linearLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        //frameLayout.setId(100);
        rl = (RelativeLayout) findViewById(R.id.rl_station);
    }

    @Override
    protected void initVariables() {
        context = this;
        mPresenter.setContext(context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() >= 0 && v.getId() < stations.length) {
            mPresenter.setStation(v);
        }
        switch (v.getId()) {
            case R.id.ivUserMsg:
                CustomToast.showToast(this, "待接入消息界面", Toast.LENGTH_LONG);
                break;
            case R.id.ivMyLocation:// 返回我的位置
                mPresenter.returnToMylocation();
                break;

            case R.id.ivSwap:
                CustomToast.showToast(this, "地铁系统暂未开通", Toast.LENGTH_SHORT);
                break;
            case R.id.selfdate:
                CustomToast.showToast(this, "待接入个人资料界面", Toast.LENGTH_SHORT);
                break;
            case R.id.fingerprint:
                CustomToast.showToast(this, "待接入卡类认证界面", Toast.LENGTH_SHORT);
                break;
            case R.id.config:
               CustomToast.showToast(this, "待接入设置界面", Toast.LENGTH_SHORT);
                break;
            case R.id.ring:
               CustomToast.showToast(this, "待接入通知界面", Toast.LENGTH_SHORT);
                break;
            case R.id.about:
               CustomToast.showToast(this, "待接入关于界面", Toast.LENGTH_SHORT);
                break;
            case R.id.history:
               CustomToast.showToast(this, "待接入历史账单界面", Toast.LENGTH_SHORT);
                break;
            case R.id.wallet:
               CustomToast.showToast(this, "待接入支付绑定界面", Toast.LENGTH_SHORT);
                break;
            case R.id.l1:
                // 获取起点
                Intent intent1 = new Intent(this, SearchActivity.class);
                intent1.putExtra("station", "起点站");
                startActivityForResult(intent1, 1);
                setPendingTransition();
                break;
            case R.id.l2:
                // 获取终点
                Intent intent2 = new Intent(this, SearchActivity.class);
                intent2.putExtra("station", "终点站");
                startActivityForResult(intent2, 2);
                setPendingTransition();
                break;
            case R.id.btn_route:
                startActivity(new Intent(this, SelectRouteActivity.class));
                finish();
                break;
            default:
                break;
        }
        drawer.closeDrawer(drawerScrollView);
    }

    @Override
    public void firstInit() {

    }

    @Override
    public void locationInit(MapStatusUpdate msu) {
         mBaiduMap.setMapStatus(msu);
    }

    //初始化车站信息
    @Override
    public void initStation(String[] strs) {
        stations = strs;
    }

    //初始化路线信息
    @Override
    public void initRoutes(String[] strs) {
        routes = strs;
    }

    //动态添加图标
    @Override
    public void route() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;//屏幕宽度（像素）
        mPresenter.addImageView(img_array_station, linearLayoutImage, this, screenWidth);
        mPresenter.addTextView(tv_array_station,this);
    }

    @Override
    public void addView(View view) {
        rl.addView(view);
    }

    @Override
    public void addMarkers(MapStatusUpdate msu) {
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public void returnToStation(MapStatusUpdate msu) {
        mBaiduMap.animateMapStatus(msu);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        // 开启方向传感器
        myOrientationUtil.star();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
        if (mLocationClient.isStarted())
            mLocationClient.stop();
        // 关闭传感器
        myOrientationUtil.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
