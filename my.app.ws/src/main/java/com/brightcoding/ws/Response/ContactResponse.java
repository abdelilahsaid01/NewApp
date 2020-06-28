package com.brightcoding.ws.Response;

public class ContactResponse {
	
	private String contactId;
	private String mobile;
	private String skype;
	
	public String getContactId() {
		return contactId;
	}
	public String getMobile() {
		return mobile;
	}
	public String getSkype() {
		return skype;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}

}
