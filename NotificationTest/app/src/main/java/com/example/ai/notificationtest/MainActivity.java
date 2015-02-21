package com.example.ai.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
    int notifiactionId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v){
        displayNotification();
    }

    protected void displayNotification(){
        Intent i = new Intent(this,NotifiacationView.class);
        i.putExtra("notify",notifiactionId);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notif = new Notification(R.drawable.ic_launcher,
                "Reminder:Meeting starts in 5 minutes",
                System.currentTimeMillis());
        CharSequence from = "System Alarm";
        CharSequence message = "Meeting with customer at 3pm";
        notif.setLatestEventInfo(this,from,message,pendingIntent);
        notif.vibrate=new long[]{100,250,100,500};
        nm.notify(notifiactionId,notif);
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
