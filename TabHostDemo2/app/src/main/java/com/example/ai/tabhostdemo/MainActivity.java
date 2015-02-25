package com.example.ai.tabhostdemo;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;


public class MainActivity extends TabActivity implements OnCheckedChangeListener {
    private TabHost mTabHost;
    private Intent mAct1Intent;
    private Intent mAct2Intent;
    private Intent mAct3Intent;
    private Intent mAct4Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mAct1Intent= new Intent(this,Act1.class);
        mAct2Intent= new Intent(this,Act2.class);
        mAct3Intent= new Intent(this,Act3.class);
        mAct4Intent= new Intent(this,Act4.class);

        ((RadioButton)findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button4)).setOnCheckedChangeListener(this);

        setupIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            switch(buttonView.getId()){
                case R.id.radio_button1:
                   mTabHost.setCurrentTabByTag("A_TAB");
                   break;
                case R.id.radio_button2:
                   mTabHost.setCurrentTabByTag("B_TAB");
                   break;
                case R.id.radio_button3:
                   mTabHost.setCurrentTabByTag("C_TAB");
                   break;
                case R.id.radio_button4:
                   mTabHost.setCurrentTabByTag("D_TAB");
                   break;
            }
        }
    }

    private void setupIntent(){
        mTabHost=getTabHost();
        mTabHost.addTab(buildTabSpec("A_TAB",R.string.tab1,R.drawable.icon_1_n,this.mAct1Intent));
        mTabHost.addTab(buildTabSpec("B_TAB",R.string.tab2,R.drawable.icon_2_n,this.mAct2Intent));
        mTabHost.addTab(buildTabSpec("C_TAB",R.string.tab3,R.drawable.icon_3_n,this.mAct3Intent));
        mTabHost.addTab(buildTabSpec("D_TAB",R.string.tab4,R.drawable.icon_4_n,this.mAct4Intent));
    }

    private TabHost.TabSpec buildTabSpec(String tag,int resLabel,int resIcon,final Intent conetnt){
        return mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),getResources().getDrawable(resIcon)).setContent(conetnt);
    }
}
