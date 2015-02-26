package net.learn2develop.BasicViews4;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.Toast;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends Activity {	
	TimePicker timePicker;
	DatePicker datePicker;
	
    int hour, minute;    
    int yr, month, day;
    
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //showDialog(TIME_DIALOG_ID);
                
        Calendar today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);        
        
        showDialog(DATE_DIALOG_ID);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
    	timePicker.setIs24HourView(true);
    	
    	datePicker = (DatePicker) findViewById(R.id.datePicker);

        //---Button view---
        Button btnOpen = (Button) findViewById(R.id.btnSet);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),
                		"Date selected:" + datePicker.getMonth() + 1 + 
                		"/" + datePicker.getDayOfMonth() +
                		"/" + datePicker.getYear() + "\n" +
                		"Time selected:" + timePicker.getCurrentHour() + 
                		":" + timePicker.getCurrentMinute(), 
                        Toast.LENGTH_SHORT).show();
                }
        });
    }    
    
    @Override    
    protected Dialog onCreateDialog(int id) 
    {
        switch (id) {
            case TIME_DIALOG_ID: 
                return new TimePickerDialog(this, mTimeSetListener, hour, minute, false);
            case DATE_DIALOG_ID: 
                return new DatePickerDialog(this, mDateSetListener, yr, month, day);
        }
        return null;    
    }
 
    private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() 
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) 
            {
            	yr = year;
                month = monthOfYear;
                day = dayOfMonth;
                Toast.makeText(getBaseContext(), 
                        "You have selected : " + (month + 1) +
                        "/" + day + "/" + year,
                        Toast.LENGTH_SHORT).show();
            }
        };
        
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() 
        {        
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) 
            {
                hour = hourOfDay;
                minute = minuteOfHour;
                Toast.makeText(getBaseContext(), 
                        "You have selected : " + hour + ":" + minute,
                        Toast.LENGTH_SHORT).show();

            }
        };       
}