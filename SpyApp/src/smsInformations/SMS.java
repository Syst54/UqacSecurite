package smsInformations;

public class SMS {
	
	private String contactNumber;
	private String message;
	private String contactName;
	
	public SMS() {
		super();
	}

	public SMS(String contactNumber, String message, String contactName) {
		super();
		this.contactNumber = contactNumber;
		this.message = message;
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = "<contactNumber>"+contactNumber+"</contactNumber>";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = "<message>"+message+"</message>";
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = "<contactName>"+contactName+"</contactName>";
	}
	

}
