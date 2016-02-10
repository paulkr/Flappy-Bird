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

	public String color;
	private int x, y;
	
	// Bird constants
	private int FLOAT_MULTIPLIER     = -1;
	public final int BIRD_WIDTH      = 44;
	public final int BIRD_HEIGHT     = 31;
	private final int BASE_COLLISION = 521 - BIRD_HEIGHT;
	private final int SHIFT          = 10;
	public final int STARTING_BIRD_X = 90;
	public final int STARTING_BIRD_Y = 343;
	
	// Physics variables
	private double velocity          = 0;
	private double gravity           = .4;
	private double delay             = 0;

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

	/**
	 * Set new coordinates when starting games
	 */
	public void setGameStartPos() {
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
	 * Rotates bird based on angle passed in
	 */
	public void rotate () {
		
	}

	/**
	 * Bird death
	 */
	private void die () {
		System.out.println("BIRD IS DEAD :(");
		System.exit(-1);
	}

	/**
	 * Bird movement during the game
	 */
	public void inGame () {

		// If the bird did not hit the base
		if(true){ //if (y < BASE_COLLISION) {

			// Change and velocity
			velocity += gravity;

			// Lower delay if possible
			if (delay > 0) { delay--; }

			// Add rounded velocity to y-coordinate
			y += (int) velocity;

		} else {
			GamePanel.audio.hit();
			die();
		}

	}

	/**
	 * Renders bird
	 */
	public void renderBird (Graphics g) {

		// Create bird animation
		Animation.animate(g, sprites, x, y, .09);

	}

}
