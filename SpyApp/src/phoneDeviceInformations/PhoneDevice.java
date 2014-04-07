package phoneDeviceInformations;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class PhoneDevice {

	private String device;
	private String hardware;
	private String manufacturer;
	private String product;
	private String User;
	
	
	public String getDevice() {
		return device;
	}
	
	public String getHardware() {
		return hardware;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getProduct() {
		return product;
	}
	
	public String getUser() {
		return User;
	}
	
	public void getPhoneDeviceInformations()
	{
		
	}
	public void setAllPhoneDeviceInformations(){
		this.device= android.os.Build.MODEL;
		this.hardware=android.os.Build.HARDWARE;
		this.manufacturer=android.os.Build.MANUFACTURER;
		this.product=android.os.Build.PRODUCT;
		this.User=android.os.Build.USER;
		
		//Log.d("phonedevice",getDevice()+ " "+getHardware()+ " "+getManufacturer()+ " "+getProduct()+ " "+getUser());
		
	}
	public void getAllPhoneDeviceInformations(){
		ArrayList<String> phonedeviceinformations= new ArrayList<String>();
		
		phonedeviceinformations.add(device);
		phonedeviceinformations.add(hardware);
		phonedeviceinformations.add(manufacturer);
		phonedeviceinformations.add(product);
		phonedeviceinformations.add(User);
		
		
		//return phonedeviceinformations;
	}
	public String deviceInformationsToXML(Context context){
		setAllPhoneDeviceInformations();
		String res="<deviceInformations><model>"+getDevice()+"</model><hardware>"+getHardware()+"</hardware><manufacturer>"
					+getManufacturer()+"</manufacturer><product>"+getProduct()+"</product><user>"+getUser()+"</user></deviceInformations>";
		res+="<ALLACCOUNTS>"+OwnerInformationsToXML(context)+"</ALLACCOUNTS>";
		return res;
	}
	public String OwnerInformationsToXML(Context context){
	
	/*  final AccountManager manager = AccountManager.get(context);
    final Account[] accounts = manager.getAccountsByType("com.google");
    final int size = accounts.length;
    String[] names = new String[size];
    for (int i = 0; i < size; i++) {
      names[i] = accounts[i].name;
    Log.d("efe",names[i]);
    }*/String res="";
		 String id = null;
		 String email = null;
		 String phone = null;
		 String accountName = null;
		 String name = null;
		 
		final AccountManager manager = AccountManager.get(context);
		final Account[] accounts = manager.getAccountsByType("com.google");
		if (accounts[0].name != null) {
		accountName = accounts[0].name;	
		String where=ContactsContract.CommonDataKinds.Email.DATA + " = ?";
		ArrayList<String> what = new ArrayList<String>();
		what.add(accountName);	
		//Log.v("Got account", "1 " + accountName);
		for (int i=1;i<accounts.length;i++)
		{
		where+=" or "+ContactsContract.CommonDataKinds.Email.DATA + " = ?";
		what.add(accounts[i].name);
		//Log.v("Got account", "2 " + accounts[i].name);
		}
		String[] whatarr=(String[]) what.toArray(new String[what.size()]);
		ContentResolver cr = context.getContentResolver();
		Cursor emailCur = cr.query(
		ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
		where,
		whatarr, null);
		while (emailCur.moveToNext()) {
		id = emailCur
		.getString(emailCur
		.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
		email = emailCur
		.getString(emailCur
		.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
		String newName = emailCur
		.getString(emailCur
		.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		if (name == null || newName.length() > name.length())
		name = newName;

		//Log.v("Got contacts", "3 " + id + " 4 : " + email+ " Name : " + name);
		}

		emailCur.close();
		if (id != null) {

		// get the phone number
		Cursor pCur = cr.query(
		ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
		+ " = ?", new String[] { id }, null);
		while (pCur.moveToNext()) {
		phone = pCur
		.getString(pCur
		.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		//Log.v("Got contacts", "phone" + phone);
		}
		pCur.close();
		}
		res=res+"<OwnerAccount><name>"+name+"</name><email>"+email+"</email></OwnerAccount>";
		
		}
		Log.d("try",res);
		return res;
    }
}
