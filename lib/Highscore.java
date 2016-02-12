/**
 * Highscore.java
 * Handles setting and getting highscores
 *
 * @author  Paul Krishnamurthy
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Highscore {

	// Read / Write to file setup
	private static final String FILE_PATH = "res/data/highscore.dat";
	private static File dataFile          = new File(FILE_PATH);
	private static Scanner dataScanner    = null;
	private static PrintWriter dataWriter = null;

	// Highscore
	private int bestScore;

	public Highscore () {

		// Load scanner with data file
		try {
			dataScanner = new Scanner(dataFile);
		} catch (IOException e) {
			System.out.println("Cannot load highscore!");
		}

		// Store highscore
		bestScore = Integer.parseInt(dataScanner.nextLine());

	}

	/**
	 * @return     Player's highscore
	 */
	public int bestScore () {
		return bestScore;
	}

	/**
	 * Sets new highscore in the data file
	 * 
	 * @param newBest     New score update
	 */
	public void setNewBest (int newBest) {

		// Set new best score
		bestScore = newBest;

		try {
			// Write new highscore to data file
			dataWriter = new PrintWriter(FILE_PATH, "UTF-8");
			dataWriter.println(Integer.toString(newBest));
			dataWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Could not set new highscore!");
		}

	}

}
