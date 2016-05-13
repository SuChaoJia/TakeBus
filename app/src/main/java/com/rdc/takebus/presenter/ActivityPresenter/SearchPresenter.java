package com.rdc.takebus.presenter.ActivityPresenter;

import com.rdc.takebus.R;
import com.rdc.takebus.presenter.tbinterface.ListViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class SearchPresenter extends BaseActivityPresenter<ListViewInterface>{
    private ListViewInterface listViewInterface;
    private List<Map<String, Object>> list = new ArrayList<>();

    public SearchPresenter(ListViewInterface listViewInterface) {
        this.listViewInterface = listViewInterface;
    }

    //初始化数据
    public void initData(){
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("station", "中环西路站");
            map.put("place", "广州大学城中环西路南");
            if(i % 3 == 0){
                map.put("img", R.drawable.historylist64px);
            }
            else{
                map.put("img", R.drawable.location_grey64px);
            }
            list.add(map);

        }
        listViewInterface.addData(list);
    }

    //加载更多数据
    public void loadData(){
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("station", "中环西路站");
            map.put("place", "广州大学城中环西路南");
            map.put("img",R.drawable.location_grey64px);
            list.add(map);
        }
        listViewInterface.moreData(list);
    }
}
