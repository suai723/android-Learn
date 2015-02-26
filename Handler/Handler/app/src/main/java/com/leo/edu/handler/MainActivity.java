package com.leo.edu.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    Handler mHandler;
TextView mHtmlTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHtmlTv = (TextView) findViewById(R.id.html_textview);
//        try {
//            String serverURL = "http://baidu.com";
//            HttpGet httpRequest = new HttpGet(serverURL);// 建立http get联机
//            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);// 发出http请求
//
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                String result = EntityUtils.toString(httpResponse.getEntity());// 获取相应的字符串
//                System.out.println(result);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mHtmlTv.setText((String)msg.obj);
            }
        };
        NetworkThread mNetworkThread = new NetworkThread(mHandler);
        mNetworkThread.start();

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
}
