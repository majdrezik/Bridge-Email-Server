package interfaces;

import java.util.Properties;

import model.Email;

public interface IBridgeEmail {
	public void setAttributes(Email email) throws Exception;
	public void identifyVendor(String email) throws Exception;
	public void connect(Properties properties) throws Exception;
	public void sendEmail(Email email) throws Exception;
}
