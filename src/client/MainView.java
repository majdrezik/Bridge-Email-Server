package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import model.Email;
import server.BridgeEmailServer;
import utils.Utils;

/**
 * 
 * @author Majd Rezik
 *
 */
public class MainView implements Runnable {
	private static final String stars = "******************************************";
	private static final String showTitle = "\t\tCompose Email";
	private static final String askToEmail = "To: ";
	private static final String askFromEmail = "From: ";
	private static final String askBody = "Body: ";
	private static List<String> validVendors = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);
	private BridgeEmailServer bridgeServer = BridgeEmailServer.getInstance();

	static Semaphore sem = new Semaphore(1);

	/**
	 * MainView adds 3 registered vendors to a list for a later use.
	 */
	public MainView() {
		validVendors.add("gmail.com");
		validVendors.add("yahoo.com");
		validVendors.add("walla.co.il");
	}

	
	/**
	 * sendEmail asks the user to insert email information including "to", "from" and "email body"
	 * and tries to send the email to Email Bridge Server - tries again in case of a failure.
	 */
	public void sendEmail() {

		Email email = new Email();

		Utils.println(stars);
		Utils.println(showTitle);
		Utils.println(stars);
		askToEmail(email);
		askFromEmail(email);
		askBody(email);

		int retries = 0;
		boolean catchSendingToServerException = false;
		do {
			try {
				Utils.println("Sending email to Bridge Email Server...");
				bridgeServer.setAttributes(email);
				catchSendingToServerException = false;
			} catch (Exception ex) {
				catchSendingToServerException = true;
				if (retries++ > 2) {
					Utils.println("FATAL - Cant send email, try again later.");
					ex.printStackTrace();
				}
			}
		} while (catchSendingToServerException);
	} // END sendEmail

	/**
	 * updates the vendor's postfix in the email object - using Utils method getEmailPostfix.
	 * @param email
	 */
	private void writePostfix(Email email) {
		email.setVendorPostfix(Utils.getEmailPostfix(email.getFromEmail()));
	}

	/**
	 * checkEmail uses the regex in Utils and checks whether the inserted email is legal or not; returns true and false respectively.
	 * @param email: String
	 * @return boolean
	 */
	private boolean checkEmail(String email) {
		return Utils.isValid(email) ? true : false;
	}

	/**
	 * askFromEmail asks the user to insert the email to send from.
	 * @param email
	 */
	private void askFromEmail(Email email) {
		do {
			Utils.println(askFromEmail);
			email.setFromEmail(sc.nextLine());
		} while (!checkEmail(email.getFromEmail()));
		if (!canSendEmail(email)) {
			askFromEmail(email);
		}
	}

	/**
	 * canSendEmail checks whether the Employee can send the email via the registered vendors.
	 * @param email
	 * @return
	 */
	private boolean canSendEmail(Email email) {
		writePostfix(email);
		if (validVendors.contains(email.getVendorPostfix()))
			return true;
		Utils.println("Unregestered Vendor, try sending by a Valid Vendor <Gmail, Walla, Yahoo>");
		return false;
	}

	/**
	 * askToEmail asks the user to insert the recipient's email.
	 * @param email
	 */
	private void askToEmail(Email email) {
		do {
			Utils.println(askToEmail);
			email.setToEmail(sc.nextLine());
		} while (!checkEmail(email.getToEmail()));
	}

	/**
	 * askBody asks the user to write the email body.
	 * @param email
	 * @return
	 */
	private void askBody(Email email) {
		Utils.println(askBody);
		email.setBody(sc.nextLine());
	}

	@Override
	public void run() {
		this.sendEmail();
	}

	public static void main(String[] args) {
		String[] strArr = { "P", "Q", "R", "S", "T", "U", "V", "W" };
		Random rand = new Random();
		int res = rand.nextInt(strArr.length);
		//For each Employee dedicate a new ClientUI
		new Thread(new MainView(), "Thread-" + strArr[res]).start();
	}

}