package com.example.mapappdemo.fragment;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.Destroyable;

import com.baidu.lbsapi.auth.i;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.baidu.platform.comapi.map.h;
import com.baidu.platform.comapi.map.m;
import com.example.mapappdemo.R;
import com.example.mapappdemo.R.drawable;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.activity.CreateCheck;
import com.example.mapappdemo.activity.MainActivity;
import com.example.mapappdemo.activity.SearchAct;

import android.support.v7.app.ActionBarActivity;
import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class CheckAreaFragment extends Fragment implements OnMarkerClickListener,OnMarkerDragListener,OnMapClickListener{
	//地图变量
	public static MapView mapView=null;
	public static BaiduMap mBaidumap = null;
    private Boolean isFirstLoad=true;
    //范围
    private Overlay mCircle=null;
    private static final int DISTANTS=500;//范围半径 m
    
    private LatLng Dest =null;
    private Marker DestMarker=null;
    
    //定位
    LocationClient mLocClient;
    private MyLocationListener myListener = new MyLocationListener();//定位监听器
    private LocationMode mCurrentMode;//设置跟踪模式
    private  boolean isFirstLoc=true;//是否识第一次定位
    private boolean hasLoc=false;
    private BitmapDescriptor mCurrentMarker;

    //poi索引
    private Intent searchIntent=null;
    public static final int REQUEST_SEARCH=1;
    
    //创建活动
    private Intent creatActIntent=null;
    public static final int REQUEST_CREATACT=2;
    
    //标注
    private LatLng markPosition=null;
    private MyLocationData userlocData=null;
    private Button checkButton=null;
    
    
    public  Handler handler=null;
    private InfoWindow mInfoWindow  =null;
    
    private View mFragmentRootView =null;
    private Context context=null;
    private LatLng CheckPoint=null;
    
    private boolean hasMarked = false;
    
    
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	mFragmentRootView=inflater.inflate(R.layout.activity_act1, container,false);
    	context=getActivity().getBaseContext();
    	BaiduMapInit();    
        LocInit();
        ListenerInit();
        //跳转
        searchIntent= new Intent(context,SearchAct.class);
        creatActIntent = new Intent(context,CreateCheck.class);
        Button searchBtn = (Button)mFragmentRootView.findViewById(R.id.searchbtn);
        checkButton =(Button)mFragmentRootView.findViewById(R.id.CheckBtn);
        checkButton.setEnabled(false);
//        searchBtn.setEnabled(false);禁用按钮
        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivityForResult(CheckAreaFragment.this.searchIntent,REQUEST_SEARCH);
            }
        });
        
        checkButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Checked!!!!", Toast.LENGTH_SHORT).show();
			}
		});
        
         handler = new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		super.handleMessage(msg);
        		if (msg.arg1==0) {
					checkButton.setEnabled(true);
				}else {
					checkButton.setEnabled(false);
				}
        	}
        };
        
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("timer_run");
				if (hasLoc) {
					if(MainActivity.dests.getActId()!=null){
						String loc=MainActivity.dests.getLoc();
						LatLng CheckPoint = new LatLng(Double.valueOf(loc.split(":")[0]) , Double.valueOf(loc.split(":")[1])); 
						LatLng userPoint = new LatLng(userlocData.latitude, userlocData.longitude);
						if(SpatialRelationUtil.isCircleContainsPoint(CheckPoint, DISTANTS, userPoint)){
							if (handler!=null) {
								Message msg= handler.obtainMessage();
								msg.arg1=0;
								handler.sendMessage(msg);
							}
						}else {
							Message msg= handler.obtainMessage();
							msg.arg1=1;
							handler.sendMessage(msg);
						}
					}
				}
			}
		},3000,5000); 
    	return mFragmentRootView;
    }
    
    public void BaiduMapInit() {
        mapView = (MapView)mFragmentRootView.findViewById(R.id.bmapView);
        mBaidumap = mapView.getMap();
        mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        UiSettings mUisetting = mBaidumap.getUiSettings();//隐藏指南针图标
        mUisetting.setCompassEnabled(false);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
	}
    
	//监听器初始化
	private void ListenerInit(){
		//地图点击监听
        mBaidumap.setOnMapClickListener(this);
        //点击marker监听函数
        mBaidumap.setOnMarkerClickListener(this);   
		//拖拽marker监听函数
		mBaidumap.setOnMarkerDragListener(this);
	}
	
	//创建标注和范围
	private void CreateCheckArea(LatLng point){
		   OverlayOptions circleOption= new CircleOptions()
           .center(point)
           .radius(DISTANTS)
           .fillColor(0xAAFFFF00)
           .stroke(new Stroke(5,0xAA00FF00));
		   mCircle=mBaidumap.addOverlay(circleOption);
	}
	
	private Marker CreateCheckPoint(LatLng point,boolean draggable){
		BitmapDescriptor Destbitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
							OverlayOptions markOPtion=new MarkerOptions().position(point)
												.icon(Destbitmap)
												.zIndex(9)
												.draggable(draggable);
		
		return DestMarker=(Marker)mBaidumap.addOverlay(markOPtion);
		
	}
	
	private void DestroyCheckArea(){
		if (DestMarker==null && mCircle==null) 
			return;
		DestMarker.remove();
		mCircle.remove();
	}
	
	
	//定位初始化
    public void LocInit(){
        mCurrentMode=LocationMode.NORMAL;//定位涂层显示方式
        mCurrentMarker= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);//设置定位图标
        mBaidumap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode,false,mCurrentMarker));//true是否获取方向信息
        mBaidumap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(getActivity().getApplicationContext());//一定要获取全局上下文
        mLocClient.registerLocationListener(myListener);//注册定位监听器
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型
        option.setScanSpan(5000);// 设置扫描间隔
        option.setIsNeedAddress(true);//结果集是否包含地址
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位
        option.setNeedDeviceDirect(true);//设置结果集包含手机方向
        mLocClient.setLocOption(option);
        mLocClient.start();//启动定位
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onStart() {
        if (MainActivity.dests.getActId()!=null) {
			String loc=MainActivity.dests.getLoc();
			LatLng CheckPoint = new LatLng(Double.valueOf(loc.split(":")[0]) , Double.valueOf(loc.split(":")[1])); 
			BitmapDescriptor Destbitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
			OverlayOptions markOPtion=new MarkerOptions().position(CheckPoint)
								.icon(Destbitmap)
								.zIndex(9)
								.draggable(false);
			mBaidumap.addOverlay(markOPtion);
		}
		super.onStart();
	}
    @Override
	public void onPause() {
        
        System.err.println("onPause");
        DestroyCheckArea();
        mapView.onPause();
        super.onPause();
    }

    @Override
	public void onDestroy() {
        
        System.err.println("onDestroy");
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
	public void onResume() {
    	
        
        System.err.println("onResume");
        if (!isFirstLoad && markPosition!=null) {
          CreateCheckArea(markPosition);
          CreateCheckPoint(markPosition,true);
		}

        
        mapView.onResume();
        super.onResume();
    }
    
   
    
    private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
		        if (location == null || mapView == null)
	          return;
	     userlocData = new MyLocationData.Builder()
	              .accuracy(location.getRadius())
	                      // 此处设置开发者获取到的方向信息，顺时针0-360
	              .latitude(location.getLatitude())
	              .longitude(location.getLongitude()).build();
	      mBaidumap.setMyLocationData(userlocData);
	      System.out.println(location.getLatitude()+" "+location.getLongitude());
	      if (isFirstLoc) {
	          isFirstLoc = false;//第一次定位标示
	          isFirstLoad =false;//第一次启动标示
	          hasLoc=true;//已定位标示
	          LatLng ll = new LatLng(location.getLatitude(),
	                  location.getLongitude());
	          MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
	          mBaidumap.animateMapStatus(u);
	      }
			
		}	
    }

	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		markPosition= marker.getPosition();//保留标注位置
		   CreateCheckArea(markPosition);
		
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
	       if (mCircle !=null)
               mCircle.remove();
			if (mInfoWindow!=null) {
				mInfoWindow=null;
				mBaidumap.hideInfoWindow();
			}
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Button button = new Button(getActivity().getApplicationContext());
		button.setText("CreateAct");
		button.setTextColor(Color.parseColor("#000000"));
		button.setTextSize((float)15);
		button.setBackgroundResource(R.drawable.popup);  
		//定义用于显示该InfoWindow的坐标点  
		LatLng pt = marker.getPosition();
		//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量 
		mInfoWindow = new InfoWindow(button, pt, -47);  
		//显示InfoWindow  
		mBaidumap.showInfoWindow(mInfoWindow);

		Bundle info = marker.getExtraInfo();//如果绑定了数据直接传绑定的数据 没绑定则新建
		if (info==null) {
			LatLng point =marker.getPosition();
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", point.latitude);
			bundle.putDouble("lon", point.longitude);
			bundle.putInt("state", 0);
			creatActIntent.putExtras(bundle);
		}else {
			info.putInt("state", 1);
			creatActIntent.putExtras(info);
		}
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("infowindow btn click!!!!!!");
				//跳转创建活动页面
				startActivityForResult(CheckAreaFragment.this.creatActIntent,REQUEST_CREATACT);
			}
		});
		return true;
	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		markPosition=point;		
		System.out.println("mapClick");
		if (DestMarker==null) {
			CreateCheckPoint(point,true);	
			CreateCheckArea(point);
		}else {
			DestroyCheckArea();
			CreateCheckPoint(point,true);
			CreateCheckArea(point);
		}
		if (mInfoWindow!=null) {
			mInfoWindow=null;
			mBaidumap.hideInfoWindow();
		}
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
    
    

}
