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

	private Random rand;
	private Calendar cal;

	// Fonts
	private Font flappyFontBase, 
				 flappyFontReal, 
				 flappyScoreFont,
				 flappyMiniFont = null;

	// Textures
	public static HashMap<String, Texture> textures = new Sprites().getGameTextures();

	// Moving base effect
	private static int baseSpeed      = 2;
	private static int[] baseCoords   = { 0, 435 };
	public static Audio audio         = new Audio();
	
	// Game variables
	public boolean ready              = false;             // If game is loaded
	private int gameState             = MENU;              // Game screen state
	private boolean inStartGameState  = false;             // To show instructions scren
	private Point clickedPoint        = new Point(-1, -1); // Store point when player clicks 
	public static int score           = 0;                 // Current game score
	private int pipeDistTracker       = 0;
	
	// Constants
	private final Bird gameBird;
	private final boolean darkTheme;
	private final String randomBird;

	public ArrayList<Pipe> pipes = new ArrayList<Pipe>();


	public GamePanel () {

		rand = new Random();

		// Try to load ttf file
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("res/fonts/flappy-font.ttf"));
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

		// If we should use the dark theme
		boolean dark = currentHour > 12;

		// Array of bird colors
		String[] birds = new String[] {
			"yellow",
			"blue",
			"red"
		};

		// Set random scene assets
		darkTheme = dark;
		randomBird = birds[rand.nextInt(3)];

		// Game bird
		gameBird = new Bird(randomBird, 172, 250, new BufferedImage[] {
			textures.get(randomBird + "Bird1").getImage(),
			textures.get(randomBird + "Bird2").getImage(),
			textures.get(randomBird + "Bird3").getImage()
		});

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
				drawBase(g);
				drawMenu(g);

				gameBird.menuFloat();

				break;

			case LEADERBOARD:
				System.out.println("LEADERBOARD");
				break;

			case GAME:

				// Start at instructions state
				if (inStartGameState) {
					startGameScreen(g);

				} else {
					// Start game
					gameBird.renderBird(g);
					gameBird.inGame();
					pipeHandler(g);
				}

				drawBase(g); // Draw base over pipes
				drawScore(g); // Draw player score

				break;

		}

	}

	/////////////////////////
	// All drawing methods //
	/////////////////////////

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
	 * Needs to be called differently based on screen
	 */
	public void drawBase (Graphics g) {

		// Moving base effect
		g.drawImage(textures.get("base").getImage(), baseCoords[0], textures.get("base").getY(), null);
		g.drawImage(textures.get("base").getImage(), baseCoords[1], textures.get("base").getY(), null);

	}

	/**
	 * Draws items that stay no matter what the scene is
	 */
	public void constantItems (Graphics g) {

		// Background
		g.drawImage(darkTheme ? textures.get("background2").getImage() : 
			textures.get("background1").getImage(), 0, 0, null);

		// Draw bird
		gameBird.renderBird(g);

	}

	////////////////
	// Menuscreen //
	////////////////

	private void drawMenu (Graphics g) {

		// Title
		g.drawImage(textures.get("titleText").getImage(), 
			textures.get("titleText").getX(), 
			textures.get("titleText").getY(), null);

		// Buttons
		g.drawImage(textures.get("playButton").getImage(),
			textures.get("playButton").getX(),
			textures.get("playButton").getY(), null);
		g.drawImage(textures.get("leaderboard").getImage(),
			textures.get("leaderboard").getX(),
			textures.get("leaderboard").getY(), null);
		g.drawImage(textures.get("rateButton").getImage(),
			textures.get("rateButton").getX(),
			textures.get("rateButton").getY(), null);

		// Credits :p
		drawCentered("Created by Paul Krishnamurthy", FlappyBird.WIDTH, FlappyBird.HEIGHT, 600, g);
		g.setFont(flappyMiniFont); // Change font
		drawCentered("www.PaulKr.com", FlappyBird.WIDTH, FlappyBird.HEIGHT, 630, g);

	}

	/////////////////
	// Game screen //
	/////////////////

	public void startGameScreen (Graphics g) {

		// Set bird's new position
		gameBird.setGameStartPos();

		// Get ready text
		g.drawImage(textures.get("getReadyText").getImage(),
			textures.get("getReadyText").getX(),
			textures.get("getReadyText").getY(), null);

		// Instructions image
		g.drawImage(textures.get("instructions").getImage(), 
			textures.get("instructions").getX(),
			textures.get("instructions").getY(), null);

	}

	/**
	 * Aligns and draws score using image textures
	 */
	public void drawScore (Graphics g) {
	
		// Char array of digits
		char[] digits = ("" + score).toCharArray();
		
		int digitCount = digits.length;

		// Calculate width for numeric textures
		int takeUp = 0;
		for (char digit : digits) {
			takeUp += digit == '1' ? 25 : 35;
		}

		// Calculate x-coordinate
		int drawScoreX = FlappyBird.WIDTH / 2 - takeUp / 2;

		// Draw every digit
		for (int i = 0; i < digitCount; i++) {
			g.drawImage(textures.get("score-" + digits[i]).getImage(), drawScoreX, 60, null);
			drawScoreX += digits[i] == '1' ? 25 : 35;
		}

	}

	/**
	 * Moves and repositions pipes
	 */
	public void pipeHandler (Graphics g) {

		// Decrease distance between pipes
		pipeDistTracker --;

		Pipe topPipe = null;
		Pipe bottomPipe = null;

		// If there is no distance,
		// a new pipe is needed
		if (pipeDistTracker < 0) {

			// Reset distance
			pipeDistTracker = Pipe.PIPE_DISTANCE;

			for (Pipe p : pipes) {
				// If pipe is out of screen
				if (p.getX() < 0) {
					if (topPipe == null) { topPipe = p; }
					else if (bottomPipe == null) {bottomPipe = p; }
				}
			}

			Pipe currentPipe; // New pipe object

			// Move and handle initial creation of pipes

			if (topPipe == null) {
				currentPipe = new Pipe("top");
				pipes.add(currentPipe);
				topPipe = currentPipe;
			} else {
				topPipe.reset();
			}

			if (bottomPipe == null) {
				currentPipe = new Pipe("bottom");
				pipes.add(currentPipe);
				bottomPipe = currentPipe;
			} else {
				bottomPipe.reset();
			}

			// Set y-coordinate of bottom pipe based on 
			// y-coordinate of top pipe
			bottomPipe.setY(topPipe.getY() + Pipe.PIPE_SPACING);

		}

		// Move and draw each pipe

		for (Pipe p : pipes) {
			p.move(); // Move the pipe

			// Draw the top and bottom pipes
			if (p.getY() <= 0) {
				g.drawImage(textures.get("pipe-top").getImage(), p.getX(), p.getY(), null);
			} else {
				g.drawImage(textures.get("pipe-bottom").getImage(), p.getX(), p.getY(), null);
			}
		}
		

	}


	//////////////////////
	// Keyboard actions //
	//////////////////////

	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

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
						gameBird.jump();
						audio.jump();

						score ++; //testing

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

		// Save clicked point
		clickedPoint = e.getPoint();

		switch (gameState) {
			case MENU:
				if (isTouching(textures.get("playButton").getRect())) {
					gameState = GAME;
					inStartGameState = true;

				} else if (isTouching(textures.get("leaderboard").getRect())) {
					System.out.println("CLICKED LEADERBOARD BUTTON");
					gameState = LEADERBOARD;

				} else if (isTouching(textures.get("rateButton").getRect())) {
					Helper.openURL("http://paulkr.com"); // Open website
				}
				break;

			case GAME:

				// Allow jump with clicks
				
				score ++; // testing

				if (inStartGameState) {
					inStartGameState = false;
				}

				gameBird.jump();
				audio.jump();

				break;

		}

	}


}

