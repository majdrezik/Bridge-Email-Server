package utils;

import model.Email;
/**
 * 
 * @author Majd Rezik
 *
 */
public class Utils {

	static public void print(String str) {
		System.out.print(str);
	}
	 
	static public void println(String str) {
		System.out.println(str);
	}

	public static boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	public static void printEmail(Email email) {
		String dashes = "------------------------------------------";
		System.out.println();
		System.out.println(dashes);
		System.out.println("\t\tTo: " + email.getToEmail());
		System.out.println("\t\tFrom: " + email.getFromEmail());
		System.out.println("\t\tBody: " + email.getBody());
		System.out.println(dashes);
		System.out.println();
	}
	
	public static String[] splitEmail(String email) {
		return email.split("@");
	}
}
