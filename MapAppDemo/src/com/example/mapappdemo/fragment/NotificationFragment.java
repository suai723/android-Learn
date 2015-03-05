package com.example.mapappdemo.fragment;

import java.util.List;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.activity.MainActivity;
import com.example.mapappdemo.adapter.PushListViewAdapter;
import com.example.mapappdemo.entity.PushMsg;
import com.example.mapappdemo.util.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NotificationFragment extends Fragment {
	View mFragmentRootView =null;
	ListView pushLv =null;
	public PushListViewAdapter pushAdapter=null;
	private Context context=null;
	private MainActivity mActivity=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFragmentRootView=inflater.inflate(R.layout.activity_act2, container,false);
		context=getActivity().getBaseContext();
		mActivity = (MainActivity)getActivity();
        pushLv=(ListView) mFragmentRootView.findViewById(R.id.act2lv);
        pushAdapter=new PushListViewAdapter(context, Utils.pushs);
		pushLv.setAdapter(pushAdapter);
		pushAdapter.notifyDataSetChanged();
		
		return mFragmentRootView;
	}
	
}
