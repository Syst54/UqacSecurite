package smsInformations;

public class SMS {
	
	private String contactNumber="";
	private String message="";
	private String contactName="";
	private String status="";
	private String date="";
	private String conversationID="-1";
	

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
	
	public String getConversationID() {
		return conversationID;
	}

	public void setConversationID(int conversationID) {
		this.conversationID = "<convID>"+conversationID+"</convID>";
	}

	public void setStatus(String status) {
		this.status = "<status>"+status+"</status>";
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setDate(String date) {
		this.date = "<date>"+date+"</date>";
	}
	
	public String getDate() {
		return date;
	}

}
