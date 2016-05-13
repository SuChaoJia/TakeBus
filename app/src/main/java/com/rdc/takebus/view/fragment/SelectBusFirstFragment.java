package com.rdc.takebus.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;

public class SelectBusFirstFragment extends Fragment{
    private View view;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.select_bus_fragment_first,container,false);
    	return view;
    }
}
