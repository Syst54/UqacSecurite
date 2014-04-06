package browserInformations;

public class BrowserPage {

	private String title;
	private String Url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public BrowserPage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BrowserPage(String title, String url) {
		super();
		this.title ="<title>"+ title+"</title>";
		Url = "<url>"+url+"</url>";
	}
	
}
