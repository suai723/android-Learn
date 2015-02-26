package net.learn2develop.WebView;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        WebView wv = (WebView) findViewById(R.id.webview1);          
        wv.loadUrl("file:///android_asset/Index.html");        
        
        /*
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<H1>A simple HTML page</H1><body>" +
            "<p>The quick brown fox jumps over the lazy dog</p>"; 
        wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        */        
        
        /*
        wv.setWebViewClient(new Callback());
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        wv.loadUrl("http://ecx.images-amazon.com/images/I/41HGB-W2Z8L._SL500_AA300_.jpg");
        */                
        //wv.loadUrl("http://www.wrox.com");        
    }
    
    private class Callback extends WebViewClient {
    	@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return(false);
		}
	}	
}