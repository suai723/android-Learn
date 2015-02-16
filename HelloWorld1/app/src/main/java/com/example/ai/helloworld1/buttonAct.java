package com.example.ai.helloworld1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class buttonAct extends Activity {
    CharSequence [] items={"Google","Apple","Micorosoft"};
    boolean[] itemsChecked=new boolean[items.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_button, menu);
        return true;
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

    public void onClick(View v){
        showDialog(0);
    }

    public void onClick2(View v){
        final ProgressDialog pd = ProgressDialog.show(this,"Doing somthing","Please wait...",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    pd.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    ProgressDialog pd;
    public void onClick3(View v){
        showDialog(1);
        pd.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=15;i++){
                    try {
                        Thread.sleep(5000);
                        pd.incrementProgressBy((int) 100 / 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                pd.dismiss();
            }
        }).start();
    }

    public void onClick4(View v){
        startActivity(new Intent("android.intent.action.Second"));
    }

    int req_code=1; //活动标识
    public  void onClick5(View v){
        startActivityForResult(new Intent("android.intent.action.Second"),req_code);//为这个子活动打上标示
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(data.getData().toString());
        if ((requestCode==req_code) && (resultCode==RESULT_OK)){ //判断是哪个子活动返回的值

            Toast.makeText(this,data.getData().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 0:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
                        .setTitle("this is a test")
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext()," ok clicked!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),"cancel clicked",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setMultiChoiceItems(items,itemsChecked,new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext(),items[which]+(isChecked?"checked":"unchecked"),Toast.LENGTH_SHORT).show();
                            }
                        }).create();
            case 1:
                   pd=new ProgressDialog(this);
                   pd.setIcon(R.drawable.ic_launcher);
                   pd.setTitle("This is a progress");
                   pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                   pd.setButton(DialogInterface.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Toast.makeText(getBaseContext(),"OK clicked",Toast.LENGTH_SHORT).show();
                       }
                   });
                   pd.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Toast.makeText(getBaseContext(),"Cancel clicked",Toast.LENGTH_SHORT).show();
                       }
                   });
                   return  pd;
        }
        return super.onCreateDialog(id);
    }

}
