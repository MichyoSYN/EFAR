package com.example.efar;

import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "NewApi", "ValidFragment" })
public class ContactFragment extends ListFragment {
	
	private static final String[] CONTACT = new String[] {
		Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	
	private static final int NAME_INDEX = 0;
	private static final int NUMBER_INDEX = 1;
	private static final int PHOTO_INDEX = 2;
	private static final int ID_INDEX = 3;
	
	private ArrayList<String> contact_names = new ArrayList<String>();
	private ArrayList<String> contact_numbers = new ArrayList<String>();
	private ArrayList<Bitmap> contact_photos = new ArrayList<Bitmap>();
	
	@SuppressWarnings("unused")
	private ListView listview = null;
	private ListAdapter listadapter = null;
	private Context context = null;
	
	@SuppressLint("ValidFragment")
	public ContactFragment(Context c) {
		this.context = c;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content_contact = inflater.inflate(R.layout.content_contact, container, false);
		return content_contact;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listview = this.getListView();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(context == null) {
			Log.v("mylog", "context == null");
		} else {
			Log.v("mylog", "context != null");
		}
		
//		listview = this.getListView();
		getContacts();
		listadapter = new ContactListAdapter();
		setListAdapter(listadapter);
		
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> adapterview, View view, int position,
//					long id) {
//				Intent dial_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact_numbers.get(position)));
//				startActivity(dial_intent);
//			}
//			
//		});
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent dial_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact_numbers.get(position)));
		startActivity(dial_intent);
	}
	
	private void getContacts() {
		ContentResolver content_resolver = context.getContentResolver();
		Cursor cursor = content_resolver.query(Phone.CONTENT_URI, CONTACT, null, null, null);
		
		
		if(cursor != null) {
			while(cursor.moveToNext()) {
				String number = cursor.getString(NUMBER_INDEX);
				if(TextUtils.isEmpty(number))
					continue;
				String name = cursor.getString(NAME_INDEX);
				Long id = cursor.getLong(ID_INDEX);
				Long photo_id = cursor.getLong(PHOTO_INDEX);
				Bitmap photo = null;
				if(photo_id > 0) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
					InputStream inputstream = ContactsContract.Contacts.openContactPhotoInputStream(content_resolver, uri);
					photo = BitmapFactory.decodeStream(inputstream);
				} else {
					photo = BitmapFactory.decodeResource(getResources(), R.drawable.contact);
				}
				contact_names.add(name);
				contact_numbers.add(number);
				contact_photos.add(photo);
			}
			cursor.close();
		}
	}
	
	class ContactListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contact_names.size();
		}
		
		public boolean areAllItemEnabled() {
			return false;
		}

		@Override
		public Object getItem(int positon) {
			// TODO Auto-generated method stub
			return positon;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewgroup) {
			// TODO Auto-generated method stub
			ViewHolder viewholder = null;
			
			if(null == view) {
				view = LayoutInflater.from(context).inflate(R.layout.listitem_contact, null);
				viewholder = new ViewHolder();
				viewholder.photo = (ImageView) view.findViewById(R.id.contact_photo);
				viewholder.name = (TextView) view.findViewById(R.id.contact_name);
				viewholder.number = (TextView) view.findViewById(R.id.contact_number);
			} else {
				viewholder = (ViewHolder) view.getTag();
			}
			
			viewholder.name.setText(contact_names.get(position));
			viewholder.number.setText(contact_numbers.get(position));
			viewholder.photo.setImageBitmap(contact_photos.get(position));
			
			return view;
		}

	}
	
	public static class ViewHolder {
		public ImageView photo;
		public TextView name;
		public TextView number;
	}
	
}
