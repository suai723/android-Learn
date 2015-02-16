package com.example.ai.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class SecondActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent i = getIntent();
        Toast.makeText(this,i.getStringExtra("str1").toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,String.valueOf(i.getIntExtra("age",0)),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,i.getStringExtra("str2").toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,String.valueOf(i.getIntExtra("age2",0)),Toast.LENGTH_SHORT).show();
    }

        public void onClick2(View v){
            Intent i = new Intent();
            i.putExtra("age3",45);
            i.setData(Uri.parse("something passed back to main activity"));
            setResult(RESULT_OK,i);
            finish();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
}
