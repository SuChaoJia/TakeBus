package com.rdc.takebus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rdc.takebus.R;
import com.rdc.takebus.model.adapter.SelectBusAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectBusFragment extends Fragment{
    private ListView bus_lv;  
	private SelectBusAdapter adapter;
	private List<Map<String, String>> data;
	private View view;
	private Context context;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		bus_lv = (ListView)view.findViewById(R.id.bus_lv);
		context = this.getActivity();
		addData();
		adapter = new SelectBusAdapter(data, context);
		Log.e("Context", ""+this.getActivity());
		bus_lv.setAdapter(adapter);
		super.onActivityCreated(savedInstanceState);
	}
	
	private void addData() {
		data = new ArrayList<Map<String,String>>();
		for(int i = 0; i<10; i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("route", "大学城环线1路");
			map.put("time", "约14分钟");
			map.put("station", "途径8站");
			data.add(map);
			Log.e("加载数据", ""+ data);
		}
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
     view = inflater.inflate(R.layout.select_bus_fragment, container,false);
     Log.e("listview", ""+bus_lv);
     return view;
    }				
}
