package com.example.mapappdemo;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class MyApplication extends FrontiaApplication  {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		
	}
}
