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

	private int FLOAT_MULTIPLIER = -1;

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

}
