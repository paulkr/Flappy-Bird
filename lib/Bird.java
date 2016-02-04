/**
 * Bird.java
 * Handles bird's state and actions
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.JPanel;

public class Bird extends JPanel {

	public String color;
	public int x, y;
	
	private int FLOAT_MULTIPLIER     = -1;
	private final int BIRD_HEIGHT    = 31;
	private final int BASE_COLLISION = 521 - BIRD_HEIGHT;
	private final int SHIFT          = 10;
	
	private double velocity          = 0;
	private double gravity           = .4;
	private double delay             = 0;
	private Audio audio              = new Audio();

	public Bird (String color, int x, int y) {
		this.color = color;
		this.x = x;
		this.y = y;
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

	public void jump () {

        if (delay < 1) {
            velocity = -SHIFT;
            delay = SHIFT;
        }

	}

	public void rotate () {
		
	}

	private void die () {
		System.out.println("BIRD IS DEAD :(");
		System.exit(-1);
	}

	public void inGame () {

		if (y < BASE_COLLISION) {

			velocity += gravity;

			if (delay > 0) {
				delay--;
			}

			y += (int) velocity;

		} else {
			audio.hit();
			die();
		}

	}
}
