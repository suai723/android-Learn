package com.example.mapappdemo.push;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.example.mapappdemo.activity.MainActivity;
import com.example.mapappdemo.util.*;


/**
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * 
 * 返回值中的errorCode，解释如下： 
 *  0 - Success
 *  10001 - Network Problem
 *  30600 - Internal Server Error
 *  30601 - Method Not Allowed 
 *  30602 - Request Params Not Valid
 *  30603 - Authentication Failed 
 *  30604 - Quota Use Up Payment Required 
 *  30605 - Data Required Not Found 
 *  30606 - Request Time Expires Timeout 
 *  30607 - Channel Token Timeout 
 *  30608 - Bind Relation Not Found 
 *  30609 - Bind Number Too Many
 * 
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 * 
 */
public class MyPushMessageReceiver extends FrontiaPushMessageReceiver{

	   public static final String TAG = MyPushMessageReceiver.class
	            .getSimpleName();
	   public static final int ONMESSAGE=0;
	   public static final int ONNOTIFIACATIONCLICKED=1;
    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     * 
     * @param context
     *            BroadcastReceiver的执行Context
     * @param errorCode
     *            绑定接口返回值，0 - 成功
     * @param appid
     *            应用id。errorCode非0时为null
     * @param userId
     *            应用user id。errorCode非0时为null
     * @param channelId
     *            应用channel id。errorCode非0时为null
     * @param requestId
     *            向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
	@Override
	public void onBind(Context context, int errorCode, String appid,
            String userId, String channelId, String requestId) {
        // 绑定成功，设置已绑定flag，可以有效的减少不必要的绑定请求
        if (errorCode == 0) {
            Utils.setBind(context, true);
        }
		
	}
	
    /**
     * delTags() 的回调函数。 删除标签回调
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags
     *            成功删除的tag
     * @param failTags
     *            删除失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
	@Override
	public void onDelTags(Context context, int errorCode,
            List<String> sucessTags, List<String> failTags, String requestId) {
		
	}

    /**
     * listTags() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示列举tag成功；非0表示失败。
     * @param tags
     *            当前应用设置的所有tag。
     * @param requestId
     *            分配给对云推送的请求的id
     */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
            String requestId) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * 接收透传消息的函数。 在网页上点击发送消息 回调这个函数 不会声称通知
     * 
     * @param context
     *            上下文
     * @param message
     *            推送的消息
     * @param customContentString
     *            自定义内容,为空或者json字符串
     */
	@Override
	public void onMessage(Context context, String message,
            String customContentString) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("message", message);
		
		 if (!TextUtils.isEmpty(customContentString)) {
	            JSONObject customJson = null;
	            try {
	                customJson = new JSONObject(customContentString);
	                String level = null;
	                if (!customJson.isNull("level")) {
	                    level = customJson.getString("level");
	                    maps.put("level", level);
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        }
		updateContent(context,maps,ONMESSAGE);
	}

    /**
     * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
     * 
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串 为网页上自定义键值对
     */
	@Override
	public void onNotificationClicked(Context context, String title,
            String description, String customContentString) {
		// TODO Auto-generated method stub
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("title", title);
		maps.put("description", description);
		 if (!TextUtils.isEmpty(customContentString)) {
	            JSONObject customJson = null;
	            try {
	                customJson = new JSONObject(customContentString);
	                String level = null;
	                if (!customJson.isNull("level")) {
	                    level = customJson.getString("level");
	                    maps.put("level", level);
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        }
		 
		updateContent(context,maps,ONNOTIFIACATIONCLICKED);
	}
    /**
     * setTags() 的回调函数。 设置标签
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags
     *            设置成功的tag
     * @param failTags
     *            设置失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
	@Override
	public void onSetTags(Context context, int errorCode,
            List<String> sucessTags, List<String> failTags, String requestId) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * PushManager.stopWork() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId
     *            分配给对云推送的请求的id
     */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
        if (errorCode == 0) {
            Utils.setBind(context, false);
        }
		
	}
	
	
	
    private void updateContent(Context context, Map<String, String> maps,int comfrom) {
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Map<String, String> caches=Utils.logStringCache;
        switch (comfrom) {
		case ONMESSAGE:
			caches.clear();
			caches.put("type", "message");
			caches.put("message", maps.get("message"));
			caches.put("level", maps.get("level"));
			break;
		case ONNOTIFIACATIONCLICKED:
			caches.clear();
			caches.put("type", "notify");
			caches.put("title", maps.get("title"));
			caches.put("description", maps.get("description"));
			caches.put("level", maps.get("level"));
			break;
		default:
			break;
		} 
        context.getApplicationContext().startActivity(intent);
    }
}
