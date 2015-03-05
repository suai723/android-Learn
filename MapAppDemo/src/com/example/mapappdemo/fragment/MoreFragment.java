package com.example.mapappdemo.fragment;

import java.util.UUID;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.adapter.DBAdapter;
import com.example.mapappdemo.entity.User;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.R.integer;
import android.app.Fragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MoreFragment extends Fragment implements OnClickListener{
	private Button loginBtn;
	private Button logoutBtn;
	private Button registBtn;
	private Button clearBtn;
	
	private EditText mLogUserName;
	private EditText mLogPassWord;
	private EditText mUserName;
	private EditText mPassWord;
	private EditText mRePassWord;
	private EditText mName;
	
	public static User currentUser =null;
	
	DBAdapter  db=null;
	View mFragmentRootView =null;
	Context context = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFragmentRootView=inflater.inflate(R.layout.activity_act4, container,false);
		context =getActivity().getBaseContext();
		db=new DBAdapter(context);
		
		loginBtn =(Button)mFragmentRootView.findViewById(R.id.login);
		logoutBtn=(Button)mFragmentRootView.findViewById(R.id.loginout);
		registBtn=(Button)mFragmentRootView.findViewById(R.id.register);
		clearBtn=(Button)mFragmentRootView.findViewById(R.id.clear);
		
		mLogUserName=(EditText)mFragmentRootView.findViewById(R.id.logusername);
		mLogPassWord=(EditText)mFragmentRootView.findViewById(R.id.logpassword);
		mUserName=(EditText)mFragmentRootView.findViewById(R.id.username);
		mPassWord=(EditText)mFragmentRootView.findViewById(R.id.password);
		mRePassWord=(EditText)mFragmentRootView.findViewById(R.id.repassword);
		mName=(EditText)mFragmentRootView.findViewById(R.id.realname);
		
		loginBtn.setOnClickListener(this);
		logoutBtn.setOnClickListener(this);
		registBtn.setOnClickListener(this);
		clearBtn.setOnClickListener(this);
		return mFragmentRootView;
	}
	


	@Override
	public void onClick(View v) {
		db.open();
		String userName=null;
		String passWord=null;
		String name=null;
		String repassword=null;
		switch (v.getId()) {
		case R.id.login:
			userName=mLogUserName.getText().toString();
			passWord=mLogPassWord.getText().toString();
			User realUser=null;
			if (userName!=null && passWord!=null) {
				User u = new User(null, userName, passWord, null, null);

				Cursor cursor=db.getUser(u.getUserName());
				if (cursor.getCount()!=0) {
					realUser=new User();
					realUser.setUserName(cursor.getString(cursor.getColumnIndex(db.KEY_USER_USERNAME)));
					realUser.setPassWord(cursor.getString(cursor.getColumnIndex(db.KEY_USER_PASSWORD)));
					realUser.setName(cursor.getString(cursor.getColumnIndex(db.KEY_USER_NAME)));
					realUser.setUserId(cursor.getString(cursor.getColumnIndex(db.KEY_USER_ID)));
					realUser.setCurrent("Y");
				}else {
					Toast.makeText(context, "无此用户!!!!!", Toast.LENGTH_SHORT).show();
					break;
				}
				if (passWord.equals(realUser.getPassWord())) {
					db.updateUser(realUser);
					currentUser=realUser;
					Toast.makeText(context, "登陆成功!!!!!!!", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(context, "密码错误!!!!!!!", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(context, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
			}	
			break;
		case R.id.loginout:
			if (currentUser!=null) {
				currentUser.setCurrent("N");
				db.updateUser(currentUser);
				Toast.makeText(context, "登出成功!!!!!", Toast.LENGTH_SHORT).show();
				currentUser=null;
			}else {
				Toast.makeText(context, "请登陆!!!!!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.register:
			
		    userName=mUserName.getText().toString();
		    passWord=mPassWord.getText().toString().trim();
		    name=mName.getText().toString();
		    repassword=mRePassWord.getText().toString().trim();
		    
		    if (!TextUtils.isEmpty(mUserName.getText()) && !TextUtils.isEmpty(mPassWord.getText())
		    		&& !TextUtils.isEmpty(mRePassWord.getText()) && !TextUtils.isEmpty(mName.getText())) {
		    	if (db.getUser(userName).getCount()==0) {//后期改为向服务器数据库请求
//		    		System.out.println(passWord+"aaa"+repassword);
					if (passWord.equals(repassword)) {
						User u = new User(UUID.randomUUID().toString(), userName, passWord, name, "N");
						db.insertUser(u);
						Cursor cursor=db.getUser(u.getUserName());
						Toast.makeText(context, "注册成功!!!!!", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(context, "2次密码输入不正确!!!!!", Toast.LENGTH_SHORT).show();
						break;
					}
				}else {
					Toast.makeText(context, "已经存在该用户!!!!!", Toast.LENGTH_SHORT).show();
					break;
				}
		    }else {
		    	Toast.makeText(context, "请输入用户信息!!!!!", Toast.LENGTH_SHORT).show();
		    	break;
			}
			break;
		case R.id.clear:
			mPassWord.setText(null);
			mRePassWord.setText(null);
			mUserName.setText(null);
			mName.setText(null);	
			break;
		default:
			break;
		}
		db.close();
		
	}
}
