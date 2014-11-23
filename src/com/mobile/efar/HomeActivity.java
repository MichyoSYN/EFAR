package com.mobile.efar;

import com.example.efar.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {
	private EventFragment event_fragment;
	private RecordFragment Record_fragment;
	private ContactFragment contact_fragment;
	private SettingFragment setting_fragment;
	
	private FrameLayout container;
	
	private ImageButton imagebutton_home;
	private ImageButton imagebutton_location;
	private ImageButton imagebutton_contact;
	private ImageButton imagebutton_setting;
	
	private TextView title;
	
	private FragmentManager fragment_manager;
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initialize();
		fragment_manager = getFragmentManager();
		selectContent(0);
	}
	
	private void initialize() {
		container = (FrameLayout) findViewById(R.id.framelayout_main);
		
		title = (TextView) findViewById(R.id.textview_title);
		
		imagebutton_home = (ImageButton) findViewById(R.id.button_home);
		imagebutton_location = (ImageButton) findViewById(R.id.button_location);
		imagebutton_contact = (ImageButton) findViewById(R.id.button_contact);
		imagebutton_setting = (ImageButton) findViewById(R.id.button_setting);
		
		imagebutton_home.setOnClickListener(this);
		imagebutton_location.setOnClickListener(this);
		imagebutton_contact.setOnClickListener(this);
		imagebutton_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		case R.id.button_home:
			selectContent(0);
			break;
		case R.id.button_location:
			selectContent(1);
			break;
		case R.id.button_contact:
			selectContent(2);
			break;
		case R.id.button_setting:
			selectContent(3);
			break;
		default:
			break;
		}

	}
	
	private void clearContent() {
		// clear all color and state to the very beginning.
	}
	
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction ft) {
		//hide all fragments in case there are too many fragments in same window.
		if(event_fragment != null) {
			ft.hide(event_fragment);
		}
		
		if(Record_fragment != null) {
			ft.hide(Record_fragment);
		}
		
		if(contact_fragment != null) {
			ft.hide(contact_fragment);
		}
		
		if(setting_fragment != null) {
			ft.hide(setting_fragment);
		}
	}
	
	@SuppressLint("NewApi")
	private void selectContent(int i) {
		clearContent();
		FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();
		hideFragments(fragment_transaction);
		Intent intent = null;
		
		switch(i) {
		case 0:
			title.setText("HOME");
			if(event_fragment == null) {
				event_fragment = new EventFragment();
				fragment_transaction.add(container.getId(), event_fragment);
			} else {
				fragment_transaction.show(event_fragment);
			}
			break;
		case 1:
			title.setText("LOCATION");
			if(Record_fragment == null) {
				Record_fragment = new RecordFragment();
				fragment_transaction.add(container.getId(), Record_fragment);
			} else {
				fragment_transaction.show(Record_fragment);
			}
			break;
		case 2:
			title.setText("CONTACT");
			if(contact_fragment == null) {
				contact_fragment = new ContactFragment(this);
				fragment_transaction.add(container.getId(),contact_fragment);
			} else {
				fragment_transaction.show(contact_fragment);
			}
			break;
		case 3:
			title.setText("SETTING");
			if(setting_fragment == null) {
				setting_fragment = new SettingFragment();
				fragment_transaction.add(container.getId(), setting_fragment);
			} else {
				fragment_transaction.show(setting_fragment);
			}
			break;
		default:
			break;
		}
		
		fragment_transaction.commit();
	}
}
