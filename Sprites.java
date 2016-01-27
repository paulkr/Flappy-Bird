
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Sprites {

	private static BufferedImage spriteSheet = null;

	public Sprites () {

		try {
			spriteSheet = ImageIO.read(new File("resources/img/spriteSheet"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load spritesheet.");
			System.exit(-1); // Exit program if file could not be found
			return;
		}

	}

}

