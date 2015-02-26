package net.learn2develop.Email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btnSendEmail;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);        
        btnSendEmail.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {     
            	String[] to = {"weimenglee@learn2develop.net", "weimenglee@gmail.com"};   
                String[] cc = {"course@learn2develop.net"};           
            	sendEmail(to, cc, "Hello", "Hello my friends!");
            }
        });
    }
    
    //---sends an SMS message to another device---
    private void sendEmail(String[] emailAddresses, String[] carbonCopies, 
    String subject, String message)
    {     
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:")); 
        String[] to = emailAddresses;   
        String[] cc = carbonCopies; 
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);   
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);        
        emailIntent.setType("message/rfc822");   
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }
}