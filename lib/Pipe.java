/**
 * Pipe.java
 * Handles collisions and rendering for pipes
 *
 * @author  Paul Krishnamurthy
 */

public class Pipe {

	// Pipe coordinates
	private int x = FlappyBird.WIDTH + 5;
	private int y;

	// Placement (top or bottom) of pipe
	String location;

	// Pipe constants
	public static final int WIDTH         = 67;
	public static final int HEIGHT        = 416;
	public static final int PIPE_DISTANCE = 150;          // Horizontal distance between pipes
	public static final int PIPE_SPACING  = HEIGHT + 170; // Vertical distance between pipes
	private static final int SPEED        = -2;

	// If the bird can get a point passing this pipe
	public boolean canAwardPoint = true;

	public Pipe (String location) {
		this.location = location;
		reset();
	}

	public void reset () {
		x = FlappyBird.WIDTH + 5; // Reset x-coordinate

		// Set boundaries for top pipes
		// This y-coordinte + PIPE_SPACING will be for the bottom pipe
		if (location.equals("top")) {
			y = - Math.max((int) (Math.random() * 320) + 30, 140);
		}
	}

	/**
	 * Moves the pipe
	 */
	public void move () {
		x += SPEED;
	}


	/**
	 * Checks for bird colliding with pipe
	 * 
	 * @param  nX     Bird x-coordinate
	 * @param  nY     Bird y-coordinate
	 * @param  nW     Bird width
	 * @param  nH     Bird height
	 * @return        If bird is colliding with the pipe
	 */
	public boolean collide (int nX, int nY, int nW, int nH) {

		// Do not allow bird to jump over pipe
		if (nX > x && nY < 0 && canAwardPoint) {
			return true;
		}

		return nX < x + WIDTH && 
				nX + nW > x &&
				nY < y + HEIGHT &&
				nY + nH > y;

	}

	/**
	 * @return     Pipe's x-coordinate
	 */
	public int getX () {
		return x;
	}

	/**
	 * @return     Pipe's y-coordinate
	 */
	public int getY () {
		return y;
	}

	/**
	 * Set's pipe's y-coordinate (for bottom pipes)
	 * 
	 * @param newY     New y-coordinate
	 */
	public void setY (int newY) {
		y = newY;
	}

}

