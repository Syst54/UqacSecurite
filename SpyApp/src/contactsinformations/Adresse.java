package contactsinformations;

public class Adresse {
	
	private String num;

private String street;
private String city;
private String state;

private String postalCode;
private String country;
private String type;


public Adresse(String num, String street, String city, String state,
		String postalCode, String country, String type) {
	super();
	this.num = num;
	this.street = street;
	this.city = city;
	this.state = state;
	this.postalCode = postalCode;
	this.country = country;
	this.type = type;
}
public Adresse() {
	super();
}
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getPostalCode() {
	return postalCode;
}
public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
}
