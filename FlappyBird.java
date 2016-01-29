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

	Sprites sp = new Sprites();

	private static InputListener inListener;

	private boolean spacePress;
	
	private Base base = new Base();


	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(375, 667);

		add(base);

		setResizable(false);
		setVisible(true);

		inListener = InputListener.getInstance();	

	}

	@Override
	public void paint (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawImage(sp.backgrounds[0], 0, 0, null);

		// Moving base effect
		// g2d.drawImage(sp.base, baseX1, 521, null);
		// g2d.drawImage(sp.base, baseX2, 521, null);

	}

	public void run () {
		while (true) {
			try {
				Thread.sleep(30);
			}
			catch (Exception e) {}

			// Movements the bases
			base.move();

			// Drawing

			repaint();
			base.draw();

		}
	}


	public static void main(String[] args) {

		FlappyBird game = new FlappyBird();

		// game.add(base);

		game.run();


	}

}

