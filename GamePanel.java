/**
 * GamePanel.java
 * 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.KeyEvent.*;
import java.util.HashMap;

public class GamePanel extends JPanel implements Globals, KeyListener, MouseListener {

	private Random rand = new Random();

	private Menu menu;
	private Bird bird;
	public boolean ready = false;

	// Game state
	int gameState = MENU;

	boolean keys[] = new boolean[256];

	Point clickedPoint;

	Rectangle imageBounds = new Rectangle(34, 448,(int)(52*2.605), (int)(29*2.605));

	// Get all the game textures
	private HashMap<String, Texture> textures = new Sprites().getGameTextures();

	public GamePanel () {

		// For random background and bird
		boolean dark = rand.nextBoolean();
		String[] birds = new String[] {
			"yellow",
			"blue",
			"red"
		};
		String randomBird = birds[rand.nextInt(3)];

		menu = new Menu(dark, randomBird);
		bird = new Bird(randomBird);

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

	@Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        switch (gameState) {

        	case MENU:
        		menu.moveBase();
				menu.draw(g);
				bird.draw(g);

				if (clickedPoint != null && imageBounds.contains(clickedPoint)) {
					   System.out.println("CLICKED PLAY BUTTON");
				}

				break;

			case LEADERBOARD:
				System.out.println("LEADERBOARD");
				break;

			case GAME:
				System.out.println("GAME");
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
		System.out.println(clickedPoint);
	}





}

