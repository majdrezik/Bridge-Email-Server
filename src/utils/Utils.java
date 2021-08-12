package utils;

import model.Email;
/**
 * 
 * @author Majd Rezik
 *
 */
public class Utils {

	///////////////////////////////////////////////////////////////////
	////////////	Helping methods to make the code clearer - Facade.
	///////////////////////////////////////////////////////////////////
	
	/**
	 * print a given string
	 * @param str
	 */
	static public void print(String str) {
		System.out.print(str);
	}
	 
	/**
	 * print a given string with a new line at the end.
	 * @param str
	 */
	static public void println(String str) {
		System.out.println(str);
	}
	
	/**
	 * regex that checks whether the given String is a valid email or not.
	 * @param email
	 * @return
	 */
	public static boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	/**
	 * printEmail prints the given email as follows: dashes - new Line - To - new Line - From - new Line - Body - new Line - dashes - new Line.
	 * @param email
	 */
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
	
	/**
	 * splitEmail splits a given email (String) into 2 parts, string before `@` and string after `@`
	 * @param email
	 * @return
	 */
	public static String[] splitEmail(String email) {
		return email.split("@");
	}
	
	/**
	 * getEmailPostfix 
	 * @param email
	 * @return returns the email postfix - the part after the `@`
	 */
	public static String getEmailPostfix(String email) {
		return splitEmail(email)[1]; //splitEmail(email) returns an array.
	}
	
	/**
	 * getEmailPostfix 
	 * @param email
	 * @return returns the email prefix - the part before the `@`
	 */
	public static String getEmailPrefix(String email) {
		return splitEmail(email)[0];
	}
	
	
}
