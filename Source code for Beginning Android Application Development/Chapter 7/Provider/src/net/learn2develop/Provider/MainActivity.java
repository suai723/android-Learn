package net.learn2develop.Provider;

import android.app.Activity;
import android.os.Bundle;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Uri allContacts = Uri.parse("content://contacts/people");
        //Uri allContacts = Uri.parse("content://contacts/people/1");        
        Uri allContacts = ContactsContract.Contacts.CONTENT_URI;
        //Uri allContacts = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, 1);
        
        
        String[] projection = new String[] 
            {ContactsContract.Contacts._ID, 
             ContactsContract.Contacts.DISPLAY_NAME,
             ContactsContract.Contacts.HAS_PHONE_NUMBER};
        
        Cursor c = managedQuery(
        		allContacts, 
        		projection, 
        		ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?", 
        		new String[] {"%"} , 
        		ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        
        //Cursor c = getContentResolver().query(allContacts, null, null, null, null);
        //startManagingCursor(c); //---allows the activity to manage the Cursor’s 
                                // lifecyle based on the activity’s lifecycle---                
        
        String[] columns = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,};
        int[] views = new int[] {R.id.contactName, R.id.contactID};
        
        SimpleCursorAdapter adapter = 
            new SimpleCursorAdapter(this, R.layout.main, c, columns, views);
        this.setListAdapter(adapter);
        
        PrintContacts(c);  
    }    
    
    private void PrintContacts(Cursor c)
    {
        if (c.moveToFirst()) {
            do{
            	String contactID = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            	String contactDisplayName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));  
                Log.v("Content Providers", contactID + ", " + contactDisplayName);
                //---get phone number---
                int hasPhone = c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone == 1) {
                    Cursor phoneCursor = getContentResolver().query(
      		            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
      		            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);                     
      	            while (phoneCursor.moveToNext()) {
      	        	    Log.v("Content Providers",
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));      	        	
      	            } 
      	            phoneCursor.close();
      	        }
            } while (c.moveToNext());
        }
    }
}