/**
 * Audio.java
 * Plays all sound effects
 *
 * @author  Paul Krishnamurthy
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private AudioInputStream audioInputStream;
	private Clip clip;

	private void playSound (String sound) {

		// Path to sound file
		String soundURL = "res/sound/" + sound + ".wav";

		// Try to load and play sound
		try {
		    audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(soundURL));
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("Count not load %s.wav!\n", sound);
		}
	}

	/**
	 * Public method for bird jump sound
	 */
	public void jump () {
		playSound("jump");
	}

	/**
	 * Public method for point sound
	 */
	public void point () {
		playSound("point");
	}

	/**
	 * Public method for collision/death sound
	 */
	public void hit () {
		playSound("hit");
	}

}

