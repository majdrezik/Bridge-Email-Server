package server;

import java.util.Properties;
import java.util.concurrent.Semaphore;

import interfaces.IBridgeEmail;
import model.Email;
import utils.Utils;

/**
 * 
 * @author Majd Rezik
 *
 */
public class BridgeEmailServer implements IBridgeEmail {

	// singleton - Eager initialization
	private final static BridgeEmailServer serverInstance = new BridgeEmailServer();

	// singleton - Eager initialization
	private MailManager mailManager = MailManager.getInstance();
	private static Semaphore sem = new Semaphore(1); // for printing

	private BridgeEmailServer() {
	}

	// Singleton
	public static BridgeEmailServer getInstance() {
		return serverInstance;
	}

	@Override
	/**
	 * setAttributes receives email from the employee and saves it in the Bridge
	 * Server. And checks whether the email is valid or not.
	 * 
	 * @param email
	 * @throws Exception
	 */
	public void setAttributes(Email email) throws Exception {

		Email _email;
		// check that both Emails (ToEmail & FromEmail) are valid.
		if (!Utils.isValid(email.getFromEmail()))
			throw new Exception("Error : Invalid Email.");
		if (!Utils.isValid(email.getToEmail()))
			throw new Exception("Error : Invalid Email.");
		_email = email;

		try {
			identifyVendor(_email);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	/**
	 * identifyVendor identifies the vendor by the postfix of the given email. And
	 * tries to connect to the appropriate server twice (in case of receiving
	 * exception). and on the 3rd time it throws back the exception.
	 * 
	 * @param email
	 * @throws Exception
	 * @return void
	 */
	public void identifyVendor(Email email) throws Exception {
		String username = null, password = null;
		String postfix, prefix;
		Properties serverProperties = new Properties(); // for sending email

		// Authentication and Encryption are always `true` no matter which server.
		serverProperties.put("mail.smtp.auth", mailManager.getAuthentication());// authentication
		serverProperties.put("mail.smtp.starttls.enable", mailManager.getEncryption()); // tls encryption

		prefix = Utils.getEmailPrefix(email.getFromEmail());
		postfix = Utils.getEmailPostfix(email.getFromEmail());
		switch (postfix) {
		case "gmail.com":
			// update the server properties with the right Host name, username and password.
			serverProperties.put("mail.smtp.host", mailManager.getGmailHost()); // host: Gmail
			username = mailManager.getGmailUsername();
			password = mailManager.getGmailPassword();
			email.setVendorName("Gmail");
			break;
		case "walla.co.il":
			// update the server properties with the right Host name, username and password.
			serverProperties.put("mail.smtp.host", mailManager.getWallaHost()); // host: Gmail
			username = mailManager.getWallaUsername();
			password = mailManager.getWallaPassword();
			email.setVendorName("Walla");
			break;
		case "yahoo.com":
			// update the server properties with the right Host name, username and password.
			serverProperties.put("mail.smtp.host", mailManager.getYahooHost()); // host: Gmail
			username = mailManager.getYahooUsername();
			password = mailManager.getYahooPassword();
			email.setVendorName("Yahoo");
			break;

		default:
			Utils.println("Email postfix invalid (" + postfix + ")");
			System.exit(1);
		}

		serverProperties.put("username", username);
		serverProperties.put("password", password);
		serverProperties.put("prefix", prefix);
		serverProperties.put("email", email);
		int retries = 0;
		boolean catchTimeOut = false;

		// try 3 times to connect to server after timeOut. if still can't connect, throw
		// exception.
		do {
			try {
				connect(serverProperties);
				catchTimeOut = false;
			} catch (Exception ex) {
				catchTimeOut = true;
				if (retries++ > 2)
					throw ex;
			}
		} while (catchTimeOut);
	}

	@Override
	/**
	 * connect imitates connecting to the server by sleeping 2 seconds. And calls
	 * sendEmail to send the email after connecting to the server.
	 * 
	 * @param properties
	 * @throws Exception
	 * @return void
	 */
	public void connect(Properties properties) throws Exception {
		// Imitate sending the email by sleeping.
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Utils.println(properties.getProperty("prefix") + " is connecting...");
		try {
			Thread.sleep(500);
			sendEmail((Email) properties.get("email"));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	/**
	 * sendEmail imitates sending a real email by sleeping 3 seconds and at the end
	 * prints the email.
	 * 
	 * @param email
	 * @throws Exception
	 * @return void
	 */
	public void sendEmail(Email email) throws Exception {

		Utils.println("Sending Email to " + email.getToEmail() + " via " + email.getVendorName() + " by "
				+ email.getFromEmail());

		try {
			Thread.sleep(3000);
			Utils.println("Email Sent.");
		} catch (InterruptedException e) {
			throw e;
		}
		sem.acquire();
		Utils.println(Thread.currentThread().getName());
		Utils.printEmail(email);
		sem.release();

	} // END sendEmail.

} // END BridgeEmailServer.
