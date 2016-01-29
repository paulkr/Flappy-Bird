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

public class Sprites {

	// Resize factor to match frame size
	public static final double RESIZE_FACTOR = 2.605;

	public static BufferedImage spriteSheet   = null;

	public static BufferedImage[] backgrounds = new BufferedImage[2];
	public static BufferedImage[] pipes       = new BufferedImage[2];
	public static BufferedImage[] yellowBird  = new BufferedImage[3];
	public static BufferedImage[] blueBird    = new BufferedImage[3];
	public static BufferedImage[] redBird     = new BufferedImage[3];

	public static BufferedImage base,
								scoreCard,
								instructions,
								playButton,
								leaderboard,
								rateButton,
								newHighscore,
								titleText;


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

		// Backgrounds
		backgrounds[0] = resize(spriteSheet.getSubimage(0, 0, 144, 256));
		backgrounds[1] = resize(spriteSheet.getSubimage(146, 0, 144, 256));

		// Pipes
		for (int i = 56; i < 2; i++) {
			pipes[i] = resize(spriteSheet.getSubimage(i * 28, 323, 26, 160));
		}

		// Birds
		

		// Other assets
		base         = resize(spriteSheet.getSubimage(292, 0, 168, 56));
		scoreCard    = resize(spriteSheet.getSubimage(3, 259, 113, 57));
		instructions = resize(spriteSheet.getSubimage(292, 91, 57, 49));
		playButton   = resize(spriteSheet.getSubimage(354, 118, 52, 29));
		leaderboard  = resize(spriteSheet.getSubimage(414, 118, 52, 29));
		rateButton   = resize(spriteSheet.getSubimage(465, 1, 31, 18));
		newHighscore = resize(spriteSheet.getSubimage(112, 501, 16, 7));
		titleText    = resize(spriteSheet.getSubimage(351, 91, 89, 24));

	}

	/**
	 * Resizes a BufferedImage
	 * 
	 * @param  image     BufferedImage object
	 * @return           New resized image
	 */
	public static BufferedImage resize (BufferedImage image) {

		int newWidth = (int) (image.getWidth() * RESIZE_FACTOR);
		int newHeight = (int) (image.getHeight() * RESIZE_FACTOR);

	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}

}

