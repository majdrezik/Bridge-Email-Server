package model;

public class Email {
	private String toEmail;
	private String fromEmail;
	private String vendorPostfix;
	private String body;
	private String vendorName;

	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public Email() {
		
	}
	
	
	public Email(String toEmail, String fromEmail, String vendorPostfix, String body) {
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
		this.vendorPostfix = vendorPostfix;
		this.body = body;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getVendorPostfix() {
		return vendorPostfix;
	}

	public void setVendorPostfix(String vendorPostfix) {
		this.vendorPostfix = vendorPostfix;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
