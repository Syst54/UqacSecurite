package phoneDeviceInformations;

import java.util.ArrayList;

import android.R.string;

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
	}
	public ArrayList<String> getAllPhoneDeviceInformations(){
		ArrayList<String> phonedeviceinformations= new ArrayList<String>();
		
		phonedeviceinformations.add(device);
		phonedeviceinformations.add(hardware);
		phonedeviceinformations.add(manufacturer);
		phonedeviceinformations.add(product);
		phonedeviceinformations.add(User);
		
		return phonedeviceinformations;
	}
}
