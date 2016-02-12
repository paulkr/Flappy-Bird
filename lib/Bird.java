/**
 * Bird.java
 * Handles bird's state and actions
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Bird extends JPanel {

	public String color;
	private int x, y;
	private boolean isAlive = true;
	
	// Bird constants
	private int FLOAT_MULTIPLIER      = -1;
	public final int BIRD_WIDTH       = 44;
	public final int BIRD_HEIGHT      = 31;
	private final int BASE_COLLISION  = 521 - BIRD_HEIGHT - 5;
	private final int SHIFT           = 10;
	private final int STARTING_BIRD_X = 90;
	private final int STARTING_BIRD_Y = 343;
	private final int FALL_SPEED      = 3;
	
	// Physics variables
	private double velocity           = 0;
	private double gravity            = .41;
	private double delay              = 0;

	private BufferedImage[] sprites;

	public Bird (String color, int x, int y, BufferedImage[] s) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.sprites = s;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public boolean isAlive () {
		return isAlive;
	}

	public void kill () {
		isAlive = false;
	}

	/**
	 * Set new coordinates when starting games
	 */
	public void setGameStartPos () {
		x = STARTING_BIRD_X;
		y = STARTING_BIRD_Y;
	}

	/**
	 * Floating bird effect on menu screen
	 */
	public void menuFloat () {

		y += FLOAT_MULTIPLIER;

		if (y < 220) {
			FLOAT_MULTIPLIER *= -1;
		} else if (y > 280) {
			FLOAT_MULTIPLIER *= -1;
		}

	}

	/**
	 * Bird jump
	 */
	public void jump () {

		if (delay < 1) {
			velocity = -SHIFT;
			delay = SHIFT;
		}

	}

	/**
	 * Bird movement during the game
	 */
	public void inGame () {

		// If the bird did not hit the base
		if (y < BASE_COLLISION) {

			// Change and velocity
			velocity += gravity;

			// Lower delay if possible
			if (delay > 0) { delay--; }

			// Add rounded velocity to y-coordinate
			y += (int) velocity;

		} else {
			GamePanel.audio.hit();
			isAlive = false;
		}

	}

	/**
	 * Renders bird
	 */
	public void renderBird (Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (isAlive()) {

			// Create bird animation
			Animation.animate(g, sprites, x, y, .09);

		} else {

			if (y < BASE_COLLISION - 10) {
				velocity += gravity;
				y += (int) velocity;
			}

			// Rotation

			AffineTransform trans = g2d.getTransform();

			AffineTransform at = new AffineTransform();
			at.rotate(.9, x+25, y+25);
			g2d.transform(at);
			g2d.drawImage(sprites[0], x, y, null);
			g2d.setTransform(trans);

			// g.drawImage(sprites[0], x, y, null);

		}

	}

}
