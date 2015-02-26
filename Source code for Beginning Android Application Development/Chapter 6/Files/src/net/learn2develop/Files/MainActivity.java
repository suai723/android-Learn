package net.learn2develop.Files;

import android.app.Activity;
import android.view.View;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;


import java.io.InputStream;
import java.io.BufferedReader;

public class MainActivity extends Activity {	
	private EditText textBox; 
	private static final int READ_BLOCK_SIZE = 100;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
               
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str = null;
        try {                
            while ((str = br.readLine()) != null) {               
         	   Toast.makeText(getBaseContext(), 
                   str, Toast.LENGTH_SHORT).show();
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        
        textBox = (EditText) findViewById(R.id.txtText1);
        Button saveBtn = (Button) findViewById(R.id.btnSave);
        Button loadBtn = (Button) findViewById(R.id.btnLoad);        
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {         
            	
            	String str = textBox.getText().toString();
                try 
                {                 	
                	/*
                	//---Internal Storage---
                    FileOutputStream fOut = 
                        openFileOutput("textfile.txt", 
                        MODE_WORLD_READABLE);
                    //---------------------- 
                    */                    
                    //---SD Card Storage---
                    File sdCard = Environment.getExternalStorageDirectory();
                	File directory = new File (sdCard.getAbsolutePath() + "/MyFiles");
                	directory.mkdirs();
                	File file = new File(directory, "textfile.txt"); 
                    FileOutputStream fOut = new FileOutputStream(file);
                    //---------------------
                    
                    OutputStreamWriter osw = new 
                    OutputStreamWriter(fOut);
                    
                    //---write the string to the file--- 
                    osw.write(str);              
                    osw.flush(); 
                    osw.close();
                    
                    //---display file saved message---
                    Toast.makeText(getBaseContext(), 
                        "File saved successfully!", 
                        Toast.LENGTH_SHORT).show();
                 
                    //---clears the EditText---
                    textBox.setText("");
                 } 
                 catch (IOException ioe) 
                 { 
                     ioe.printStackTrace(); 
                 }  
                 //==================================
            }
        });
        
        loadBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try 
                {  
                	/*
                	//---Internal Storage---
                    FileInputStream fIn = openFileInput("textfile.txt");
                    InputStreamReader isr = new InputStreamReader(fIn); 
                    */
                    //---SD Storage---                	
                	File sdCard = Environment.getExternalStorageDirectory();
                	File directory = new File (sdCard.getAbsolutePath() + "/MyFiles");
                	File file = new File(directory, "textfile.txt");
                	FileInputStream fIn = new FileInputStream(file);                	
                    InputStreamReader isr = new InputStreamReader(fIn);
                	//----------------
                    
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
     
                    int charRead;
                    while ((charRead = isr.read(inputBuffer))>0)
                    {                    
                        //---convert the chars to a String---
                        String readString = 
                            String.copyValueOf(inputBuffer, 0, charRead);                    
                        s += readString;

                       inputBuffer = new char[READ_BLOCK_SIZE];
                    } 
                    //---set the EditText to the text that has been 
                    // read---                                
                    textBox.setText(s);
                    
                    Toast.makeText(getBaseContext(),
                        "File loaded successfully!", 
                        Toast.LENGTH_SHORT).show();
                } 
                catch (IOException ioe) { 
                    ioe.printStackTrace(); 
                }            	
            }
        });
    }    
}