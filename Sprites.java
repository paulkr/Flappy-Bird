/**
 * Sprites.java
 * Cuts up the main sprite sheet
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;

public class Sprites {

	// Resize factor to match frame size
	private static final double RESIZE_FACTOR = 2.605;

	private static BufferedImage spriteSheet = null;

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public Sprites () {

		// Try to load sprite sheet, exit program if cannot

		try {
			spriteSheet = ImageIO.read(new File("resources/img/spriteSheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load sprite sheet.");
			System.exit(-1); // Exit program if file could not be found
			return;
		}

		textures.put("background1", new Texture(resize(spriteSheet.getSubimage(0, 0, 144, 256)),   0, 0));
		textures.put("background2", new Texture(resize(spriteSheet.getSubimage(146, 0, 144, 256)), 0, 0));

		// Pipes
		for (int i = 56; i < 2; i++) {
			textures.put("pipe" + (i + 1), new Texture(resize(spriteSheet.getSubimage(i * 28, 323, 26, 160)), 10000, 10000));
		}

		// Birds
		textures.put("yellowBird1", new Texture(resize(spriteSheet.getSubimage(31, 491, 17, 12)), 172, 250));
		textures.put("yellowBird2", new Texture(resize(spriteSheet.getSubimage(59, 491, 17, 12)), 172, 250));
		textures.put("yellowBird3", new Texture(resize(spriteSheet.getSubimage(3, 491, 17, 12)),  172, 250));

		textures.put("blueBird1",   new Texture(resize(spriteSheet.getSubimage(115, 329, 17, 12)), 172, 250));
		textures.put("blueBird2",   new Texture(resize(spriteSheet.getSubimage(115, 355, 17, 12)), 172, 250));
		textures.put("blueBird3",   new Texture(resize(spriteSheet.getSubimage(87, 491, 17, 12)), 172, 250));

		textures.put("redBird1",    new Texture(resize(spriteSheet.getSubimage(115, 407, 17, 12)), 172, 250));
		textures.put("redBird2",    new Texture(resize(spriteSheet.getSubimage(115, 433, 17, 12)), 172, 250));
		textures.put("redBird3",    new Texture(resize(spriteSheet.getSubimage(115, 381, 17, 12)), 172, 250));

		// Other assets
		textures.put("base",         new Texture(resize(spriteSheet.getSubimage(292, 0, 168, 56)),  0, 0));
		textures.put("scoreCard",    new Texture(resize(spriteSheet.getSubimage(3, 259, 113, 57)),  3, 259));
		textures.put("instructions", new Texture(resize(spriteSheet.getSubimage(292, 91, 57, 49)),  292, 91));
		textures.put("playButton",   new Texture(resize(spriteSheet.getSubimage(354, 118, 52, 29)), 34, 448));
		textures.put("leaderboard",  new Texture(resize(spriteSheet.getSubimage(414, 118, 52, 29)), 203, 448));
		textures.put("rateButton",   new Texture(resize(spriteSheet.getSubimage(465, 1, 31, 18)),   147, 355));
		textures.put("newHighscore", new Texture(resize(spriteSheet.getSubimage(112, 501, 16, 7)),  112, 501));
		textures.put("titleText",    new Texture(resize(spriteSheet.getSubimage(351, 91, 89, 24)),  72, 100));

	}

	/**
	 * Resizes a BufferedImage
	 * 
	 * @param  image     BufferedImage object
	 * @return           New resized image
	 */
	private static BufferedImage resize (BufferedImage image) {

		int newWidth = (int) (image.getWidth() * RESIZE_FACTOR);
		int newHeight = (int) (image.getHeight() * RESIZE_FACTOR);

	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}

	/**
	 * Public getter for Textures HashMap
	 * 
	 * @return     Texture
	 */
	public HashMap<String, Texture> getGameTextures () {
		return textures;
	}

}

