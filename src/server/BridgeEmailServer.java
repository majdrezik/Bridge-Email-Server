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

	private Email email;
	private String postfix, prefix;
	private MailManager mail = new MailManager();
	Properties serverProperties = new Properties(); // for sending email
	static Semaphore sem = new Semaphore(1);

	@Override
	public void setAttributes(Email email) throws Exception {

		// check that both Emails (ToEmail & FromEmail) are valid.
		if (!Utils.isValid(email.getFromEmail()))
			throw new Exception("Invalid Email.");
		if (!Utils.isValid(email.getToEmail()))
			throw new Exception("Invalid Email.");

		this.email = email;

		identifyVendor(email.getFromEmail());
	}

	@Override
	public void identifyVendor(String email) throws Exception {
		String username, password;

		serverProperties.put("mail.smtp.auth", mail.getAuthentication());// authentication
		serverProperties.put("mail.smtp.starttls.enable", mail.getEncryption()); // tls encryption

		String[] parts = email.split("@");
		prefix = parts[0];
		postfix = parts[1];
		switch (postfix) {
		case "gmail.com":
			serverProperties.put("mail.smtp.host", mail.getGmailHost()); // host: Gmail
			username = mail.getGmailUsername();
			password = mail.getGmailPassword();
			this.email.setVendorName("Gmail");
			break;
		case "walla.co.il":
			serverProperties.put("mail.smtp.host", mail.getWallaHost()); // host: Gmail
			username = mail.getWallaUsername();
			password = mail.getWallaPassword();
			this.email.setVendorName("Walla");
			break;
		case "yahoo.com":
			serverProperties.put("mail.smtp.host", mail.getYahooHost()); // host: Gmail
			username = mail.getYahooUsername();
			password = mail.getYahooPassword();
			this.email.setVendorName("Yahoo");
			break;

		default:
			Utils.println("Email postfix invalid (" + postfix + ")");
			System.exit(1);
		}
		try {
			connect(serverProperties);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void connect(Properties properties) throws Exception {
		// Imitate sending the email by sleeping.
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		Utils.println(prefix + " Connecting...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		try {
			sendEmail(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void sendEmail(Email email) throws Exception {

		Utils.println("Sending Email to " + email.getToEmail() + " via " + email.getVendorName() + " by "
				+ email.getFromEmail());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		Utils.println("Email Sent.");
		sem.acquire();
		Utils.printEmail(email);
		sem.release();
	}

} // END BridgeEmailServer
