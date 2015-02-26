package net.learn2develop.Networking;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element; 
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainActivity extends Activity {
    ImageView img;    
    
    private class BackgroundTask extends AsyncTask<String, Void, Bitmap> {    	 
        protected Bitmap doInBackground(String... url) {        	
        	 //---download an image---
        	Bitmap bitmap = DownloadImage(url[0]);
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return bitmap;
        }       
        
        protected void onPostExecute(Bitmap bitmap) {         
        	ImageView img = (ImageView) findViewById(R.id.img);
            img.setImageBitmap(bitmap);        	
        }
	}
    
    private InputStream OpenHttpConnection(String urlString) 
    throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }	
	
    private Bitmap DownloadImage(String URL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
        	Toast.makeText(this, e1.getLocalizedMessage(), 
        			Toast.LENGTH_LONG).show();
            e1.printStackTrace();
        }
        return bitmap;                
    }
    
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];          
        try {
            while ((charRead = isr.read(inputBuffer))>0)
            {                    
                //---convert the chars to a String---
                String readString = 
                    String.copyValueOf(inputBuffer, 0, charRead);                    
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }    
        return str;        
    }
    
    private void WordDefinition(String word) {
        InputStream in = null;
        try {
            in = OpenHttpConnection("http://services.aonaware.com/DictService/DictService.asmx/Define?word=" + word);
            Document doc = null;
            DocumentBuilderFactory dbf = 
                DocumentBuilderFactory.newInstance();
            DocumentBuilder db;            
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }            
            doc.getDocumentElement().normalize(); 
            
            //---retrieve all the <Definition> nodes---
            NodeList definitionElements = 
                doc.getElementsByTagName("Definition"); 
            
            String strDefinition = "";
            for (int i = 0; i < definitionElements.getLength(); i++) { 
                Node itemNode = definitionElements.item(i); 
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) 
                {            
                    //---convert the Node into an Element---
                    Element definitionElement = (Element) itemNode;
                    
                    //---get all the <WordDefinition> elements under 
                    // the <Definition> element---
                    NodeList wordDefinitionElements = 
                        (definitionElement).getElementsByTagName(
                        "WordDefinition");
                                        
                    strDefinition = "";
                    for (int j = 0; j < wordDefinitionElements.getLength(); j++) {                    
                        //---convert a <WordDefinition> Node into an Element---
                        Element wordDefinitionElement = 
                            (Element) wordDefinitionElements.item(j);
                        
                        //---get all the child nodes under the 
                        // <WordDefinition> element---
                        NodeList textNodes = 
                            ((Node) wordDefinitionElement).getChildNodes();
                        
                        //---get the first node, which contains the text---
                        strDefinition += 
                            ((Node) textNodes.item(0)).getNodeValue() + ". ";    
                    }                    
                    //---display the title---
                    Toast.makeText(getBaseContext(),strDefinition, 
                        Toast.LENGTH_SHORT).show();
                } 
            }            
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();            
        }        
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        //---Button view---
        Button btnOpen = (Button) findViewById(R.id.Button01);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
                        	            	
            	new BackgroundTask().execute("http://www.streetcar.org/mim/cable/images/cable-01.jpg");
            
            	/*
                //---download an RSS feed---
                String str = DownloadText(
                    "http://www.appleinsider.com/appleinsider.rss");
                Toast.makeText(getBaseContext(), str, 
                               Toast.LENGTH_SHORT).show();
                
                //---access a Web service using GET---
                WordDefinition("Apple");
                */
            	
            }
        });
    }
}






