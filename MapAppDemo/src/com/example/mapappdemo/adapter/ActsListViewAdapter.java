package com.example.mapappdemo.adapter;

import java.util.List;

import com.example.mapappdemo.R;
import com.example.mapappdemo.R.id;
import com.example.mapappdemo.R.layout;
import com.example.mapappdemo.entity.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActsListViewAdapter extends BaseAdapter{
	private Context context=null; 
	private LayoutInflater listContainer=null; 
	private List<Activity> acts=null;

	public final class ListItemView{
		private TextView actName=null;     
		private TextView uuid=null;
		private TextView hotsUserName=null;
		private TextView day=null;
		private TextView startTime=null;
	}
    
    
	public ActsListViewAdapter(Context context,List<Activity> acts) {
		super();
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.acts=acts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return acts.size();
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
		// TODO Auto-generated method stub
	    ListItemView listItemView = null; 
	    if (convertView==null) {
			listItemView=new ListItemView();
			convertView = listContainer.inflate(R.layout.activity_acts, null);   
			listItemView.actName = (TextView)convertView.findViewById(R.id.actname);
			listItemView.uuid=(TextView)convertView.findViewById(R.id.uuid);
			listItemView.hotsUserName=(TextView)convertView.findViewById(R.id.hostname);
			listItemView.day=(TextView)convertView.findViewById(R.id.actsday);
			listItemView.startTime=(TextView)convertView.findViewById(R.id.actsstarttime);
			convertView.setTag(listItemView);
		}else { 
			listItemView = (ListItemView)convertView.getTag();
		}
		     
		  listItemView.actName.setText(((Activity)acts.get(position)).getActName());  
		  listItemView.uuid.setText(((Activity)acts.get(position)).getActId()); 
		  listItemView.hotsUserName.setText(((Activity)acts.get(position)).getHostUserName());  
		  listItemView.hotsUserName.setText(((Activity)acts.get(position)).getHostUserName());  
		  listItemView.day.setText(((Activity)acts.get(position)).getStartDay());  
		  listItemView.startTime.setText(((Activity)acts.get(position)).getStartTime());
		return convertView;
	}

}
