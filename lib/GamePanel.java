/**
 * GamePanel.java
 * Main game panel
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class GamePanel extends JPanel implements KeyListener, MouseListener {

	private Random rand;
	private Calendar cal;
	
	////////////////////
	// Game variables //
	////////////////////
	
	// Fonts
	private Font flappyFontBase, 
				 flappyFontReal, 
				 flappyScoreFont,
				 flappyMiniFont = null;

	// Textures
	public static HashMap<String, Texture> textures = new Sprites().getGameTextures();	
	
	// Moving base effect
	private static int baseSpeed    = 2;
	private static int[] baseCoords = { 0, 435 };

	// Game states
	final static int MENU = 0;
	final static int GAME = 1;
	private int gameState = MENU;  

	private int score;           // Player score
	private int pipeDistTracker; // Distance between pipes
	
	public boolean ready = false;                   // If game has loaded
	private boolean inStartGameState = false;       // To show instructions scren
	private Point clickedPoint = new Point(-1, -1); // Store point when player clicks 
	private boolean scoreWasGreater;                // If score was greater than previous highscore
	private boolean darkTheme;                      // Boolean to show dark or light screen
	private String randomBird;                      // Random bird color
	private String medal;                           // Medal to be awarded after each game
	public ArrayList<Pipe> pipes;                   // Arraylist of Pipe objects

	private Bird gameBird;
	private Highscore highscore = new Highscore();
	public static Audio audio   = new Audio();


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

		restart(); // Reset game variables

		// Input listeners
		addKeyListener(this);
		addMouseListener(this);

	}

	/**
	 * To start game after everything has been loaded
	 */
	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}

	/**
	 * Restarts game by resetting game variables
	 */
	public void restart () {

		// Reset game statistics
		score            = 0;
		pipeDistTracker  = 0;
		scoreWasGreater  = false;

		// Get current hour with Calendar
		// If it is past noon, use the dark theme
		cal = Calendar.getInstance();
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);

		// Array of bird colors
		String[] birds = new String[] {
			"yellow",
			"blue",
			"red"
		};

		// Set random scene assets
		darkTheme = currentHour > 12; // If we should use the dark theme
		randomBird = birds[rand.nextInt(3)]; // Random bird color

		// Game bird
		gameBird = new Bird(randomBird, 172, 250, new BufferedImage[] {
			textures.get(randomBird + "Bird1").getImage(),
			textures.get(randomBird + "Bird2").getImage(),
			textures.get(randomBird + "Bird3").getImage()
		});

		// Remove old pipes
		pipes = new ArrayList<Pipe>();

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

		// Only move screen if bird is alive
		if (gameBird.isAlive()) {

			// Move base
			baseCoords[0] = baseCoords[0] - baseSpeed < -435 ? 435 : baseCoords[0] - baseSpeed;
			baseCoords[1] = baseCoords[1] - baseSpeed < -435 ? 435 : baseCoords[1] - baseSpeed;

		}

		// Background
		g.drawImage(darkTheme ? textures.get("background2").getImage() : 
			textures.get("background1").getImage(), 0, 0, null);

		// Draw bird
		gameBird.renderBird(g);

		switch (gameState) {

			case MENU:

				drawBase(g);
				drawMenu(g);

				gameBird.menuFloat();

				break;

			case GAME:

				if (gameBird.isAlive()) {

					// Start at instructions state
					if (inStartGameState) {
						startGameScreen(g);

					} else {
						// Start game
						pipeHandler(g);
						gameBird.inGame();
					}

					drawBase(g);                      // Draw base over pipes
					drawScore(g, score, false, 0, 0); // Draw player score

				} else {

					pipeHandler(g);
					drawBase(g);

					// Draw game over assets
					gameOver(g);

				}

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
	 * 
	 * @param mini     Boolean for drawing small or large numbers
	 * @param x        X-coordinate to draw for mini numbers
	 */
	public void drawScore (Graphics g, int drawNum, boolean mini, int x, int y) {
	
		// Char array of digits
		char[] digits = ("" + drawNum).toCharArray();
		
		int digitCount = digits.length;

		// Calculate width for numeric textures
		int takeUp = 0;
		for (char digit : digits) {

			// Size to add varies based on texture
			if (mini) {
				takeUp += 18;
			} else {
				takeUp += digit == '1' ? 25 : 35;
			}
		}

		// Calculate x-coordinate
		int drawScoreX = mini ? (x - takeUp) : (FlappyBird.WIDTH / 2 - takeUp / 2);

		// Draw every digit
		for (int i = 0; i < digitCount; i++) {
			g.drawImage(textures.get((mini ? "mini-score-" : "score-") + digits[i]).getImage(), drawScoreX, (mini ? y : 60), null);
			
			// Size to add varies based on texture
			if (mini) {
				drawScoreX += 18;
			} else {
				drawScoreX += digits[i] == '1' ? 25 : 35;
			}
		}

	}

	/**
	 * Moves and repositions pipes
	 */
	public void pipeHandler (Graphics g) {

		// Decrease distance between pipes
		if (gameBird.isAlive()) {
			pipeDistTracker --;
		}

		// Initialize pipes as null
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
					if (topPipe == null) { 
						topPipe = p;
						topPipe.canAwardPoint = true;
					}
					else if (bottomPipe == null) {
						bottomPipe = p; 
						topPipe.canAwardPoint = true;
					}
				}
			}

			Pipe currentPipe; // New pipe object for top and bottom pipes

			// Move and handle initial creation of top and bottom pipes

			if (topPipe == null) {
				currentPipe = new Pipe("top");
				topPipe = currentPipe;
				pipes.add(topPipe);
			} else {
				topPipe.reset();
			}

			if (bottomPipe == null) {
				currentPipe = new Pipe("bottom");
				bottomPipe = currentPipe;
				pipes.add(bottomPipe);

				// Avoid doubling points when passing initial pipes
				bottomPipe.canAwardPoint = false;
			} else {
				bottomPipe.reset();
			}

			// Set y-coordinate of bottom pipe based on 
			// y-coordinate of top pipe
			bottomPipe.setY(topPipe.getY() + Pipe.PIPE_SPACING);

		}

		// Move and draw each pipe

		for (Pipe p : pipes) {

			// Move the pipe
			if (gameBird.isAlive()) {
				p.move();
			}

			// Draw the top and bottom pipes
			if (p.getY() <= 0) {
				g.drawImage(textures.get("pipe-top").getImage(), p.getX(), p.getY(), null);
			} else {
				g.drawImage(textures.get("pipe-bottom").getImage(), p.getX(), p.getY(), null);
			}

			// Check if bird hits pipes
			if (gameBird.isAlive()) {
				if (p.collide(
					gameBird.getX(), 
					gameBird.getY(),
					gameBird.BIRD_WIDTH,
					gameBird.BIRD_HEIGHT
				)) {
					// Kill bird and play sound
					gameBird.kill();
					audio.hit();
				} else {

					// Checks if bird passes a pipe
					if (gameBird.getX() >= p.getX() + p.WIDTH / 2) {

						// Increase score and play sound
						if (p.canAwardPoint) {
							audio.point();
							score ++;
							p.canAwardPoint = false;
						}
					}
				}
			}
		}
	}

	public void gameOver (Graphics g) {

		// Game over text
		g.drawImage(textures.get("gameOverText").getImage(), 
			textures.get("gameOverText").getX(), 
			textures.get("gameOverText").getY(), null);

		// Scorecard
		g.drawImage(textures.get("scoreCard").getImage(),
			textures.get("scoreCard").getX(),
			textures.get("scoreCard").getY(), null);

		// New highscore image
		if (scoreWasGreater) {
			g.drawImage(textures.get("newHighscore").getImage(),
				textures.get("newHighscore").getX(),
				textures.get("newHighscore").getY(), null);
		}

		// Draw mini fonts for current and best scores
		drawScore(g, score, true, 303, 276);
		drawScore(g, highscore.bestScore(), true, 303, 330);

		// Handle highscore
		if (score > highscore.bestScore()) {

			// New best score
			scoreWasGreater = true;
			highscore.setNewBest(score); // Set in data file
		}

		// Medal
		if (score >= 10) { medal = "bronze"; }
		if (score >= 20) { medal = "silver"; }
		if (score >= 30) { medal = "gold"; }
		if (score >= 40) { medal = "platinum"; }

		// Only award a medal if they deserve it
		if (score > 9) {
			g.drawImage(textures.get(medal).getImage(),
				textures.get(medal).getX(),
				textures.get(medal).getY(), null);
		}

		// Buttons
		g.drawImage(textures.get("playButton").getImage(),
			textures.get("playButton").getX(),
			textures.get("playButton").getY(), null);
		g.drawImage(textures.get("leaderboard").getImage(),
			textures.get("leaderboard").getX(),
			textures.get("leaderboard").getY(), null);

	}


	//////////////////////
	// Keyboard actions //
	//////////////////////

	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (gameState == MENU) {

			// Start game on 'enter' key
			if (keyCode == KeyEvent.VK_ENTER) {
				gameState = GAME;
				inStartGameState = true;
			}

		} else if (gameState == GAME && gameBird.isAlive()) {

			if (keyCode == KeyEvent.VK_SPACE) {

				// Exit instructions state
				if (inStartGameState) {
					inStartGameState = false;
				}

				// Jump and play audio even if in instructions state
				gameBird.jump();
				audio.jump();
			}
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

		if (gameState == MENU) {

			if (isTouching(textures.get("playButton").getRect())) {
				gameState = GAME;
				inStartGameState = true;

			} else if (isTouching(textures.get("leaderboard").getRect())) {

				// Dummy message
				JOptionPane.showMessageDialog(this, 
					"We can't access the leaderboard right now!",
					"Oops!",
					JOptionPane.ERROR_MESSAGE);
			}

			if (gameBird.isAlive()) {

				if (isTouching(textures.get("rateButton").getRect())) {
					Helper.openURL("http://paulkr.com"); // Open website
				}

			}

		} else if (gameState == GAME) {

			if (gameBird.isAlive()) {

				// Allow jump with clicks
				if (inStartGameState) {
					inStartGameState = false;
				}

				// Jump and play sound
				gameBird.jump();
				audio.jump();

			} else {

				// On game over screen, allow restart and leaderboard buttons
				if (isTouching(textures.get("playButton").getRect())) {
					inStartGameState = true;
					gameState = GAME;
					restart();
					gameBird.setGameStartPos();

				} else if (isTouching(textures.get("leaderboard").getRect())) {

					// Dummy message
					JOptionPane.showMessageDialog(this, 
						"We can't access the leaderboard right now!",
						"Oops!",
						JOptionPane.ERROR_MESSAGE);
				}

			}
			
		}

	}


}

