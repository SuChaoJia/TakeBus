package com.rdc.takebus.presenter.tbinterface;

import android.view.View;

import com.baidu.mapapi.map.MapStatusUpdate;


/**
 * Created by 梦涵 on 2016/5/11.
 */
public interface MapInterface {
    void firstInit();
    void locationInit(MapStatusUpdate msu);
    void initStation(String[] strs);
    void initRoutes(String[] strs);
    void route();
    void addView(View view);
    void addMarkers(MapStatusUpdate msu);//添加覆盖物
    void returnToStation(MapStatusUpdate msu);
}
