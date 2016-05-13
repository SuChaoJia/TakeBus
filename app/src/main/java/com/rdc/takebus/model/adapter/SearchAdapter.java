package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter{

	private List<Map<String, Object>> list;
	private Context context;
	
    public SearchAdapter(Context context){
		this.context = context;
	}

	public void setData(List<Map<String, Object>> list){
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void moreData(List<Map<String, Object>>list){
		setData(list);
		notifyDataSetChanged();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.search_item,null);
		    holder.tv_station = (TextView)convertView.findViewById(R.id.tv_station);
		    holder.tv_place = (TextView)convertView.findViewById(R.id.tv_place);
		    holder.img_history=(ImageView)convertView.findViewById(R.id.img_history);
		    convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_station.setText((String)list.get(position).get("station"));
		holder.tv_place.setText((String)list.get(position).get("place"));
	    holder.img_history.setBackgroundResource((Integer)list.get(position).get("img"));
		return convertView;
	}
     class ViewHolder{
    	 TextView tv_station;
    	 TextView tv_place;
    	 ImageView img_history;
     }
}