package com.example.mapappdemo.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.R.menu;
import com.example.mapappdemo.adapter.DBAdapter;
import com.example.mapappdemo.entity.User;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;


@SuppressLint("NewApi")
public class CreateCheck extends Activity implements OnClickListener,OnDateChangedListener,OnTimeChangedListener{
	
	private Button createBtn = null;
	private Button cancelBtn=null;
	private EditText actId=null;
	private EditText actName=null;
	private EditText address=null;
	private EditText startDay=null;
	private EditText startTime=null;
	private EditText endTime=null;
	private EditText actDesc=null;
	private AlertDialog ad=null;
	
	private DatePicker datePicker=null;
	private TimePicker timePicker=null;
	
	private String day=null;
	private String time=null;
	
	private com.example.mapappdemo.entity.Activity act = null;
	
	private Intent i =null;
	DBAdapter  db=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		i=getIntent();
		db=new DBAdapter(getBaseContext());
		createBtn=(Button)findViewById(R.id.createsubmit);
		cancelBtn=(Button)findViewById(R.id.createcancle);
		actId=(EditText)findViewById(R.id.actid);
		actName=(EditText)findViewById(R.id.actname);
		address=(EditText)findViewById(R.id.addr);
		startDay=(EditText)findViewById(R.id.startday);
		startTime=(EditText)findViewById(R.id.starttime);
		endTime=(EditText)findViewById(R.id.endtime);
		actDesc=(EditText)findViewById(R.id.actdesc);
		
		act=new com.example.mapappdemo.entity.Activity();
		act.setActId(UUID.randomUUID().toString());
		
		Bundle bundle=i.getExtras();
		if (((int)bundle.getInt("state"))==0) {
			address.setEnabled(true);
			String loc =String.valueOf(bundle.getDouble("lat")) +":"+String.valueOf(bundle.getDouble("lon"));
			act.setLoc(loc);
		}else {
			String addr=(String)bundle.get("addr");
			String loc =String.valueOf(bundle.getDouble("lat")) +":"+String.valueOf(bundle.getDouble("lon"));
			address.setEnabled(false);
			act.setLoc(loc);
			act.setAddr(addr);
			
		}
		createBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		startDay.setOnClickListener(this);
		startTime.setOnClickListener(this);
		endTime.setOnClickListener(this);
		
		actId.setText(act.getActId());
		address.setText(act.getAddr());
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater=null;
		LinearLayout dataLayout=null;
		switch (v.getId()) {
		case R.id.createsubmit:
			System.out.println("Create Submit!!!!");
			if (!TextUtils.isEmpty(actId.getText()) && !TextUtils.isEmpty(actName.getText()) 
					&& !TextUtils.isEmpty(address.getText()) && !TextUtils.isEmpty(startDay.getText())
					&& !TextUtils.isEmpty(startTime.getText()) && !TextUtils.isEmpty(endTime.getText())
					&& !TextUtils.isEmpty(actDesc.getText())) {
				db.open();
				Cursor cursor=db.getCurrentUser();
				if (cursor.getCount()!=0) {
					User u = new User();
					u.setCurrent(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_USER_CURRENT)));
					u.setUserId(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_USER_ID)));
					u.setUserName(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_USER_USERNAME)));
					act.setActName(actName.getText().toString());
					act.setAddr(address.getText().toString());
					act.setDesc(actDesc.getText().toString());
					act.setStartDay(startDay.getText().toString());
					act.setStartTime(startTime.getText().toString());
					act.setEndTime(endTime.getText().toString());
					act.setHostUserId(u.getUserId());
					act.setHostUserName(u.getUserName());
					db.inserAct(act);
					db.close();
					Toast.makeText(getBaseContext(), "创建活动成功!!!!!", Toast.LENGTH_SHORT).show();		
		            setResult(RESULT_OK,getIntent());
		            finish();
				}else {
					Toast.makeText(getBaseContext(), "请先登陆!!!!!", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.createcancle:
			System.out.println("Create Cancel!!!!");
            setResult(RESULT_OK,getIntent());
            finish();
			break;
		case R.id.startday:
			layoutInflater =LayoutInflater.from(this);
			dataLayout = (LinearLayout) layoutInflater.inflate(R.layout.activity_date_picker, null);
			datePicker = (DatePicker) dataLayout.findViewById(R.id.datepicker); 
			Calendar ca = Calendar.getInstance();
			datePicker.init(ca.get(Calendar.YEAR), ca.get(Calendar.DAY_OF_MONTH), ca.get(Calendar.MONTH), this);
			ad = new AlertDialog.Builder(this)  
             .setTitle("Choose Start Day")  
             .setView(dataLayout)
             .setPositiveButton("设置", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	 startDay.setText(day);  	  
                 }  
             })  
             .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	 startDay.setText("");  
                 }  
             }).show();  
			break;
		case R.id.starttime:
			layoutInflater =LayoutInflater.from(this);
			dataLayout = (LinearLayout) layoutInflater.inflate(R.layout.activity_time_picker, null);
			timePicker = (TimePicker) dataLayout.findViewById(R.id.timepicker); 
			timePicker.setOnTimeChangedListener(this);
			ad = new AlertDialog.Builder(this)  
             .setTitle("Choose Start time")  
             .setView(dataLayout)
             .setPositiveButton("设置", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	 startTime.setText(time);  
                 }  
             })  
             .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	 startTime.setText("");  
                 }  
             }).show();  
			break;
		case R.id.endtime:
			layoutInflater =LayoutInflater.from(this);
			dataLayout = (LinearLayout) layoutInflater.inflate(R.layout.activity_time_picker, null);
			timePicker = (TimePicker) dataLayout.findViewById(R.id.timepicker); 
			timePicker.setOnTimeChangedListener(this);
			ad = new AlertDialog.Builder(this)  
             .setTitle("Choose end time")  
             .setView(dataLayout)
             .setPositiveButton("设置", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	  endTime.setText(time);   	  
                 }  
             })  
             .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                 public void onClick(DialogInterface dialog, int whichButton) {  
                	 endTime.setText("");  
                 }  
             }).show();  
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
	System.out.println("onTimeChanged!!!!"+hourOfDay+":"+minute);
	
		 Calendar ca =Calendar.getInstance();
		 ca.set(1, 1, 1, view.getCurrentHour(), view.getCurrentMinute() );
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
		 time=sdf.format(ca.getTime()); 
		 System.out.println(time);		
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		System.out.println("onDateChanged!!!!"+year+":"+monthOfYear+":"+dayOfMonth);
		
		 Calendar ca =Calendar.getInstance();
		 ca.set(view.getYear(), view.getMonth(),view.getDayOfMonth() );
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
		 day=sdf.format(ca.getTime()); 
		 System.out.println(day);		
	}
}
