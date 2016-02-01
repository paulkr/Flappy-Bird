/**
 * Bird.java
 * Handles bird's state and actions
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.JPanel;

public class Bird extends JPanel {

	private String color;
	private int x, y;

	public Bird (String color) {
		this.color = color;
	}

	public String getColor () {
		return color;
	}

}
