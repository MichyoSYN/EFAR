package com.mobile.efar;

import com.example.efar.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class SettingFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content_setting = inflater.inflate(R.layout.content_setting, container, false);
		return content_setting;
	}
	
}
