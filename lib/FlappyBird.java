/**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class FlappyBird extends JFrame implements ActionListener {

	GamePanel game;
	Timer gameTimer;

	// Game setup constants
	public static final int WIDTH  = 375;
	public static final int HEIGHT = 667;
	private static final int DELAY = 12;

	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);

		// Game timer
		gameTimer = new Timer(DELAY, this);
		gameTimer.start();
  
  		// Add Panel to Frame
		game = new GamePanel();
		add(game);

		// Set game icon
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icons.png"));

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

