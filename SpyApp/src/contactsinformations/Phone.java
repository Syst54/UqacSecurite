package contactsinformations;

public class Phone {
 	private String number;
 	private String timeContact;
 	
 	public String getNumber() {
 		return number;
 	}
 
 	public void setNumber(String number) {
 		this.number = number;
 	}
 
 	public String getTimeContact() {
 		return timeContact;
 	}
 
 	public void setType(String type) {
 		this.timeContact = type;
 	}
 
 	public Phone(String number, String timeContact) {
 		this.number ="<number>"+ number+"</number>";
 		this.timeContact = "<timeContact>"+timeContact+"</timeContact>";
 	}
 	
 }