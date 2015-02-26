package net.learn2develop.BasicViews5;

import android.app.ListActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {	
	/*
	 String[] presidents = {
	            "Dwight D. Eisenhower",
	            "John F. Kennedy",
	            "Lyndon B. Johnson",
	            "Richard Nixon",
	            "Gerald Ford",
	            "Jimmy Carter",
	            "Ronald Reagan",
	            "George H. W. Bush",
	            "Bill Clinton",
	            "George W. Bush",
	            "Barack Obama"
	    };
	*/
	
	String[] presidents;	 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        ListView lstView = getListView();
        //lstView.setChoiceMode(0); //CHOICE_MODE_NONE
        //lstView.setChoiceMode(1); //CHOICE_MODE_SINGLE
        lstView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
        lstView.setTextFilterEnabled(true);
        
        presidents = getResources().getStringArray(R.array.presidents_array);
        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, presidents));
    }
    
    public void onListItemClick(
    ListView parent, View v, int position, long id) 
    {   
    	//---toggle the check displayed next to the item---
    	parent.setItemChecked(position, parent.isItemChecked(position));   	
    	
        Toast.makeText(this, 
    	    "You have selected " + presidents[position], 
            Toast.LENGTH_SHORT).show();
    }  
};