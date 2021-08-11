package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;
/**
 * 
 * @author Majd Rezik
 *
 */
public class MailManager {

	private Properties props;
	private static final MailManager manager = new MailManager();
	//public MailManager() {
	//make it private: singleton
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

	
	public static MailManager getInstance() {
		return manager;
	}
	
////////////////////////////////////////////////////////
	public String getGmailHost() {
		return props.getProperty("Gmail_Host");
	}

	public String getWallaHost() {
		return props.getProperty("Walla_Host");
	}

	public String getYahooHost() {
		return props.getProperty("Yahoo_Host");
	}

////////////////////////////////////////////////////////
	public String getGmailUsername() {
		return props.getProperty("Gmail_Username");
	}

	public String getWallaUsername() {
		return props.getProperty("Walla_Username");
	}

	public String getYahooUsername() {
		return props.getProperty("Yahoo_Username");
	}
////////////////////////////////////////////////////////

	public String getGmailPassword() {
		return props.getProperty("Gmail_Password");
	}

	public String getWallaPassword() {
		return props.getProperty("Walla_Password");
	}

	public String getYahooPassword() {
		return props.getProperty("Yahoo_Password");
	}
////////////////////////////////////////////////////////
	public String getAuthentication() {
		return props.getProperty("AUTHENTICATION");
	}
////////////////////////////////////////////////////////

	public String getEncryption() {
		return props.getProperty("ENCRYPTION");
	}
}