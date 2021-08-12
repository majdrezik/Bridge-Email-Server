package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * 
 * @author Majd Rezik
 *
 */
public class MailManager {

	private Properties props;
	private static final MailManager manager = new MailManager();

	/**
	 * MailManager loads the data from file mail.config which has the information about each server.
	 */
	private MailManager() {
		try {
			props = new Properties();
			FileInputStream input = new FileInputStream("src/mail.config");
			props.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Singelton - return one instance.
	 * @return
	 */
	public static MailManager getInstance() {
		return manager;
	}
	
////////////////////////////////////////////////////////
//////// 	helping methods to get the data from mail.config file
///////		in order to prevent from compiling the code again if values need to be changed.
////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * @return gmail Host name
	 */
	public String getGmailHost() {
		return props.getProperty("Gmail_Host");
	}

	/**
	 * 
	 * @return Walla Host name
	 */
	public String getWallaHost() {
		return props.getProperty("Walla_Host");
	}

	/**
	 * 
	 * @return Yahoo Host name
	 */
	public String getYahooHost() {
		return props.getProperty("Yahoo_Host");
	}

////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @return Gmail username
	 */
	public String getGmailUsername() {
		return props.getProperty("Gmail_Username");
	}
	/**
	 * 
	 * @return Walla username
	 */
	public String getWallaUsername() {
		return props.getProperty("Walla_Username");
	}

	/**
	 * 
	 * @return Yahoo username
	 */
	public String getYahooUsername() {
		return props.getProperty("Yahoo_Username");
	}
////////////////////////////////////////////////////////

	/**
	 * 
	 * @return Gmail user password
	 */
	public String getGmailPassword() {
		return props.getProperty("Gmail_Password");
	}

	/**
	 * 
	 * @return Walla user password
	 */
	public String getWallaPassword() {
		return props.getProperty("Walla_Password");
	}

	/**
	 * 
	 * @return Yahoo user password
	 */
	public String getYahooPassword() {
		return props.getProperty("Yahoo_Password");
	}
////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @return Authentication (true/false)
	 */
	public String getAuthentication() {
		return props.getProperty("AUTHENTICATION");
	}
////////////////////////////////////////////////////////

	/**
	 * 
	 * @return Encryption (true/false)
	 */
	public String getEncryption() {
		return props.getProperty("ENCRYPTION");
	}
}