/**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlappyBird extends JFrame {

	GamePanel game;

	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(375, 667);

		game = new GamePanel(this);
		add(game);

		setResizable(false);
		setVisible(true);
	}

	// public void actionPerformed (ActionEvent evt) { }

	public static void main(String[] args) {

		FlappyBird frame = new FlappyBird();

	}

}

