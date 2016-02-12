/**
 * Texture.java
 * Stores data for game textures
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Texture {

	// Image attributes
	private BufferedImage image;
	private int x, y, width, height;
	private Rectangle rect;


	public Texture (BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.rect = new Rectangle(x, y, width, height);
	}

	////////////////////////////////////////////////
	// Public getter methods for image attributes //
	////////////////////////////////////////////////

	public BufferedImage getImage () {
		return image;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public int getWidth () {
		return width;
	}

	public int getHeight () {
		return height;
	}

	public Rectangle getRect () {
		return rect;
	}

}

