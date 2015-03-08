package com.example.mapappdemo.activity;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.platform.comapi.map.m;
import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.R.menu;
import com.example.mapappdemo.adapter.PushListViewAdapter;
import com.example.mapappdemo.entity.Activity;
import com.example.mapappdemo.entity.PushMsg;
import com.example.mapappdemo.fragment.CheckAreaFragment;
import com.example.mapappdemo.fragment.ShowCheckAreaFragment;
import com.example.mapappdemo.fragment.MoreFragment;
import com.example.mapappdemo.fragment.NotificationFragment;
import com.example.mapappdemo.util.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;


@SuppressLint("NewApi")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{

    private TabHost mTabHost;
    

    public Fragment nowFragment =null;
    public Activity currentCheckArea=null;

    public CheckAreaFragment checkAreaFragment =null;
    public NotificationFragment notificationFragment = null;
    public ShowCheckAreaFragment showCheckAreaFragment =null;
    public MoreFragment moreFragment =null;
    
    public ListView pushLv= null;
	public PushListViewAdapter pushAdapter = null;
	public AsyncHttpClient client=null;
	public static Activity dests=new Activity();
	
	public RadioButton btn1;
	public RadioButton btn2;
	public RadioButton btn3;
	public RadioButton btn4;
	
	public static final String BASE="http://192.168.199.140:8888/MapAppServer";
   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn1=((RadioButton)findViewById(R.id.radio_button1));
        btn2=((RadioButton)findViewById(R.id.radio_button2));
        btn3=((RadioButton)findViewById(R.id.radio_button3));
        btn4=((RadioButton)findViewById(R.id.radio_button4));
        
        btn1.setOnCheckedChangeListener(this);
        btn2.setOnCheckedChangeListener(this);
        btn3.setOnCheckedChangeListener(this);
        btn4.setOnCheckedChangeListener(this);

        setDefaultFragment();
        
     // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
        PushManager.enableLbs(getApplicationContext());
     // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(MainActivity.this, "api_key"));
        //设置云推送标签
        List<String> tags = new ArrayList<String>(Arrays.asList("t1","t2"));
        PushManager.setTags(getApplicationContext(), tags);
        
        //App初始化连接服务器
        AsyncHttpClient client= new AsyncHttpClient();
        client.get(BASE+"/init", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String success = null;
                try {
                    success = new String(bytes, "UTF-8");        
                    Toast.makeText(getBaseContext(), success, Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            	Toast.makeText(getBaseContext(), "初始化失败 请检查网络"+String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });

    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		 FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction =fm.beginTransaction();
		if (isChecked){
            switch(buttonView.getId()){
                case R.id.radio_button1:
                	if (checkAreaFragment==null) {
						checkAreaFragment=new CheckAreaFragment();
					}
                	transaction.replace(android.R.id.tabcontent,checkAreaFragment,"checkAreaFragment");
                	nowFragment = checkAreaFragment;
                    break;
                case R.id.radio_button2:
                	if (notificationFragment==null) {
						notificationFragment=new NotificationFragment();
					}
                	transaction.replace(android.R.id.tabcontent,notificationFragment,"notificationFragment");
                	nowFragment = notificationFragment;
                    break;
                case R.id.radio_button3:
                	if (showCheckAreaFragment==null) {
						showCheckAreaFragment=new ShowCheckAreaFragment();
					}
                	transaction.replace(android.R.id.tabcontent,showCheckAreaFragment,"showCheckAreaFragment");
                	nowFragment=showCheckAreaFragment;
                    break;
                case R.id.radio_button4:
                	if (moreFragment==null) {
						moreFragment=new MoreFragment();
					}
                	transaction.replace(android.R.id.tabcontent,moreFragment,"moreFragment");
                	nowFragment=moreFragment;
                    break;
            }
            transaction.commit();
        }
	}
	
	public void setDefaultFragment(){
		checkAreaFragment = new CheckAreaFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction =fm.beginTransaction();
    	transaction.replace(android.R.id.tabcontent,checkAreaFragment,"checkAreaFragment");
    	nowFragment=checkAreaFragment;
    	transaction.commit();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "MainAct", Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	Map<String, String> caches = Utils.logStringCache;
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction =fm.beginTransaction();
    	if (!caches.isEmpty()) {
        	if (caches.get("type").equals("message")) {
        		PushMsg msg  = new PushMsg();
        		msg.setType("message");
        		msg.setLevel(caches.get("level")==null?"普通消息":caches.get("level")+"级消息");
        		msg.setContent(caches.get("message"));
        		msg.setTitle(null);
        		Utils.pushs.add(msg);
        		Toast.makeText(getBaseContext(), "Message!!!!", Toast.LENGTH_SHORT).show();
    		}else if (caches.get("type").equals("notify")) {
    			PushMsg msg  = new PushMsg();
        		msg.setType("notify");
        		msg.setLevel(caches.get("level")==null?"普通通知":caches.get("level")+"级通知");
        		msg.setContent(caches.get("description"));
        		msg.setTitle(caches.get("title"));
        		Utils.pushs.add(msg);
    			Toast.makeText(getBaseContext(), "Notification!!!!", Toast.LENGTH_SHORT).show();
    		}
        	if (notificationFragment!=null) {
        		notificationFragment.pushAdapter.notifyDataSetChanged();
			}else {
				notificationFragment=new NotificationFragment();
			}
        	transaction.add(android.R.id.tabcontent,notificationFragment,"notificationFragment");
		}
    	super.onNewIntent(intent);
    }
    
    
    
    
}
