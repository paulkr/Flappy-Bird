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

	private static double currentFrame = 0;

	public static void animate (Graphics g2d, BufferedImage[] sprites, int x, int y, double speed) {
		
		int count = sprites.length;
		
		g2d.drawImage(sprites[(int) (Math.round(currentFrame))], x, y, null);

        if (currentFrame >= count - 1) {
            currentFrame = 0;
        } else currentFrame += speed;

	}

}

