/**
 * Helper.java
 * Helper class with various tools
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.Desktop;
import java.net.URI;

public class Helper {

	/**
	 * Tries to open url in default web browser
	 * 
	 * @param url     Destination URL
	 */
	public static void openURL (String url) {

		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Sorry could not open URL...");
		}

	}

}