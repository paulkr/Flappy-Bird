/**
 * Bird.java
 * Handles bird's state and actions
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Bird extends JPanel {

	// Bird attributes
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
	
	// Physics variables
	private double velocity           = 0;
	private double gravity            = .41;
	private double delay              = 0;
	private double rotation           = 0;

	// Bird sprites
	private BufferedImage[] sprites;


	public Bird (String color, int x, int y, BufferedImage[] s) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.sprites = s;
	}

	/**
	 * @return     Bird's x-coordinate
	 */
	public int getX () {
		return x;
	}

	/**
	 * @return     Bird's y-coordinate
	 */
	public int getY () {
		return y;
	}

	/**
	 * @return     If bird is alive
	 */
	public boolean isAlive () {
		return isAlive;
	}

	/**
	 * Kills bird
	 */
	public void kill () {
		isAlive = false;
	}

	/**
	 * Set new coordinates when starting game
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

		// Change direction within floating range
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

		// If the bird did not hit the base, lower it
		if (y < BASE_COLLISION) {

			// Change and velocity
			velocity += gravity;

			// Lower delay if possible
			if (delay > 0) { delay--; }

			// Add rounded velocity to y-coordinate
			y += (int) velocity;

		} else {

			// Play audio and set state to dead
			GamePanel.audio.hit();
			isAlive = false;
		}

	}

	/**
	 * Renders bird
	 */
	public void renderBird (Graphics g) {

		// Calculate angle to rotate bird based on y-velocity
		rotation = ((90 * (velocity + 25) / 25) - 90) * Math.PI / 180;
		
		// Divide for clean jump
		rotation /= 2;

		// Handle rotation offset
		rotation = rotation > Math.PI / 2 ? Math.PI / 2 : rotation;

		if (!isAlive()) {

			// Drop bird on death
			if (y < BASE_COLLISION - 10) {
				velocity += gravity;
				y += (int) velocity;
			}

		}

		// Create bird animation and pass in rotation angle
		Animation.animate(g, sprites, x, y, .09, rotation);

	}

}
