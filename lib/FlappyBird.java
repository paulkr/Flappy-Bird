/**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.*;
import java.awt.event.*;

public class FlappyBird extends JFrame implements ActionListener {

	GamePanel game;
	Timer gameTimer;
	Timer spritesTimer;

	private boolean spacePress;

	public static final int WIDTH = 375;
	public static final int HEIGHT = 667;
	private static final int DELAY = 10;

	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);

		gameTimer = new Timer(DELAY, this);
		gameTimer.start();
 
		game = new GamePanel();
		add(game);

		setResizable(false);
		setVisible(true);	

	}

	public void actionPerformed (ActionEvent e) {
		if (game != null && game.ready) {
			game.repaint();
		}
	}


	public static void main(String[] args) {

		FlappyBird game = new FlappyBird();

	}

}

