package contactsinformations;

public class Organisation {
 	private String organization = "";
 	private String title = "";
 	
 	public String getOrganization() {
 		return organization;
 	}
 	public void setOrganization(String organization) {
 		if (organization!=null && organization.length()>0)
 			this.organization = "<organizationName>"+ organization+"</organizationName>";
 	}
 	public String getTitle() {
 		return title;
 	}
 	public void setTitle(String title) {
 		if (title!=null && title.length()>0)
 			this.title = "<title>"+title+"</title>";
 	}
 	
 	public Organisation() {
 		
 	}
 	public Organisation(String org, String title) {
 		setTitle(title);
 		setOrganization(org);
 	}
 }
