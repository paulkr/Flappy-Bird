/**
 * Animation.java
 * Creates animation with array of sprites
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Animation {

	// Start at first frame
	private static double currentFrame = 0;

	/**
	 * Creates an animation with an array of sprites
	 * 
	 * @param sprites     Array of BufferedImages
	 * @param x           x-coordinate
	 * @param y           y-coordinate
	 * @param speed       Speed of animation
	 */
	public static void animate (Graphics g2d, BufferedImage[] sprites, int x, int y, double speed) {
		
		int count = sprites.length;
		
		// Draw the current frame
		g2d.drawImage(sprites[(int) (Math.round(currentFrame))], x, y, null);

		// Switch animation frames
        if (currentFrame >= count - 1) {
            currentFrame = 0;
        } else currentFrame += speed;

	}

}

