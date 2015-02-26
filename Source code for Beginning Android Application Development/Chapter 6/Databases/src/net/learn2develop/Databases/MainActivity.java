package net.learn2develop.Databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import android.database.Cursor;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {        	
        	String destPath = "/data/data/" + getPackageName() + "/databases/MyDB";
        	File f = new File(destPath);        	
        	if (!f.exists()) {        	
			    CopyDB( getBaseContext().getAssets().open("mydb"), 
					new FileOutputStream(destPath));
        	}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
                
        DBAdapter db = new DBAdapter(this); 

        /*
        //---add a contact---
        db.open();        
        long id = db.insertContact("Wei-Meng Lee", "weimenglee@learn2develop.net");        
        id = db.insertContact("Mary Jackson", "mary@jackson.com");
        db.close();
        */
        
        //---get all contacts---
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {          
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
        
        /*
        //---get a contact---
        db.open();
        Cursor c = db.getContact(2);
        if (c.moveToFirst())        
            DisplayContact(c);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        db.close();
        */
        
        /*
        //---update contact---
        db.open();
        if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();        
        db.close();
        */
        
        /*
        //---delete a contact---
        db.open();
        if (db.deleteContact(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();            
        db.close();
        */
    }
        
    public void CopyDB(InputStream inputStream, OutputStream outputStream) 
    throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
    
    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Email:  " + c.getString(2),
                Toast.LENGTH_LONG).show();        
    } 
}