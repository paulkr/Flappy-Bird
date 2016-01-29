

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class Base extends JPanel {

	public int[] xCoords = {0, 435};

	Sprites sp = new Sprites();

	/**
	 * Moves the x-coordinate of the base
	 */
	public void move () {

		xCoords[0] = xCoords[0] - 5 < -434 ? 430 : xCoords[0] - 5;

		xCoords[1] = xCoords[1] - 5 < -434 ? 430 : xCoords[1] - 5;

	}

	@Override
    public void paint (Graphics g2d) {
        super.paintComponent(g2d);

		// Moving base effect
		g2d.drawImage(sp.base, xCoords[0], 521, null);
		g2d.drawImage(sp.base, xCoords[1], 521, null);

    }

    public void draw () {
    	this.repaint();
    }

}