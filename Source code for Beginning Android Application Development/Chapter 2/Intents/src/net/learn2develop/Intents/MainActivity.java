package net.learn2develop.Intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	Button b1, b2, b3, b4, b5;
    int request_Code = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---Web browser button---
        b1 = (Button) findViewById(R.id.btn_webbrowser);
        b1.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0){
            	/*
                Intent i = new
                    Intent(android.content.Intent.ACTION_VIEW,
                      Uri.parse("http://www.amazon.com"));
                */
            	
            	//---OR---
            	Intent i = new
                Intent("android.intent.action.VIEW");            	
                //Intent(android.content.Intent.ACTION_VIEW);            	
            	i.setData(Uri.parse("http://www.amazon.com"));
            	
                startActivity(i);                
            }
        });
        
        //---Make calls button---
        b2 = (Button) findViewById(R.id.btn_makecalls);
        b2.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0){
            	/*
                Intent i = new 
                    Intent(android.content.Intent.ACTION_DIAL);
                startActivity(i);
                */
            	
                //---OR---
            	
                Intent i = new 
                    Intent(android.content.Intent.ACTION_CALL, 
                  Uri.parse("tel:+651234567"));
                startActivity(i);
                                
            }
        });   
        
        //---Show Map button---
        b3 = (Button) findViewById(R.id.btn_showMap);
        b3.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0){
                Intent i = new 
                    Intent(android.content.Intent.ACTION_VIEW, 
                  Uri.parse("geo:37.827500,-122.481670"));                
                startActivity(i);                
            }
        });   
        
        //---Choose Contact button---
        b4 = (Button) findViewById(R.id.btn_chooseContact);
        b4.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0){
                Intent i = new 
                Intent(android.content.Intent.ACTION_PICK);                		                
                //i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i,request_Code);                
            }
        });                

        b5 = (Button) findViewById(R.id.btn_launchMyBrowser);
        b5.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0)
            {    
            	/*
            	//---Method 1---            	
            	Intent i = new
                    Intent("net.learn2develop.MyBrowser");
            	i.setData(Uri.parse("http://www.amazon.com"));            	
                startActivity(i);                
            	*/
            	
            	/*
                //---Method 2---
                Intent i = new
                    Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.amazon.com"));
                startActivity(i);                
                */
            	                
                //---Method 3---
                Intent i = new
                    Intent("net.learn2develop.MyBrowser",
                        Uri.parse("http://www.amazon.com"));
                i.addCategory("net.learn2develop.OtherApps");
                i.addCategory("net.learn2develop.SomeOtherApps");
                startActivity(i);                                
            }                
        });        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == request_Code) 
        {
     	    if (resultCode == RESULT_OK)
       	    {                
       	        Toast.makeText(this,data.getData().toString(), 
       	            Toast.LENGTH_SHORT).show();
       	        Intent i = new Intent(
       	        		android.content.Intent.ACTION_VIEW, 
       	        		Uri.parse(data.getData().toString()));
       	        startActivity(i);
       	    }            
        }
    }
}