
import java.awt.image.BufferedImage;

public class Texture {

	private BufferedImage image;
	private int x, y, width, height;


	public Texture (BufferedImage image, int x, int y, int width, int height) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

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

}

