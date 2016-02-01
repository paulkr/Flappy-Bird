

import javax.swing.JPanel;


public class Menu extends JPanel {

	public static int baseSpeed = 1;
	public static int[] xCoords = {0, 435};

	/**
	 * Moves the x-coordinate of the base
	 */
	public static void moveBase () {
		xCoords[0] = xCoords[0] - baseSpeed < -435 ? 435 : xCoords[0] - baseSpeed;
		xCoords[1] = xCoords[1] - baseSpeed < -435 ? 435 : xCoords[1] - baseSpeed;
	}

}

