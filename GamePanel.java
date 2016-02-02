/**
 * GamePanel.java
 * 
 * 
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

public class GamePanel extends JPanel implements Globals, KeyListener, MouseListener {

	private Random rand = new Random();
	private Calendar cal;

	private Menu menu;
	private Bird menuBird;
	private Point clickedPoint;
	private boolean darkTheme;
	private String randomBird;

	public boolean ready = false;

	// Fonts
	private Font flappyFontBase, 
				 flappyFontReal, 
				 flappyMiniFont = null;

	// Game state
	int gameState = MENU;

	boolean keys[] = new boolean[256];

	// Get all the game textures
	private HashMap<String, Texture> textures = new Sprites().getGameTextures();

	public GamePanel () {

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

		clickedPoint = new Point(-1, -1);

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

		// menu = new Menu();
		menuBird = new Bird(randomBird, 172, 250);

		// Set all keys to false
		for (int i = 0; i < 256; i++) {
			keys[i] = false;
		}

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

		switch (gameState) {

			case MENU:
				Menu.moveBase();
				menuDrawing(g);

				menuBird.menuFloat();
				birdDrawing(g);


				break;

			case LEADERBOARD:
				System.out.println("LEADERBOARD");
				break;

			case GAME:
				System.out.println("GAME");
				break;

		}

	}

	// Drawing from different components

	private void menuDrawing (Graphics g2d) {

		// Set font and color
		g2d.setFont(flappyFontReal);
		g2d.setColor(Color.white);

		// Background
		g2d.drawImage(darkTheme ? textures.get("background2").getImage() : 
			textures.get("background1").getImage(), 0, 0, null);

		// Title
		g2d.drawImage(textures.get("titleText").getImage(), 72, 100, null);

		// Buttons
		g2d.drawImage(textures.get("playButton").getImage(), 34, 448, null);
		g2d.drawImage(textures.get("leaderboard").getImage(), 203, 448, null);
		g2d.drawImage(textures.get("rateButton").getImage(), 147, 355, null);

		// Moving base effect
		g2d.drawImage(textures.get("base").getImage(), Menu.xCoords[0], 521, null);
		g2d.drawImage(textures.get("base").getImage(), Menu.xCoords[1], 521, null);

		// Credits :p
		g2d.drawString("Created by Paul Krishnamurthy", 27, 600);
		g2d.setFont(flappyMiniFont); // Change font
		g2d.drawString("www.PaulKr.com", 115, 630);

	}

	private void birdDrawing (Graphics g2d) {

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



	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {
		int keyCode = e.getKeyCode();

		keys[keyCode] = true;

		switch (gameState) {
			case MENU:
				switch (keyCode) {
					case KeyEvent.VK_SPACE:
						System.out.println("SPACE PRESSED");
						gameState = GAME;
						break;
					case KeyEvent.VK_ENTER:
						System.out.println("ENTER PRESSED");
						gameState = GAME;
						break;
					case KeyEvent.VK_L:
						System.out.println("L PRESSED");
						gameState = LEADERBOARD;
						break;
				}
		}
	}

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}

	public void mousePressed (MouseEvent e) {
		System.out.println("YOU PRESSED");

		clickedPoint = e.getPoint();

		if (isTouching(textures.get("playButton").getRect())) {
			System.out.println("CLICKED PLAY BUTTON");
			gameState = GAME;

		} else if (isTouching(textures.get("leaderboard").getRect())) {
			System.out.println("CLICKED LEADERBOARD BUTTON");
			gameState = LEADERBOARD;

		} else if (isTouching(textures.get("rateButton").getRect())) {
			Helper.openURL("http://paulkr.com");
		}

	}


}

