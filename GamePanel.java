/**
 * GamePanel.java
 * 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel {

	private Random rand = new Random();

	private Menu menu;
	private Bird bird;
	public boolean ready = false;

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

	}

	public void addNotify() {
        super.addNotify();
        ready = true;
    }

	@Override
    public void paint (Graphics g) {
        super.paintComponent(g);

	 	menu.moveBase();
		menu.draw(g);
		bird.draw(g);        
    }

}

