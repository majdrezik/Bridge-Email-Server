package interfaces;

import java.util.Properties;

import model.Email;
/**
 * 
 * @author Majd Rezik
 *
 */
public interface IBridgeEmail {
	/**
	 * setAttributes receives email from the employee and saves it in the Bridge Server.
	 * And checks whether the email is valid or not.
	 * @param email
	 * @throws Exception
	 */
	public void setAttributes(Email email) throws Exception;
	
	/**
	 * identifyVendor identifies the vendor by the postfix of the given email.
	 * And tries to connect to the appropriate server.
	 * @param email
	 * @throws Exception
	 */
	public void identifyVendor(String email) throws Exception;
	
	/**
	 * connect imitates connecting to the server by sleeping 2 seconds.
	 * And calls sendEmail to send the email after connecting to the server.
	 * @param properties
	 * @throws Exception
	 */
	public void connect(Properties properties) throws Exception;
	
	/**
	 * sendEmail imitates sending a real email by sleeping 3 seconds and at the end prints the email.
	 * @param email
	 * @throws Exception
	 */
	public void sendEmail(Email email) throws Exception;
}
