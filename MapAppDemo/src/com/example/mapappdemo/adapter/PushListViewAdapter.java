package com.example.mapappdemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mapappdemo.R;
import com.example.mapappdemo.adapter.ActsListViewAdapter.ListItemView;
import com.example.mapappdemo.entity.Activity;
import com.example.mapappdemo.entity.PushMsg;

public class PushListViewAdapter extends BaseAdapter {
	private Context context=null; 
	private LayoutInflater listContainer=null; 
	private List<PushMsg> pushs=null;
	

	public final class ListItemView{
		private TextView type=null;     
		private TextView title=null;
		private TextView content=null;
		private TextView level=null;
	}

	
	public PushListViewAdapter(Context context,List<PushMsg> pushs) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.pushs=pushs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pushs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ListItemView listItemView = null; 
	    if (convertView==null) {
			listItemView=new ListItemView();
			convertView = listContainer.inflate(R.layout.activity_pushs, null);   
			listItemView.type = (TextView)convertView.findViewById(R.id.pushtype);
			listItemView.title=(TextView)convertView.findViewById(R.id.pushtitle);
			listItemView.content=(TextView)convertView.findViewById(R.id.pushcontent);
			listItemView.level=(TextView)convertView.findViewById(R.id.pushlevel);
			convertView.setTag(listItemView);
		}else { 
			listItemView = (ListItemView)convertView.getTag();
		}
	      String type=(((PushMsg)pushs.get(position)).getType());
	      String title=(((PushMsg)pushs.get(position)).getTitle());
	      String content=(((PushMsg)pushs.get(position)).getContent());
	      String level=(((PushMsg)pushs.get(position)).getLevel());
	      listItemView.type.setText(type);
	      listItemView.title.setText(title);
	      listItemView.content.setText(content);
	      listItemView.level.setText(level);
		  
		return convertView;
	}
}
