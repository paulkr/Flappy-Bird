/**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class FlappyBird extends JFrame {

	GamePanel game;

	private static InputListener inListener;

	private boolean spacePress;
	
	private Menu menu;


	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(375, 667);

		// For random background
		boolean dark = new Random().nextBoolean();
		add(new Menu(dark));

		setResizable(false);
		setVisible(true);

		inListener = InputListener.getInstance();	

	}

	public void run () {

		while (true) {

			// Try to sleep
			try {
				Thread.sleep(30);
			} catch (Exception e) {}

			// Menu screen
			menu.moveBase();
			menu.draw();
		}

	}


	public static void main(String[] args) {

		FlappyBird game = new FlappyBird();

		game.run();

	}

}

