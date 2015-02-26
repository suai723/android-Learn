package net.learn2develop.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import android.view.KeyEvent;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {
    String tag = "Events";
    
    int request_Code = 1;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //---hides the title bar---
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
        Log.d(tag, "In the onCreate() event");     
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
        {
            //startActivity(new Intent("net.learn2develop.ACTIVITY2"));    
            //startActivity(new Intent(this, Activity2.class));
            /*
            startActivityForResult(new Intent(
                "net.learn2develop.ACTIVITY2"), 
                request_Code);
            */        	
            Intent i = new Intent("net.learn2develop.ACTIVITY2");            
           
            Bundle extras = new Bundle();
            extras.putString("Name", "Your name here");
            i.putExtras(extras);
            startActivityForResult(i, 1);
        }
        return false;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == request_Code) {
    	    if (resultCode == RESULT_OK) {                
    	        Toast.makeText(this,data.getData().toString(), 
    	            Toast.LENGTH_SHORT).show();
            }            
        }
    } 
    
    public void onStart()
    {
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }    
    public void onRestart()
    {
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }    
    public void onResume()
    {
        super.onResume();
        Log.d(tag, "In the onResume() event");
    }
    public void onPause()
    {
        super.onPause();
        Log.d(tag, "In the onPause() event");
    }    
    public void onStop()
    {
        super.onStop();
        Log.d(tag, "In the onStop() event");
    }    
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");
    }    
}