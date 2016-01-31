/**
 * Bird.java
 * Handles bird's state and actions
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import javax.swing.*;


public class Bird extends JPanel {

	Sprites sp = new Sprites();
	String color;

	public Bird (String color) {
		this.color = color;
	}

	public void draw (Graphics g) {
		switch (color) {
			case "yellow":
				g.drawImage(sp.yellowBird[0], 172, 250, null);
			case "blue":
				g.drawImage(sp.blueBird[0], 172, 250, null);
			case "red":
				g.drawImage(sp.redBird[0], 172, 250, null);
		}
	}

}
