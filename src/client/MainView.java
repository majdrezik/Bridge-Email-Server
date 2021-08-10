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
	List<String> validVendors = new ArrayList<>();
	Email email = new Email();
	Scanner sc = new Scanner(System.in);
	BridgeEmailServer bridgeServer = new BridgeEmailServer();
	static List<Email> emailList = new ArrayList<>();
	static Semaphore sem1 = new Semaphore(1);
	static Semaphore sem2 = new Semaphore(1);

	static int index = 0;

	public void sendEmail() { 
		validVendors.add("gmail.com");
		validVendors.add("yahoo.com"); 
		validVendors.add("walla.co.il");
 
		Utils.println(stars);
		Utils.println(showTitle); 
		Utils.println(stars);
		askToEmail();
		askFromEmail(); 
		askBody(); 
		try { // the thread of bridgeEmail must wait until this thread finishes.
			Utils.println("Sending email to Bridge Email Server...");
			bridgeServer.setAttributes(email);
		}
		catch (Exception ex) { // TODO Auto-generated catch block
			ex.printStackTrace(); 
			}
		} //END sendEmail

	private void checkEmail(String email, String key) {

		if (!Utils.isValid(email)) {
			Utils.println("Invalid Email, try again: ");
			if (key.equalsIgnoreCase("From"))
				askFromEmail();
			else if (key.equalsIgnoreCase("To"))
				askToEmail();
		}
		String[] parts = Utils.splitEmail(email);
		String postfix = parts[1];
		if (!validVendors.contains(postfix) && key.equalsIgnoreCase("From")) {
			Utils.println("Unregestered Vendor, try sending by a Valid Vendor <Gmail, Walla, Yahoo>");
			askFromEmail();
		}
	} //END checkEmail

	// get `From` field and update it in email object.
	private void askFromEmail() {
		Utils.println(askFromEmail);
		email.setFromEmail(sc.nextLine());
		checkEmail(email.getFromEmail(), "From");
	}

	// get `To` field and update it in email object.
	private void askToEmail() {
		Utils.println(askToEmail);
		email.setToEmail(sc.nextLine());
		checkEmail(email.getToEmail(), "To");
	}

	private void askBody() {
		Utils.println(askBody);
		email.setBody(sc.nextLine());
	}

	public void sendEmailThread(Email email) {
		try {
			bridgeServer.setAttributes(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			sem1.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Email email = emailList.get(index++);
		sem1.release();
		sendEmailThread(email);
	}

	public void illustrateThreads() {
		String[] toList = new String[10];
		String[] fromList = new String[10];
		int i = 0;
		for (; i < 4; i++) {
			toList[i] = "to" + i + "@gmail.com";
			fromList[i] = "from" + i + "@gmail.com";
			emailList.add(new Email(toList[i], fromList[i], "@gmail.com", "Body " + i));
		}
		for (; i < 7; i++) {
			toList[i] = "to" + i + "@walla.co.il";
			fromList[i] = "from" + i + "@walla.co.il";
			emailList.add(new Email(toList[i], fromList[i], "@gmail.com", "Body " + i));
		}

		for (; i < 10; i++) {
			toList[i] = "to" + i + "@yahoo.com";
			fromList[i] = "from" + i + "@yahoo.com";
			emailList.add(new Email(toList[i], fromList[i], "@gmail.com", "Body " + i));
		}

		for (i = 0; i < emailList.size(); i++) {
			new Thread(new MainView()).start();
		}
	}

	
	public static void main(String[] args) {
		MainView main = new MainView();
		main.sendEmail(); 			//manual email.
		
		//main.illustrateThreads();		// automatic emails.
		
	}
	
}