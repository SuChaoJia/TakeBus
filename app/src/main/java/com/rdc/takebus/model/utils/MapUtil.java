package com.rdc.takebus.model.utils;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.rdc.takebus.view.CustomView.CustomToast;

/**
 * Created by 梦涵 on 2016/5/11.
 */
public class MapUtil implements BDLocationListener {
    private Context context;
    private float mCurrentX;
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BaiduMap mBaiduMap;
    private double longiTude;
    private double latitude;
    private boolean isFirstLoc;
    private MyOrientationUtil myOrientationUtil;


    public MapUtil(Context context) {
        this.context = context;
    }

    public void setX(float currentX){
        mCurrentX = currentX;
    }

    public void setMode(MyLocationConfiguration.LocationMode mode){
        mCurrentMode = mode;
    }

    public void setMap(BaiduMap map){
        mBaiduMap = map;
    }

    public void setLongitude(double longiTude){
        this.longiTude = longiTude;
    }

    public double getLongitude(){
        return longiTude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setIsFirst(boolean isFirstLoc){
        this.isFirstLoc = isFirstLoc;
    }




    @Override
    public void onReceiveLocation(BDLocation location) {
        MyLocationData data = new MyLocationData.Builder().direction(mCurrentX)// 方向设置
                .accuracy(location.getRadius())// 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(location.getLatitude())// 纬度
                .longitude(location.getLongitude()).build();// 经度
        // 自定义marker
        // mCurrentMarker =
        // BitmapDescriptorFactory.fromResource(R.drawable.pointer_location);
        mCurrentMarker = null;
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
        mBaiduMap.setMyLocationData(data);
        setLongitude(location.getLongitude());
        setLatitude(location.getLatitude());
        // 第一次定位时候，把地图转移到中心点
        if (isFirstLoc) {
            isFirstLoc = false;
            // 经度、纬度
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(msu);
            CustomToast.showToast(context, location.getAddrStr(), Toast.LENGTH_SHORT);
       }
    }
}

