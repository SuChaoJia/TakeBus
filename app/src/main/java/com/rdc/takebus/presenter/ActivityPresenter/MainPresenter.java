package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.rdc.takebus.R;
import com.rdc.takebus.model.entity.Bus;
import com.rdc.takebus.model.utils.MapUtil;
import com.rdc.takebus.model.utils.MyOrientationUtil;
import com.rdc.takebus.presenter.tbinterface.MapInterface;

import java.util.List;

/**
 * Created by 梦涵 on 2016/5/11.
 */
public class MainPresenter extends BaseActivityPresenter<MapInterface> {
    private MapInterface mapInterface;
    private BaiduMap map;
    private Context context;
    private float mCurrentX;
    private BitmapDescriptor mBusMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_bus);
    private  String[] stations;
    private int flagStartBefore; // 记录起点之前位置
    private int flagEndBefore; // 记录终点之前位置
    private int screenWidth;
    private MapUtil mapUtil;
    private Activity acrivity;
    private TextView[] tv_array_station;
    private ImageView[] img_array_station;

    public MainPresenter(MapInterface mapInterface){
        this.mapInterface = mapInterface;
    }

    public void setContext(Context context){
        this.context = context;
        mapUtil = new MapUtil(context);
    }


    public void initData(){
        stations = new String[] { "中环西路站", "广美生活区", "广大生活区", "广园客运站", "科学中心", "综合商业南区", "广中医" };
        String[] routes = new String[] { "大学城专线4路", "番202路", "大学城环线1路" };
        mapInterface.initStation(stations);
        mapInterface.initRoutes(routes);
    }

    public void initMap(MapView mapView){
        //移除百度地图的logo
        mapView.removeViewAt(1);
        //删除缩放按钮
        View scale = mapView.getChildAt(1);
        scale.setVisibility(View.GONE);
        //删除指南针
        map = mapView.getMap();
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    public void initLocation(MyLocationConfiguration.LocationMode mode, LocationClient mLocationClient,
                             MyOrientationUtil myOrientationUtil, float x){
        mCurrentX = x;
        mode = MyLocationConfiguration.LocationMode.NORMAL;
        // 开启定位图层
        map.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(new MapUtil(context));

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);// 每1秒请求一次
        mLocationClient.setLocOption(option);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        myOrientationUtil = new MyOrientationUtil(context);
        myOrientationUtil.setOnOrientationListener(new MyOrientationUtil.OnOrientationListener() {

            @Override
            public void onOrientationChange(float x) {
                mCurrentX = x;
            }
        });
        // 一开始显示的放大倍数在500m左右
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mapInterface.locationInit(msu);
    }

    public void addBusMarkers(List<Bus> buses){
        map.clear();
        LatLng latlng = null;// 经纬度对象
        Marker marker = null;
        OverlayOptions options;
        for (Bus bus : buses) {
            // 经纬度
            latlng = new LatLng(bus.getLatitude(), bus.getLongtitude());
            // Marker图标,图层高度
            options = new MarkerOptions().position(latlng).icon(mBusMarker).zIndex(5);
            marker = (Marker) map.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bus", bus);
            marker.setExtraInfo(bundle);
        }
        // 把地图回到最后一个地址
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
        mapInterface.addMarkers(msu);
    }

    public void addImageView(ImageView []imageViews, LinearLayout linearLayout, View.OnClickListener listener, int screenWidth) {
        this.screenWidth = screenWidth;
        imageViews = new ImageView[stations.length];
        for (int i = 0; i < stations.length; i++) {
            ImageView img = new ImageView(context);
            img.setId(i);
            img.setOnClickListener(listener);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(screenWidth / 5,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            img.setLayoutParams(linearParams);
            img.setImageResource(R.drawable.circle_default_3_2px);
            if (i == 0) {
                // 标记起点
                flagStartBefore = i;
                img.setImageResource(R.drawable.circle_start_3_2px);
            }
            if (i == 4) {
                // 标记终点
                flagEndBefore = i;
                img.setImageResource(R.drawable.circle_end_3_2px);
            }
            imageViews[i] = img;
            linearLayout.addView(img);
        }
        img_array_station = imageViews;
    }

    // 动态添加文字
    public void addTextView(TextView[] tv_array_station, Activity activity) {
        this.acrivity = activity;
        View view;
        // 构建TextView数组长度
        tv_array_station = new TextView[stations.length];
        for (int i = 0; i < stations.length; i++) {
            // 偶数
            if (i % 2 == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.tv_station_up, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_station);
                tv_array_station[i] = tv;
                tv.setText(stations[i]);
                // 调用XML的颜色文件
                Resources resource = (Resources) activity.getBaseContext().getResources();
                if (i == 0) {

                    ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.bt_green);
                    if (csl != null) {
                        tv.setTextColor(csl);
                    }
                }
                if (i == 4) {
                    ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.bt_pink);
                    if (csl != null) {
                        tv.setTextColor(csl);
                    }
                }
                view.setId(i + 1);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth / 5,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ABOVE, 100);
                if (i != 0)
                    params.addRule(RelativeLayout.RIGHT_OF, i);

                view.setLayoutParams(params);
            }
            // 奇数
            else {
                view = LayoutInflater.from(context).inflate(R.layout.tv_station_down, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_station);
                tv_array_station[i] = tv;
                tv.setText(stations[i]);
                view.setId(i + 1);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth / 5,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, 100);
                params.addRule(RelativeLayout.RIGHT_OF, i);
                view.setLayoutParams(params);
            }
            mapInterface.addView(view);
        }
        this.tv_array_station = tv_array_station;
    }

    public void setStation(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View item = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
        TextView tv_start = (TextView) item.findViewById(R.id.tv_start_station);
        TextView tv_end = (TextView) item.findViewById(R.id.tv_end_station);
        builder.setView(item);
        final Dialog dialog = builder.create();
        dialog.show();
        // 设置Dialog的宽度
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (screenWidth / 4) * 3;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

        tv_start.setOnClickListener(new View.OnClickListener() {

            // 表示点击的是起点站
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                if (flagStartBefore != v.getId())
                    setStation(v, 0);
                dialog.dismiss();
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            // 表示点击的是终点站
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                if (flagEndBefore != v.getId())
                    setStation(v, 1);
                dialog.dismiss();

            }
        });
    }

    // 设置起点终点改变颜色
    public void setStation(View v, int tag) {
        Resources resource = (Resources)acrivity.getBaseContext().getResources();
        if (tag == 0) {

            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.bt_green);
            // 当前TextView更换颜色
            tv_array_station[v.getId()].setTextColor(csl);
            // 原来起点的那个字体设置为黑色
            ColorStateList cslBefore = (ColorStateList) resource.getColorStateList(R.color.bt_black);
            tv_array_station[flagStartBefore].setTextColor(cslBefore);

            img_array_station[v.getId()].setImageResource(R.drawable.circle_start_3_2px);
            img_array_station[flagStartBefore].setImageResource(R.drawable.circle_default_3_2px);
            // 如果在同位置上进行设置，则调换起点和终点
            if (flagEndBefore == v.getId()) {
                ColorStateList cslPink = (ColorStateList) resource.getColorStateList(R.color.bt_pink);
                // 当前TextView更换颜色
                tv_array_station[flagStartBefore].setTextColor(cslPink);
                // 原来起点的那个字体设置为黑色
                img_array_station[flagStartBefore].setImageResource(R.drawable.circle_end_3_2px);
                flagEndBefore = flagStartBefore;
            }
            flagStartBefore = v.getId();

        } else if (tag == 1) {

            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.bt_pink);
            // 当前TextView更换颜色
            tv_array_station[v.getId()].setTextColor(csl);
            // 原来起点的那个字体设置为黑色
            ColorStateList cslBefore = (ColorStateList) resource.getColorStateList(R.color.bt_black);
            tv_array_station[flagEndBefore].setTextColor(cslBefore);

            img_array_station[v.getId()].setImageResource(R.drawable.circle_end_3_2px);
            img_array_station[flagEndBefore].setImageResource(R.drawable.circle_default_3_2px);
            // 如果在同位置上进行设置，则调换起点和终点

            if (flagStartBefore == v.getId()) {
                ColorStateList cslGreen = (ColorStateList) resource.getColorStateList(R.color.bt_green);
                // 当前TextView更换颜色
                tv_array_station[flagEndBefore].setTextColor(cslGreen);
                // 原来起点的那个字体设置为黑色
                img_array_station[flagEndBefore].setImageResource(R.drawable.circle_start_3_2px);
                flagStartBefore = flagEndBefore;
            }
            flagEndBefore = v.getId();
        }
    }


    /**
     * 返回我的位置
     */
    public void returnToMylocation() {
        LatLng ll = new LatLng(mapUtil.getLatitude(), mapUtil.getLongitude());
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
        mapInterface.returnToStation(msu);
    }

}
