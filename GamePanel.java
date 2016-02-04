/**
 * GamePanel.java
 * Main game panel
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.event.KeyEvent.*;
import java.util.HashMap;
import java.io.InputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel implements Globals, KeyListener, MouseListener {

	private Random rand = new Random();
	private Calendar cal;

	// Fonts
	private Font flappyFontBase, 
				 flappyFontReal, 
				 flappyScoreFont,
				 flappyMiniFont = null;

	// Textures
	private HashMap<String, Texture> textures = new Sprites().getGameTextures();

	// Moving base effect
	private static int baseSpeed      = 1;
	private static int[] baseCoords   = {0, 435};
	private static Audio audio        = new Audio();
	
	public boolean ready              = false;             // If game is loaded
	private int gameState             = GAME;              // Game screen state
	private boolean keys[]            = new boolean[256];  // Array of pressed keys
	private boolean inStartGameState  = true;              // To show instructions scren
	private Point clickedPoint        = new Point(-1, -1); // Store point when player clicks 
	private int score                 = 0;                 // Current game score
	
	private final int STARTING_BIRD_X = 90;
	private final int STARTING_BIRD_Y = 343;
	private final Bird menuBird;
	private final boolean darkTheme;
	private final String randomBird;


	public GamePanel () {

		// Try to load ttf file
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("resources/fonts/flappy-font.ttf"));
			flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

			// Header and sub-header fonts
			flappyScoreFont = flappyFontBase.deriveFont(Font.PLAIN, 50);
			flappyFontReal  = flappyFontBase.deriveFont(Font.PLAIN, 20);
			flappyMiniFont  = flappyFontBase.deriveFont(Font.PLAIN, 15);

		} catch (Exception ex) {

			// Exit is font cannot be loaded
			ex.printStackTrace();
			System.err.println("Could not load Flappy Font!");
			System.exit(-1);
		}

		// Get current hour with Calendar
		// If it is past noon, use the dark theme
		cal = Calendar.getInstance();
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);

		boolean dark = currentHour > 12;

		String[] birds = new String[] {
			"yellow",
			"blue",
			"red"
		};

		// Set random scene assets
		darkTheme = dark;
		randomBird = birds[rand.nextInt(3)];

		menuBird = new Bird(randomBird, 172, 250);

		// Set all keys to false
		for (int i = 0; i < 256; i++) {
			keys[i] = false;
		}

		// Input listeners
		addKeyListener(this);
		addMouseListener(this);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}

	/**
	 * Checks if point is in rectangle
	 * 
	 * @param      r     Rectangle
	 * @return           Boolean if point collides with rectangle
	 */
	private boolean isTouching (Rectangle r) {
		return r.contains(clickedPoint);
	}

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		// Set font and color
		g.setFont(flappyFontReal);
		g.setColor(Color.white);

		// Move base
		baseCoords[0] = baseCoords[0] - baseSpeed < -435 ? 435 : baseCoords[0] - baseSpeed;
		baseCoords[1] = baseCoords[1] - baseSpeed < -435 ? 435 : baseCoords[1] - baseSpeed;

		// Paint constant items
		constantItems(g);

		switch (gameState) {

			case MENU:
				drawMenu(g);

				menuBird.menuFloat();

				break;

			case LEADERBOARD:
				System.out.println("LEADERBOARD");
				break;

			case GAME:
				
				drawScore(g); // Draw player score

				// Start at instructions state
				if (inStartGameState) {
					startGameScreen(g);

				} else {
					// Start game
					drawBird(g);
					menuBird.inGame();
				}

				break;

		}

	}

	// All game drawing methods


	/**
	 * Draws a string centered based on given restrictions
	 * 
	 * @param s     String to be drawn
	 * @param w     Constraining width
	 * @param h     Constraining height
	 * @param y     Fixed y-coordiate
	 */
	public void drawCentered (String s, int w, int h, int y, Graphics g) {
		FontMetrics fm = g.getFontMetrics();

		// Calculate x-coordinate based on string length and width
		int x = (w - fm.stringWidth(s)) / 2;
		g.drawString(s, x, y);
	}

	/**
	 * Draws items that stay no matter what the scene is
	 */
	public void constantItems (Graphics g2d) {

		// Background
		g2d.drawImage(darkTheme ? textures.get("background2").getImage() : 
			textures.get("background1").getImage(), 0, 0, null);

		// Moving base effect
		g2d.drawImage(textures.get("base").getImage(), baseCoords[0], textures.get("base").getY(), null);
		g2d.drawImage(textures.get("base").getImage(), baseCoords[1], textures.get("base").getY(), null);

		// Draw bird
		drawBird(g2d);

	}

	////////////////
	// Menuscreen //
	////////////////

	private void drawMenu (Graphics g2d) {

		// Title
		g2d.drawImage(textures.get("titleText").getImage(), 
			textures.get("titleText").getX(), 
			textures.get("titleText").getY(), null);

		// Buttons
		g2d.drawImage(textures.get("playButton").getImage(),
			textures.get("playButton").getX(),
			textures.get("playButton").getY(), null);
		g2d.drawImage(textures.get("leaderboard").getImage(),
			textures.get("leaderboard").getX(),
			textures.get("leaderboard").getY(), null);
		g2d.drawImage(textures.get("rateButton").getImage(),
			textures.get("rateButton").getX(),
			textures.get("rateButton").getY(), null);

		// Credits :p
		drawCentered("Created by Paul Krishnamurthy", 375, 667, 600, g2d);
		g2d.setFont(flappyMiniFont); // Change font
		drawCentered("www.PaulKr.com", 375, 667, 630, g2d);

	}

	/**
	 * Draws bird
	 */
	private void drawBird (Graphics g2d) {

		switch (menuBird.color) {
			case "yellow":
				Animation.animate(g2d,
				new BufferedImage[] {
					textures.get("yellowBird1").getImage(),
					textures.get("yellowBird2").getImage(), 
					textures.get("yellowBird3").getImage()
				},
				menuBird.x, menuBird.y, .09);
				break;

			case "blue":
				Animation.animate(g2d,
				new BufferedImage[] {
					textures.get("blueBird1").getImage(),
					textures.get("blueBird2").getImage(), 
					textures.get("blueBird3").getImage()
				},
				menuBird.x, menuBird.y, .09);
				break;

			case "red":
				Animation.animate(g2d,
				new BufferedImage[] {
					textures.get("redBird1").getImage(),
					textures.get("redBird2").getImage(), 
					textures.get("redBird3").getImage()
				},
				menuBird.x, menuBird.y, .09);
				break;
		}
	}

	/////////////////
	// Game screen //
	/////////////////

	public void startGameScreen (Graphics g2d) {

		menuBird.x = STARTING_BIRD_X;
		menuBird.y = STARTING_BIRD_Y;

		// Get ready text
		g2d.drawImage(textures.get("getReadyText").getImage(),
			textures.get("getReadyText").getX(),
			textures.get("getReadyText").getY(), null);

		// Instructions image
		g2d.drawImage(textures.get("instructions").getImage(), 
			textures.get("instructions").getX(),
			textures.get("instructions").getY(), null);

	}

	public void drawScore (Graphics g2d) {
		// System.out.println(score);

		// g2d.setFont(flappyScoreFont);
		// g2d.drawString(score + "", 100, 100);

		// Graphics2D g2 = (Graphics2D) g2d;

		// FontRenderContext frc = g2.getFontRenderContext();
		// TextLayout textTl = new TextLayout(score + "", flappyScoreFont, frc);
		// AffineTransform transform = new AffineTransform();
		// Shape outline = textTl.getOutline(null);
		// Rectangle outlineBounds = outline.getBounds();
		// transform = g2.getTransform();

		// transform.translate(100, 100);

		// g2.transform(transform);
		// g2.setColor(Color.black);
		// g2.draw(outline);
		// g2.setClip(outline);
	
		char[] digits = ("" + score).toCharArray();
		System.out.println(Arrays.toString(digits));
		for (int i = 0; i < digits.length; i++) {
			// System.out.println(digits[i]);
			g2d.drawImage(textures.get("score-" + digits[i]).getImage(), 100 + i*);
		}

	}



	//////////////////////
	// Keyboard actions //
	//////////////////////

	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

		keys[keyCode] = true;

		switch (gameState) {
			case MENU:
				switch (keyCode) {

					// Start game on 'enter'
					case KeyEvent.VK_ENTER:
						gameState = GAME;
						inStartGameState = true;
						break;

					// Open leaderboard on 'L'
					case KeyEvent.VK_L:
						System.out.println("L PRESSED");
						gameState = LEADERBOARD;
						break;
				}
				break;

			case GAME:
				switch (keyCode) {
					case KeyEvent.VK_SPACE:

						if (inStartGameState) {
							inStartGameState = false;
						}

						// Jump and play audio even if in instructions state
						menuBird.jump();
						audio.jump();

						break;
				}
				break;
		}
	}

	///////////////////
	// Mouse actions //
	///////////////////

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}

	public void mousePressed (MouseEvent e) {

		clickedPoint = e.getPoint();

		score ++; // testing
		System.out.println(score);

		if (gameState == MENU) {
			if (isTouching(textures.get("playButton").getRect())) {
				gameState = GAME;
				inStartGameState = true;

			} else if (isTouching(textures.get("leaderboard").getRect())) {
				System.out.println("CLICKED LEADERBOARD BUTTON");
				gameState = LEADERBOARD;

			} else if (isTouching(textures.get("rateButton").getRect())) {
				Helper.openURL("http://paulkr.com"); // Open website
			}
		} 

	}


}

