/**
 * GamePanel.java
 * 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {

	private FlappyBird mainFrame;
	private boolean[] keys;

	public GamePanel (FlappyBird fb) {
		mainFrame = fb;
		keys = new boolean[256];
		addKeyListener(this);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		mainFrame.start();
	}

	public void move () {
		if (keys[KeyEvent.VK_SPACE]) {
			System.out.println("SPACE PRESSED");
		}
	}

	public void keyTyped (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased (KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}

