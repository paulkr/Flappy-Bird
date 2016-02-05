/**
 * Pipe.java
 * Handles collisions and rendering for pipes
 *
 * @author  Paul Krishnamurthy
 */


public class Pipe {

	int x, y;

	public Pipe (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean collide () {
		return true;
	}

	public void passed () {
		GamePanel.score ++;
	}

}

