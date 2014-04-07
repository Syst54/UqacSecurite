package phoneDeviceInformations;

import java.util.ArrayList;

import android.R.string;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
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
	public String deviceInformationsToXML(){
		setAllPhoneDeviceInformations();
		String res="<deviceInformations><model>"+getDevice()+"</model><hardware>"+getHardware()+"</hardware><manufacturer>"
					+getManufacturer()+"</manufacturer><product>"+getProduct()+"</product><user>"+getUser()+"</user></deviceInformations>";
		return res;
	}
	public void OwnerInformations(Context context){
	
	  final AccountManager manager = AccountManager.get(context);
    final Account[] accounts = manager.getAccountsByType("com.google");
    final int size = accounts.length;
    String[] names = new String[size];
    for (int i = 0; i < size; i++) {
      names[i] = accounts[i].name;
    Log.d("efe",names[i]);
    }
    
    }
}
