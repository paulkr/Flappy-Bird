/**
 * InputListener.java
 * Listen for keyboard and mouse actions
 *
 * @author  Paul Krishnamurthy
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InputListener extends JPanel implements KeyListener, MouseListener {

	private static InputListener instance;
	private boolean mousePress;
	private boolean[] keys;

	public InputListener () {
		keys = new boolean[256];
	}

	/**
	 * Public method to access InputListener instance
	 * 
	 * @return     InputListener instance
	 */
	public static InputListener getInstance () {
		// Create new instance if one is not already there
		instance = instance == null ? new InputListener() : instance;
		return instance;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed (KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased (KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public boolean spacePress () {
		return keys[32];
	}

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}

}
