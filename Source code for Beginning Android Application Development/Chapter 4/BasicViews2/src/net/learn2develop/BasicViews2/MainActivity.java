package net.learn2develop.BasicViews2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	private static int progress;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        progress = 0;
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(200);
        
        //---do some work in background thread---
        new Thread(new Runnable() 
        {
            public void run() 
            {
                //---do some work here---
                while (progressStatus < 200) 
                {
                    progressStatus = doSomeWork();
                    //---Update the progress bar---
                    handler.post(new Runnable() 
                    {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
 
                //---hides the progress bar---
                handler.post(new Runnable() 
                {
                    public void run() 
                    {
                        //---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                        progressBar.setVisibility(8);
                    }
                });
            }    
 
            //---do some long lasting work here---
            private int doSomeWork() 
            {
                try {
                    //---simulate doing some work---
                    Thread.sleep(50);
                } catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
                return ++progress;
            } 
        }).start();  
    }
}