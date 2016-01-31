/**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class FlappyBird extends JFrame {

	GamePanel game;

	private static InputListener inListener;

	private boolean spacePress;

	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(375, 667);

		game = new GamePanel();
		add(game);

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
			

			game.repaint();
		}

	}


	public static void main(String[] args) {

		FlappyBird game = new FlappyBird();

		game.run();

	}

}

