package contactsinformations;

public class Organisation {
 	private String organization = "";
 	private String title = "";
 	public String getOrganization() {
 		return organization;
 	}
 	public void setOrganization(String organization) {
 		this.organization = "<organizationName>"+organization+"</organizationName>";
 	}
 	public String getTitle() {
 		return title;
 	}
 	public void setTitle(String title) {
 		this.title ="<title>"+ title+"</title>";
 	}
 	
 	public Organisation() {
 		
 	}
 	public Organisation(String org, String title) {
 		this.organization ="<organizationName>"+ org+"</organizationName>";
 		this.title = "<title>"+title+"</title>";
 	}
 }
