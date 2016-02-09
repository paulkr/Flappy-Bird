/**
 * Pipe.java
 * Handles collisions and rendering for pipes
 *
 * @author  Paul Krishnamurthy
 */


public class Pipe {

	private int x = FlappyBird.WIDTH + 5;
	private int y;

	String location;

	public static final int WIDTH = 67;
	public static final int HEIGHT = 416;
	public static final int PIPE_DISTANCE = 150;          // Horizontal distance between pipes
	public static final int PIPE_SPACING = HEIGHT + 170;  // Vertical distance between pipes

	private final int speed = -2;

	public Pipe (String location) {
		this.location = location;
		reset();
	}

	public void reset () {
		x = FlappyBird.WIDTH + 5;

		if (location.equals("top")) {
			y = - Math.max((int) (Math.random() * 320) + 30, 140);
		}
	}

	public void move () {
		x += speed;
	}

	public boolean collide (int nX, int nY, int nW, int nH) {
		
		return true;

	}

	public void passed () {
		GamePanel.score ++;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public void setY (int newY) {
		y = newY;
	}

}

