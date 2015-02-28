package com.example.mapappdemo;

import java.io.Serializable;
import java.util.UUID;

import com.example.mapappdemo.entity.Activity;
import com.example.mapappdemo.entity.Person;
import com.example.mapappdemo.entity.User;

import android.app.DownloadManager.Query;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper implements Serializable {

	static final String KEY_ROWID="_id";//行id
	static final String KEY_ACT_ID="act_id";//活动id
	static final String KEY_ACT_ADDR="addr";//活动地址
	static final String KEY_ACT_LOC="loc";//活动地理位置
	static final String KEY_ACT_DAY="start_day";//开始日期
	static final String KEY_ACT_START_TIME="start_time";//开始时间
	static final String KEY_ACT_END_TIME="end_time";//结束时间
	static final String KEY_ACT_PERSON_NUM="person_num";//参与人数
	static final String KEY_ACT_DESC="desc";//活动描述
	static final String KEY_ACT_HOST_USERNAME="host_user_name";//
	static final String KEY_ACT_HOST_USERID="host_user_id";//
	static final String KEY_ACT_NAME="act_name";//
	static final String KEY_ACT_STATE="act_state";//
	
	static final String[] ActFields=new String[]{KEY_ROWID, KEY_ACT_ID, KEY_ACT_ADDR,KEY_ACT_LOC,KEY_ACT_DAY,
			KEY_ACT_START_TIME,KEY_ACT_END_TIME,KEY_ACT_PERSON_NUM,KEY_ACT_DESC,KEY_ACT_HOST_USERNAME,
			KEY_ACT_HOST_USERID,KEY_ACT_NAME,KEY_ACT_STATE};
	
	
	static final String KEY_PERSON_ID="person_id";//活动人编号
	static final String KEY_PERSON_ACT_ID="act_id";//对应活动id
	static final String KEY_PERSON_NAME="name";//参加活动人姓名
	static final String KEY_PERSON_CURLOC="current_loc";//当前位置
	static final String KEY_PERSON_ARRIVE_STATE="arrive_state";//是否到达活动
	static final String KEY_PERSON_ARRIVE_TIME="arrive_time";//到达时间
	static final String KEY_PERSON_LEAVE_STATE="leave_state";//是否请假
	static final String KEY_PERSON_REASON_DESC="reason_desc";//请假原因
	static final String KEY_PERSON_HOST="host";//参与者还是主办人
	static final String KEY_PERSON_USER_NAME="user_name";//用户名
	static final String KEY_PERSON_USER_ID="user_id";//用户id
	
	static final String[] PersonFields= new String[]{KEY_PERSON_ID,KEY_PERSON_ACT_ID,KEY_PERSON_NAME,KEY_PERSON_CURLOC,
		KEY_PERSON_ARRIVE_STATE,KEY_PERSON_ARRIVE_TIME,KEY_PERSON_LEAVE_STATE,KEY_PERSON_REASON_DESC,KEY_PERSON_HOST,
		KEY_PERSON_USER_NAME,KEY_PERSON_USER_ID};
	
	
	static final String KEY_USER_ID="user_id";
	static final String KEY_USER_USERNAME="user_name";//用户名
	static final String KEY_USER_PASSWORD="user_password";//密码
	static final String KEY_USER_NAME="name";//姓名
	static final String KEY_USER_CURRENT="current";
	
	static final String[] UserFields= new String[]{
		KEY_USER_ID,KEY_USER_USERNAME,KEY_USER_PASSWORD,KEY_USER_NAME,KEY_USER_CURRENT
	};
	
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_ACT_TABLE ="t_act";
    static final String DATABASE_PERSON_TABLE ="t_person";
    static final String DATABASE_USER_TABLE ="t_user";
    
    static final int DATABASE_VERSION = 2;

    static final String DBCREATE_ACT_TABLE="create table "+DATABASE_ACT_TABLE+" ("
    		+"_id integer primary key autoincrement, "
    		+ "act_id text, "
    		+ "addr text, "
    		+ "loc text, "
    		+ "start_day text, "
    		+ "start_time text, "
    		+ "end_time text, "
    		+ "person_num text, "
    		+ "desc text, "
    		+ "host_user_name text, "
    		+ "host_user_id text, "
    		+ "act_name text, "
    		+ "act_state text "
    		+ ");";
    static final String DBCREATE_PERSON_TABLE="create table "+DATABASE_PERSON_TABLE+" ("
    		+ "_id integer primary key autoincrement,"
    		+ "person_id text, "
    		+ "act_id text, "
    		+ "name text, "
    		+ "current_loc text, "
    		+ "arrive_state text, "
    		+ "arrive_time text, "
    		+ "leave_state text, "
    		+ "reason_desc text, "
    		+ "host text, "
    		+ "user_name text, "
    		+ "user_id text "    
    		+ ");";
    static final String DBCREATE_USER_TABLE="create table "+DATABASE_USER_TABLE+" ("
    		+ "_id integer primary key autoincrement, "
    		+ "user_id text, "
    		+ "user_name text, "
    		+ "user_password text, "
    		+ "name text, "
    		+ "current text "
    		+ ");";
    SQLiteDatabase db;
	
	public DBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			db.execSQL(DBCREATE_ACT_TABLE);
			db.execSQL(DBCREATE_PERSON_TABLE);
			db.execSQL(DBCREATE_USER_TABLE);
		} catch ( SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
    public DBAdapter open() {
        db = getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_ACT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_PERSON_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_USER_TABLE);
		
		db.execSQL(DBCREATE_ACT_TABLE);
		db.execSQL(DBCREATE_PERSON_TABLE);
		db.execSQL(DBCREATE_USER_TABLE);
	}
	
	public long inserAct(Activity act){
		ContentValues values = new ContentValues();
		values.put(KEY_ACT_ID,act.getActId());
		values.put(KEY_ACT_ADDR, act.getAddr());
		values.put(KEY_ACT_LOC, act.getLoc());
		values.put(KEY_ACT_DAY, act.getStartDay());
		values.put(KEY_ACT_START_TIME, act.getStartTime());
		values.put(KEY_ACT_END_TIME, act.getEndTime());
		values.put(KEY_ACT_PERSON_NUM, act.getPersonNum());
		values.put(KEY_ACT_DESC, act.getDesc());
		values.put(KEY_ACT_HOST_USERNAME, act.getHostUserName());
		values.put(KEY_ACT_HOST_USERID, act.getHostUserId());
		values.put(KEY_ACT_NAME, act.getActName());
		return db.insert(DATABASE_ACT_TABLE, null, values);	
	}
	
	public Boolean deleteACT(String actId) {
		return db.delete(DATABASE_ACT_TABLE, KEY_ACT_ID + "=\'" + actId+"\'", null) > 0;
	}

	public Cursor getAllActs() {

		return db.query(DATABASE_ACT_TABLE, ActFields, null, null, null, null, null);
	}
	
	public Cursor getAct(String actId) {
	    Cursor cursor = db.query(true, DATABASE_ACT_TABLE, ActFields,KEY_ACT_ID + "=\'" + actId+"\'", null, null, null, null, null);
	    if (cursor!=null) {
			cursor.moveToFirst();
		}
	    return cursor;
	}
	
	public Boolean updateAct(Activity act){
		ContentValues values = new ContentValues();
		values.put(KEY_ACT_ID,act.getActId());
		values.put(KEY_ACT_ADDR, act.getAddr());
		values.put(KEY_ACT_LOC, act.getLoc());
		values.put(KEY_ACT_DAY, act.getStartDay());
		values.put(KEY_ACT_START_TIME, act.getStartTime());
		values.put(KEY_ACT_END_TIME, act.getEndTime());
		values.put(KEY_ACT_PERSON_NUM, act.getPersonNum());
		values.put(KEY_ACT_DESC, act.getDesc());
		values.put(KEY_ACT_HOST_USERNAME, act.getHostUserName());
		values.put(KEY_ACT_HOST_USERID, act.getHostUserId());
		values.put(KEY_ACT_NAME, act.getActName());
		return db.update(DATABASE_ACT_TABLE,values,KEY_ACT_ID+"="+act.getActId(),null)>0;
	};
	
	
	
	public long insertPerson(Person person) {
		ContentValues values = new ContentValues();
		values.put(KEY_PERSON_ID, person.getPersonId());
		values.put(KEY_PERSON_ACT_ID, person.getActId());
		values.put(KEY_PERSON_NAME, person.getName());
		values.put(KEY_PERSON_CURLOC, person.getCurrentLoc());
		values.put(KEY_PERSON_ARRIVE_STATE, person.getArriveState());
		values.put(KEY_PERSON_LEAVE_STATE, person.getLeaveState());
		values.put(KEY_PERSON_REASON_DESC, person.getReasonDesc());
		values.put(KEY_PERSON_HOST, person.getHost());
		values.put(KEY_PERSON_USER_NAME, person.getUserName());
		values.put(KEY_PERSON_USER_ID, person.getUserId());
		
		return db.insert(DATABASE_PERSON_TABLE, null, values);	
	}
	
	public Boolean deleteActPerson(String actId,String userId) {
		String where =KEY_PERSON_ACT_ID + "=\'" + actId+"\' and "+KEY_PERSON_USER_ID+"=\'"+userId+"\'";
		return db.delete(DATABASE_PERSON_TABLE, where, null) > 0;
	}
	
	public Cursor getAllPersons(String actId) {
	    Cursor cursor = db.query(true, DATABASE_PERSON_TABLE, PersonFields, KEY_PERSON_ACT_ID + "=\'" + actId+"\'", null, null, null, null, null);
	    if (cursor!=null) {
			cursor.moveToFirst();
		}
	    return cursor;
	}
	
	public Cursor getPerson(String actId,String userId) {
		String where =KEY_PERSON_ACT_ID + "=\'" + actId+"\' and "+KEY_PERSON_USER_ID+"=\'"+userId+"\'";
	    Cursor cursor = db.query(true, DATABASE_PERSON_TABLE, PersonFields, where, null, null, null, null, null);
	    if (cursor!=null) {
			cursor.moveToFirst();
		}
	    return cursor;
	}
	
	public Boolean updatePerson(Person person){
		ContentValues values = new ContentValues();
		values.put(KEY_PERSON_ID, person.getPersonId());
		values.put(KEY_PERSON_ACT_ID, person.getActId());
		values.put(KEY_PERSON_NAME, person.getName());
		values.put(KEY_PERSON_CURLOC, person.getCurrentLoc());
		values.put(KEY_PERSON_ARRIVE_STATE, person.getArriveState());
		values.put(KEY_PERSON_LEAVE_STATE, person.getLeaveState());
		values.put(KEY_PERSON_REASON_DESC, person.getReasonDesc());
		values.put(KEY_PERSON_HOST, person.getHost());
		values.put(KEY_PERSON_USER_NAME, person.getUserName());
		values.put(KEY_PERSON_USER_ID, person.getUserId());
		
		String where =KEY_PERSON_ACT_ID + "=\'" + person.getActId()+"\' and "+KEY_PERSON_USER_ID+"=\'"+person.getUserId()+"\'";
		
		return db.update(DATABASE_PERSON_TABLE,values,where,null)>0;
	};
	
	
	public long insertUser(User user) {
		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user.getUserId());
		values.put(KEY_USER_USERNAME, user.getUserName());
		values.put(KEY_USER_PASSWORD, user.getPassWord());
		long i=db.insert(DATABASE_USER_TABLE, null, values);
		return i;
	}
	
	public Boolean deleteUser(String userName) {
		String where =KEY_USER_USERNAME + "=\'" + userName+"\'";
		return db.delete(DATABASE_USER_TABLE, where, null) > 0;
	}
	
	public Cursor getAllUser() {
		return db.query(DATABASE_USER_TABLE,UserFields, null, null, null, null, null);
	}
	
	public Cursor getUser(String userName) {
		String where=KEY_USER_USERNAME + "=\'" + userName+"\'";
		Cursor cursor=db.rawQuery("select user_id,user_name,user_password,name,current from t_user where user_name=?",new String[]{userName});
		cursor.moveToFirst();
	    return cursor;
	}

	//获得当前登陆用户
	public Cursor getCurrentUser() {
		Cursor cursor=db.rawQuery("select user_id,user_name,user_password,name,current from t_user where current=?",new String[]{"Y"});
		cursor.moveToFirst();
		return cursor;
	}
	
	public Boolean updateUser(User user){
		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user.getUserId());
		//values.put(KEY_USER_USERNAME, user.getUserName());
		values.put(KEY_USER_PASSWORD, user.getPassWord());
		values.put(KEY_USER_CURRENT, user.getCurrent());
		
		String where =KEY_USER_USERNAME + "=\'" + user.getUserName()+"\'";
		return db.update(DATABASE_USER_TABLE,values,where,null)>0;
	}
}
