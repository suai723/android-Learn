package net.learn2develop.ContentProviders;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ContentValues editedValues = new ContentValues();
        editedValues.put(BooksProvider.TITLE, "Android Tips and Tricks");        
        getContentResolver().update(
            Uri.parse(
                "content://net.learn2develop.provider.Books/books/2"), 
            editedValues, 
            null, 
            null);
        
        getContentResolver().delete(
                Uri.parse("content://net.learn2develop.provider.Books/books/2"), 
                null, null);

        getContentResolver().delete(
                Uri.parse("content://net.learn2develop.provider.Books/books"), 
                null, null);
        
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	/*
                //---add a book---
                ContentValues values = new ContentValues();
                values.put(BooksProvider.TITLE, ((EditText) findViewById(R.id.txtTitle)).getText().toString());
                values.put(BooksProvider.ISBN, ((EditText) findViewById(R.id.txtISBN)).getText().toString());        
                Uri uri = getContentResolver().insert(BooksProvider.CONTENT_URI, values);                
    	    	Toast.makeText(getBaseContext(),uri.toString(), Toast.LENGTH_LONG).show();
    	    	*/
    	    	
    	        ContentValues values = new ContentValues();
    	        values.put("title", ((EditText) findViewById(R.id.txtTitle)).getText().toString());
    	        values.put("isbn", ((EditText) findViewById(R.id.txtISBN)).getText().toString());        
    	        Uri uri = getContentResolver().insert(
    	                Uri.parse("content://net.learn2develop.provider.Books/books"), 
    	                values);
    	        Toast.makeText(getBaseContext(),uri.toString(), Toast.LENGTH_LONG).show();
    	    }
        });
        
        Button btnRetrieve = (Button) findViewById(R.id.btnRetrieve);
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
                //---retrieve the titles---
                Uri allTitles = Uri.parse(
                    "content://net.learn2develop.provider.Books/books");
                Cursor c = managedQuery(allTitles, null, null, null, "title desc");
                if (c.moveToFirst()) {
                    do{                    	
                    	Log.v("ContentProviders",
                            c.getString(c.getColumnIndex(
                                BooksProvider._ID)) + ", " +                     
                            c.getString(c.getColumnIndex(
                                BooksProvider.TITLE)) + ", " +                     
                            c.getString(c.getColumnIndex(
                                BooksProvider.ISBN)));               
                    } while (c.moveToNext());
                }
            }
        });
        
    }
}