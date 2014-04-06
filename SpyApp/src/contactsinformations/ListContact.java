package contactsinformations;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
		ArrayList<Contact>contactList=new ArrayList<Contact>();

		
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null); 
		
		while (cursor.moveToNext()) { 
			ArrayList<String>emailsList=new ArrayList<String>();
			
			ArrayList<Phone>phoneList= new ArrayList<Phone>();
			ArrayList<Adresse> adresseList = new ArrayList<Adresse>();
			ArrayList<String> SMSList= new ArrayList<String>();
			// Création d'un nouveau contact
			Contact contact=new Contact();
			// Récupération de l'id
		   String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); 
		   contact.setId(contactId);
		   String Displayname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		   contact.setDisplayName(Displayname);


		    
		   String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)); 
		 // Log.d("name","id :"+contactId+" "+"name :"+Displayname);
		   // if (Boolean.parseBoolean(hasPhone)) { 
		      // You know it has a number so now query it like this
		      Cursor phones = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null); 
		      while (phones.moveToNext()) { 
		         String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));                 
		      String timeContact = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
		      //Ajout des téléphones au contact
		      Phone phone=new Phone(phoneNumber, timeContact);
		      phoneList.add(phone);
		    
		    //  Log.d("phone","phone :"+phoneNumber+" timeContact :"+timeContact);
		      } 
		      phones.close(); 
		   //}

		   Cursor emails =cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null); 
		   // Ajout du nom du contact
		   //String name= emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		   //contact.setDisplayName(name);
		   
		   while (emails.moveToNext()) { 
		      // Ajout des emails au contact
		      String emailAddress ="<email>"+ emails.getString( 
		      emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))+"</email>"; 
		    //  Log.d("mail","mail :"+emailAddress);
		     // contact.addEmail(emailAddress);
		    emailsList.add(emailAddress);
		   } 
		   
		   emails.close();
		   // Ajout des adresses au contact
		   String num="";
		   String street = "";
		   String city = "";
		   String state = "";
		   String postcode = "";
		   String country = "";
		   
		   
		   Cursor addCur = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?",  new String[]{ContactsContract.Contacts._ID}, null); 
           while (addCur.moveToNext()) { 
        	   		//num=addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.));
                    /*street = addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                    city = addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                    state = addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                    postcode = addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                    country = addCur.getString(addCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
           */
                    Adresse adresse = new Adresse(num,street, city,state, postcode, country, "none");
                    adresseList.add(adresse);
                    Log.d("addr",num);
           } 
           addCur.close();
           // Ajout de l'organisation au contact
           String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
           String[] orgWhereParams = new String[]{contactId,
               ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
           Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                       null, orgWhere, orgWhereParams, null);
           String orgName="";
           String title ="";
           if (orgCur.moveToFirst()) {
                orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
          
          // Log.d("organisation","org name :"+orgName+" title :"+title);
           }
           Organisation organization=new Organisation(orgName, title);
           contact.setOrganization(organization);
           orgCur.close();
           contact.setEmail(emailsList);
           contact.setPhone(phoneList);
           contact.setAddresses(adresseList);
           
           contactList.add(contact);
           
           //Log.d("contact","name :"+contact.getDisplayName()+" phone :"+contact.getPhone().get(0).getNumber().toString());
          
		}
		
		cursor.close(); 
		setContactList(contactList);
		 
	}
	public String GetAllContactsInformationsToXML(Context context){
		
		CreateListContactFromPhone(context);
		
		String allContactsInformations="";
		for(int i=0;i<ContactList.size();i++)
		{
			allContactsInformations=allContactsInformations+ContactList.get(i).GetContactInformations();
		}
		//Log.d("abc",allContactsInformations);
		return allContactsInformations;
	}
	
	
}
