
import java.awt.*;
import javax.swing.*;
import java.io.InputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;


public class Menu extends JPanel {

	// Load images before painting
	private Sprites sp = new Sprites();

	// FlappyBird fonts
	private Font flappyFontBase, 
				 flappyFontReal, 
				 flappyMiniFont = null;

	private boolean darkTheme;
	private String randomBird;
	public int baseSpeed = 2;

	public int[] xCoords = {0, 435};

	public Menu (boolean background, String selectedBird) {

		// Try to load ttf file
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("resources/fonts/flappy-font.ttf"));
			flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

			// Header and sub-header fonts
			flappyFontReal = flappyFontBase.deriveFont(Font.PLAIN, 20);
			flappyMiniFont = flappyFontReal.deriveFont(Font.PLAIN, 15);

		} catch (Exception ex) {
			// Exit is font cannot be loaded
		    ex.printStackTrace();
		    System.err.println("Could not load Flappy Font!");
		    System.exit(-1);
		}

		darkTheme  = background;
		randomBird = selectedBird;
		System.out.println(randomBird);
	}


	/**
	 * Moves the x-coordinate of the base
	 */
	public void moveBase () {
		xCoords[0] = xCoords[0] - baseSpeed < -434 ? 430 : xCoords[0] - baseSpeed;
		xCoords[1] = xCoords[1] - baseSpeed < -434 ? 430 : xCoords[1] - baseSpeed;
	}

	public void draw(Graphics g2d) {
		// Set font and color
		g2d.setFont(flappyFontReal);
		g2d.setColor(Color.white);

		// Background
		g2d.drawImage(darkTheme ? sp.backgrounds[1] : sp.backgrounds[0], 0, 0, null);

		// Title
		g2d.drawImage(sp.titleText, 72, 100, null);

		// Buttons
		g2d.drawImage(sp.playButton, 34, 448, null);
		g2d.drawImage(sp.leaderboard, 203, 448, null);
		g2d.drawImage(sp.rateButton, 147, 355, null);

		// Moving base effect
		g2d.drawImage(sp.base, xCoords[0], 521, null);
		g2d.drawImage(sp.base, xCoords[1], 521, null);

		// Credits :p
		g2d.drawString("Created by Paul Krishnamurthy", 27, 600);
		g2d.setFont(flappyMiniFont); // Change font
		g2d.drawString("www.PaulKr.com", 115, 630);
	}

}

