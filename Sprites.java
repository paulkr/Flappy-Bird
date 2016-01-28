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

public class Sprites {

	private static BufferedImage spriteSheet   = null;
	private static BufferedImage[] backgrounds = new BufferedImage[2];
	private static BufferedImage[] texts       = new BufferedImage[27];
	private static BufferedImage[] pipes       = new BufferedImage[4];

	public Sprites () {

		try {
			spriteSheet = ImageIO.read(new File("resources/img/spriteSheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load sprite sheet.");
			System.exit(-1); // Exit program if file could not be found
			return;
		}

		// Cut up sprites
		backgrounds[0] = spriteSheet.getSubimage(0, 0, 100, 200);
		backgrounds[1] = spriteSheet.getSubimage(0, 0, 100, 200);
	}


}

