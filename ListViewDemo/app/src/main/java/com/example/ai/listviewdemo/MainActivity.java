package com.example.ai.listviewdemo;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    String[] parms ={
      "key1","key2","key3","key4","key5","key6","key7","key8","key9","key10","key11","key12"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,parms));
    }

    public void onListItemClick(
            ListView parent, View v, int position, long id)
    {
        //---toggle the check displayed next to the item---
        parent.setItemChecked(position, parent.isItemChecked(position));

        Toast.makeText(this,
                "You have selected " + parms[position],
                Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
