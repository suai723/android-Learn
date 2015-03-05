package com.example.mapappdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.activity.MainActivity;
import com.example.mapappdemo.adapter.ActsListViewAdapter;
import com.example.mapappdemo.adapter.DBAdapter;
import com.example.mapappdemo.entity.Activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.R.integer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShowCheckAreaFragment extends Fragment implements OnClickListener,OnItemClickListener{
	private Button joinActBtn=null;
	private ListView actLv=null;
	private ActsListViewAdapter actsListViewAdapter = null;
	private DBAdapter db =null;
	private List<Activity> acts=null;
	private List<String> actNames=null;
	
	public static final int REQUEST_ACT1=3;
	View mFragmentRootView =null;
	Context context =null;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		mFragmentRootView=inflater.inflate(R.layout.activity_act3, container,false);
		context=getActivity().getBaseContext();
		db=new DBAdapter(context);
		acts=getAllActs();
		joinActBtn=(Button)mFragmentRootView.findViewById(R.id.joinact);
		actLv=(ListView)mFragmentRootView.findViewById(R.id.act3lv);
//		arrayAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		actsListViewAdapter = new ActsListViewAdapter(context, acts);
		actLv.setAdapter(actsListViewAdapter);
		actLv.setOnItemClickListener(this);
		return mFragmentRootView;
	}
	

	@Override
	public void onClick(View v) {

	}
	
	public List<Activity> getAllActs(){
		List<Activity> list = new ArrayList<Activity>();
		db.open();
		Cursor cursor = db.getAllActs();
		if (cursor.getCount()!=0) {
			while(cursor.moveToNext()){
				String actName = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_NAME));
				String rowId = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ROWID));
				String addr = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_ADDR));
				String loc = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_LOC));
				String actId = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_ID));
				String startTime =cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_START_TIME));
				String startDay =cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_DAY));
				String endTime= cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_END_TIME));
				String hostUserName=cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_HOST_USERNAME));
				String hostUserId = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_HOST_USERID));
				String desc = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ACT_DESC));
				
				Activity act = new Activity(actName, rowId, actId, addr, loc, startTime, endTime, null, desc, null, hostUserName, hostUserId, startDay, "Y");
				list.add(act);
			}
		}
		db.close();
		return list;
		
	}
	
	public void addActsToListView() {
		acts=getAllActs();
		actsListViewAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		addActsToListView();
		super.onResume();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MainActivity mainActivity = (MainActivity)getActivity();
//		mainActivity.currentCheckArea=acts.get(position);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction =fm.beginTransaction();
    	transaction.replace(android.R.id.tabcontent,mainActivity.checkAreaFragment,"checkAreaFragment");
//    	mainActivity.nowFragment=mainActivity.checkAreaFragment;
    	transaction.commit();
		System.out.println("onItemClick!!!!!");
	}
}
