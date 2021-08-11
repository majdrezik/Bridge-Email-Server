package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import model.Email;
import server.BridgeEmailServer;
import server.MailManager;
import utils.Utils;

/**
 * 
 * @author Majd Rezik
 *
 */
public class MainView implements Runnable {
	String stars = "******************************************";
	String showTitle = "\t\tCompose Email";
	String askToEmail = "To: ";
	String askFromEmail = "From: ";
	String askBody = "Body: ";
	static List<String> validVendors = new ArrayList<>();
//	Email email = new Email();
	Scanner sc = new Scanner(System.in);
	BridgeEmailServer bridgeServer = new BridgeEmailServer();
	static List<Email> emailList = new ArrayList<>();
	static Semaphore sem1 = new Semaphore(1);
	static Semaphore sem2 = new Semaphore(1);
	Boolean isListModified = false;
	static int index = 0;
	
	public void sendEmail() {

		Email email = new Email();
		
		try {
			sem2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!isListModified) {
			validVendors.add("gmail.com");
			validVendors.add("yahoo.com");
			validVendors.add("walla.co.il");
			isListModified = true;
			sem2.release();
		}
		
		Utils.println(stars);
		Utils.println(showTitle);
		Utils.println(stars);
		email = askToEmail(email);
		email = askFromEmail(email);
		email = askBody(email);
		try { 
			Utils.println("Sending email to Bridge Email Server...");
			bridgeServer.setAttributes(email);
		} catch (Exception ex) { 
			ex.printStackTrace();
		}
	} // END sendEmail

	private Email checkEmail(Email email, String key) {

		if (!Utils.isValid(key.equalsIgnoreCase("from") ? email.getFromEmail() : email.getToEmail())) {
			Utils.println("Invalid Email, try again: ");
			if (key.equalsIgnoreCase("From"))
				email = askFromEmail(email);
			else if (key.equalsIgnoreCase("To"))
				email = askToEmail(email);
		}
		String[] parts = Utils.splitEmail(key.equalsIgnoreCase("from") ? email.getFromEmail() : email.getToEmail());
		String postfix = parts[1];
		if (!validVendors.contains(postfix) && key.equalsIgnoreCase("From")) {
			Utils.println("Unregestered Vendor, try sending by a Valid Vendor <Gmail, Walla, Yahoo>");
			email = askFromEmail(email);
		}
		return email;
	} // END checkEmail

	// get `From` field and update it in email object.
	private Email askFromEmail(Email email) {
		Utils.println(askFromEmail);
		email.setFromEmail(sc.nextLine());
		email = checkEmail(email, "From");
		return email;
	}

	// get `To` field and update it in email object.
	private Email askToEmail(Email email) {
		Utils.println(askToEmail);
		email.setToEmail(sc.nextLine());
		email = checkEmail(email, "To");
		return email;
	}

	private Email askBody(Email email) {
		Utils.println(askBody);
		email.setBody(sc.nextLine());
		return email;
	}


	@Override
	public void run() {
		this.sendEmail();
	}

	public static void main(String[] args) {
		new Thread(new MainView()).start();
	}

}