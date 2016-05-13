package com.rdc.takebus.model.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {
	static List<String> list;

	public static List<String> parseJson(String response) {
		list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			JSONObject object = jsonArray.optJSONObject(0);
			JSONArray array = object.getJSONArray("lines");
			int i = 0;
			while (!array.isNull(i)) {
				list.add(array.getString(i++));
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
