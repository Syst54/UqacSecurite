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
	
	private Organisation organisation;
 	
	
	public Contact() {
		super();
	}
	
	public Organisation getOrganization() {
		return organisation;
		
	}
	public void setOrganization(Organisation organisation) {
		this.organisation = organisation;
	}
	
	public boolean IsOrganisation(){
		return !(organisation==null || (organisation.getTitle().length()==0 && organisation.getOrganization().length()==0));
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
 		this.id = "<id>"+id+"</id>";
	}
	public String getDisplayName() {
		if(displayName.equals(null))
			return "";
		else
		return displayName;
	}
	public void setDisplayName(String dName) {
		this.displayName ="<DisplayName>"+ dName+"</DisplayName>";
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
	
	public String GetContactInformations(){
		String res="";
		String phone="";
		String email="";
		String organization="";
		//int tailleListPhone=getPhone().size();
		for(int i=0;i<getPhone().size();i++)
		{
			phone=phone+"<phone>"+getPhone().get(i).getNumber()+ getPhone().get(i).getNumber()+"</phone>";
		}
		for(int j=0;j<getEmail().size();j++)
		{
			email=email+getEmail().get(j);
		}
		if(IsOrganisation())
		organization="<organization>"+getOrganization().getOrganization()+getOrganization().getTitle()+"</organization>";		
		else
			organization=""; //"<organization></organization>";

		
		res="<contact>"+getId()+getDisplayName()+phone+email+organization+"</contact>";
		return res;
		
	}
	
}
