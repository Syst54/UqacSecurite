package contactsinformations;

import java.util.ArrayList;



import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class Contact {
	private String id;
	private String displayName;
	private ArrayList<Phone> phone;
	private ArrayList<String> email;

	private ArrayList<Adresse> addresses = new ArrayList<Adresse>();
	
	private contactsinformations.Organization organization;
 	
	
	public Contact() {
		super();
	}
	
	public contactsinformations.Organization getOrganization() {
		return organization;
	}
	public void setOrganization(contactsinformations.Organization organization) {
		this.organization = organization;
	}
	
	public ArrayList<Adresse> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<Adresse> addresses) {
		this.addresses = addresses;
	}
	public void addAddress(Adresse adresse) {
		this.addresses.add(adresse);
	}
	public ArrayList<String> getEmail() {
		return email;
	}
	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}
	public void addEmail(String mail){
		this.email.add(mail);
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
	
	
}
