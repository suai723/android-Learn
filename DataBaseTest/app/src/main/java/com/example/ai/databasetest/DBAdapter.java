package com.example.ai.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Ai on 15/2/21.
 */
public class DBAdapter  extends SQLiteOpenHelper {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;

    SQLiteDatabase db;

    static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement," +
                    "name text not null, " +
                    "email text not null)";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upfrading database from version" + oldVersion + "to" + newVersion + "which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


    public DBAdapter open() {
        db = getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }


    public long insert(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteContact(long rowid) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowid, null) > 0;
    }

    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_EMAIL}, null, null, null, null, null);
    }

    public Cursor getContact(long rowid) {
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowid, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  boolean updateContact(long rowid,String name,String email){
        ContentValues value = new ContentValues();
        value.put(KEY_NAME,name);
        value.put(KEY_EMAIL,email);
        return db.update(DATABASE_TABLE,value,KEY_ROWID+"="+rowid,null)>0;
    }

}
