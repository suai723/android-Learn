package com.leo.edu.handler;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by docker on 2015/2/23.
 */
public class NetworkThread extends Thread {

    Handler mHandler;

    public NetworkThread(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            String serverURL = "http://baidu.com";
            HttpGet httpRequest = new HttpGet(serverURL);// 建立http get联机
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);// 发出http请求

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity());// 获取相应的字符串
                Message mMessage = mHandler.obtainMessage();
                mMessage.what = 1;
                mMessage.obj = result;
                mHandler.sendMessage(mMessage);
                System.out.println(result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
