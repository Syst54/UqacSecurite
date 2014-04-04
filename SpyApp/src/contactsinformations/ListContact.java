package contactsinformations;

import java.util.ArrayList;



import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ListContact {
	
	private String WhosContactList;
	private ArrayList<Contact> ContactList;
	
	
	public ListContact() {
		
	}

	public String getWhosContactList() {
		return WhosContactList;
	}

	public void setWhosContactList(String whosContactList) {
		WhosContactList = whosContactList;
	}

	public ArrayList<Contact> getContactList() {
		return ContactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		ContactList = contactList;
	}

	
	public void CreateListContactFromPhone(Context context){
		
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null); 
		
		while (cursor.moveToNext()) { 
			// Création d'un nouveau contact
			Contact contact=new Contact();
			// Récupération de l'id
		   String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); 
		   contact.setId(contactId);
		   
		   String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)); 
		   if (Boolean.parseBoolean(hasPhone)) { 
		      // You know it has a number so now query it like this
		      Cursor phones = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null); 
		      while (phones.moveToNext()) { 
		         String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));                 
		      String timeContact = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
		      //Ajout des téléphones au contact
		      Phone phone=new Phone(phoneNumber, timeContact);
		      contact.addPhone(phone);
		      Log.d("phone",phoneNumber+timeContact);
		      } 
		      phones.close(); 
		   }

		   Cursor emails =cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null); 
		   // Ajout du nom du contact
		   String name= emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		   contact.setDisplayName(name);
		   
		   while (emails.moveToNext()) { 
		      // Ajout des emails au contact
		      String emailAddress = emails.getString( 
		      emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)); 
		      Log.d("mail",emailAddress);
		      contact.addEmail(emailAddress);
		   } 
		   
		   emails.close();
		   // Ajout des adresses au contact
		   String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
           String[] addrWhereParams = new String[]{contactId,
               ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
           Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                       null, null, null, null);
           while(addrCur.moveToNext()) {
               String poBox = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
               String street = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
               String city = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
               String state = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
               String postalCode = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
               String country = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
               String type = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
Log.d("adresse", poBox+street+city+state+postalCode+country+type);
             Adresse  adresse= new Adresse(poBox, street,city, state,postalCode,country,type);
             contact.addAddress(adresse);
           }
           addrCur.close();
           // Ajout de l'organisation au contact
           String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
           String[] orgWhereParams = new String[]{contactId,
               ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
           Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                       null, orgWhere, orgWhereParams, null);
           if (orgCur.moveToFirst()) {
               String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
               String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
           Organization organization=new Organization(orgName, title);
           contact.setOrganization(organization);
           Log.d("organisation",orgName+title);
           }
           orgCur.close();
           ContactList.add(contact);
		}
		
		cursor.close(); 
		 
	}
}
