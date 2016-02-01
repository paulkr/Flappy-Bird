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

		textures.put("background1", new Texture(resize(spriteSheet.getSubimage(0, 0, 144, 256)),   0, 0, 144, 256));
		textures.put("background2", new Texture(resize(spriteSheet.getSubimage(146, 0, 144, 256)), 146, 0, 144, 256));

		// Pipes
		for (int i = 56; i < 2; i++) {
			textures.put("background" + (i + 1), new Texture(resize(spriteSheet.getSubimage(i * 28, 323, 26, 160)), i * 28, 323, 26, 160));
		}

		// Birds
		textures.put("yellowBird1", new Texture(resize(spriteSheet.getSubimage(31, 491, 17, 12)), 31, 491, 17, 12));
		textures.put("yellowBird2", new Texture(resize(spriteSheet.getSubimage(59, 491, 17, 12)), 59, 491, 17, 12));
		textures.put("yellowBird3", new Texture(resize(spriteSheet.getSubimage(3, 491, 17, 12)),  3, 491, 17, 12));

		textures.put("blueBird1",   new Texture(resize(spriteSheet.getSubimage(115, 329, 17, 12)), 115, 329, 17, 12));
		textures.put("blueBird2",   new Texture(resize(spriteSheet.getSubimage(115, 355, 17, 12)), 115, 355, 17, 12));
		textures.put("blueBird3",   new Texture(resize(spriteSheet.getSubimage(115, 491, 17, 12)), 115, 491, 17, 12));

		textures.put("redBird1",    new Texture(resize(spriteSheet.getSubimage(115, 407, 17, 12)), 115, 407, 17, 12));
		textures.put("redBird2",    new Texture(resize(spriteSheet.getSubimage(115, 433, 17, 12)), 115, 433, 17, 12));
		textures.put("redBird3",    new Texture(resize(spriteSheet.getSubimage(115, 381, 14, 12)), 115, 381, 14, 12));

		// Other assets
		textures.put("base",         new Texture(resize(spriteSheet.getSubimage(292, 0, 168, 56)),  292, 0, 168, 56));
		textures.put("scoreCard",    new Texture(resize(spriteSheet.getSubimage(3, 259, 113, 57)),  3, 259, 113, 57));
		textures.put("instructions", new Texture(resize(spriteSheet.getSubimage(292, 91, 57, 49)),  292, 91, 57, 49));
		textures.put("playButton",   new Texture(resize(spriteSheet.getSubimage(354, 118, 52, 29)), 354, 118, 52, 29));
		textures.put("leaderboard",  new Texture(resize(spriteSheet.getSubimage(414, 118, 52, 29)), 414, 118, 52, 29));
		textures.put("rateButton",   new Texture(resize(spriteSheet.getSubimage(465, 1, 31, 18)),   465, 1, 31, 18));
		textures.put("newHighscore", new Texture(resize(spriteSheet.getSubimage(112, 501, 16, 7)),  112, 501, 16, 7));
		textures.put("titleText",    new Texture(resize(spriteSheet.getSubimage(351, 91, 89, 24)),  351, 91, 89, 24));

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

