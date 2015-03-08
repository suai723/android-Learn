package com.example.mapappdemo.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.activity.MainActivity;
import com.example.mapappdemo.adapter.ActsListViewAdapter;
import com.example.mapappdemo.adapter.DBAdapter;
import com.example.mapappdemo.entity.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.R.integer;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.ClientCertRequest;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class ShowCheckAreaFragment extends Fragment implements OnClickListener,OnItemClickListener,OnItemLongClickListener{
	private Button joinActBtn=null;
	private ListView actLv=null;
	private ActsListViewAdapter actsListViewAdapter = null;
	private DBAdapter db =null;
	private List<Activity> acts=null;
	private List<String> actNames=null;
	public static final int REQUEST_ACT1=3;
	View mFragmentRootView =null;
	Context context =null;
	LinearLayout joinLayout=null;
	LayoutInflater layoutInflater=null;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		mFragmentRootView=inflater.inflate(R.layout.activity_act3, container,false);
		context=getActivity().getBaseContext();
		db=new DBAdapter(context);
		acts=new ArrayList<Activity>();
		joinActBtn=(Button)mFragmentRootView.findViewById(R.id.joinact);
		actLv=(ListView)mFragmentRootView.findViewById(R.id.act3lv);
		actsListViewAdapter = new ActsListViewAdapter(context, acts);
		actLv.setAdapter(actsListViewAdapter);
		actLv.setOnItemClickListener(ShowCheckAreaFragment.this);
		actLv.setOnItemLongClickListener(this);
		joinActBtn = (Button)mFragmentRootView.findViewById(R.id.joinact);
		joinActBtn.setOnClickListener(this);
		db.open();   //第一次进清空
		db.deleteACT(null);
		db.close();
		if (MoreFragment.currentUser !=null) {
			RequestParams params = new RequestParams();
			params.put("userid", MoreFragment.currentUser.getUserId());
			AsyncHttpClient client=new AsyncHttpClient();
			client.post(MainActivity.BASE+"/searcharea",params,new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int i, Header[] Heater, byte[] bytes) {
					String json;
					try {
						json = new String(bytes, "UTF-8");
						Gson gson =new Gson();
						List<Activity> checks=gson.fromJson(json,new TypeToken<List<Activity>>(){}.getType());
						db.open();
						db.deleteACT(null);
						for (Activity act:checks) {
							db.inserAct(act);
						}
						db.close();
						addActsToListView();
						
						Toast.makeText(context, "接收成功",Toast.LENGTH_SHORT).show();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Toast.makeText(context, "连接失败！请检查网络连接",Toast.LENGTH_SHORT).show();
					
				}
			});
		}else {
			Toast.makeText(context, "请登录",Toast.LENGTH_SHORT).show();
		}
		

		
		return mFragmentRootView;
		
	
	}
	

	@Override
	public void onClick(View v) {
		
		if (v.getId()==R.id.joinact && MoreFragment.currentUser !=null) {
			layoutInflater = LayoutInflater.from(getActivity());
			joinLayout= (LinearLayout) layoutInflater.inflate(R.layout.activity_join, null);
			final EditText joinActid=(EditText)joinLayout.findViewById(R.id.joinact);
			new AlertDialog.Builder(getActivity())
						.setTitle("JoinCheckArea")
						.setView(joinLayout)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {					
							@Override
							public void onClick(DialogInterface dialog, int which) {
								RequestParams params = new RequestParams();
								params.put("userid", MoreFragment.currentUser.getUserId());
								params.put("username", MoreFragment.currentUser.getUserName());
								params.put("actid", joinActid.getText().toString());
								params.put("curloc", MoreFragment.currentUser.getCurrent());
								AsyncHttpClient client=new AsyncHttpClient();
								client.post(MainActivity.BASE+"/saveperson",params,new AsyncHttpResponseHandler() {	
									@Override
									public void onSuccess(int i, Header[] header, byte[] bytes) {		
										try {
											String success = new String(bytes,"UTF-8");
											if (success.equals("ok")) {
												Toast.makeText(context, "添加签到地点成功",Toast.LENGTH_SHORT).show();	
											}else {
												Toast.makeText(context, "添加签到地点失败",Toast.LENGTH_SHORT).show();	
											}
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
									
									@Override
									public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
										Toast.makeText(context, "连接失败！请检查网络连接",Toast.LENGTH_SHORT).show();										
									}
								}); 
								Toast.makeText(context, "加入签到地点",Toast.LENGTH_SHORT).show();
							}
						}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(context, "取消",Toast.LENGTH_SHORT).show();
								
							}
						}).show();
							
			
		}else {
			Toast.makeText(context, "请登录",Toast.LENGTH_SHORT).show();
		}
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
		acts.clear();
		acts.addAll(getAllActs());
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

	long lastClickTime;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MainActivity mainActivity = (MainActivity)getActivity();
		MainActivity.dests=acts.get(position);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction =fm.beginTransaction();
    	transaction.replace(android.R.id.tabcontent,mainActivity.checkAreaFragment,"checkAreaFragment");
    	mainActivity.nowFragment=mainActivity.checkAreaFragment;
   	    transaction.commit();
   	    mainActivity.btn1.setChecked(true);
   	 Toast.makeText(context, "短按跳转",Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final int p = position;
		RequestParams params = new RequestParams();
		params.put("userid", MoreFragment.currentUser.getUserId());
		params.put("actid", acts.get(p).getActId());
		AsyncHttpClient client=new AsyncHttpClient();
		
		client.post(MainActivity.BASE+"/delarea",params,new AsyncHttpResponseHandler() {		
			@Override
			public void onSuccess(int i, Header[] Heater, byte[] bytes) {
				String success;
				try {
					success = new String(bytes, "UTF-8");
		
					if (success.equals("ok")) {
						Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
						acts.remove(p);
						actsListViewAdapter.notifyDataSetChanged();
					}else {
						Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(context, "连接失败！请检查网络连接",Toast.LENGTH_SHORT).show();
				
			}
		});
		Toast.makeText(context, "长按删除",Toast.LENGTH_SHORT).show();
		return false;
	}


}
