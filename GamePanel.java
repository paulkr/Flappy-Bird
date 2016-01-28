
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {

	private FlappyBird mainFrame;
	private boolean[] keys;

	public GamePanel (FlappyBird fb) {
		mainFrame = fb;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		addKeyListener(this);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed (KeyEvent e) {
		System.out.println(e.getKeyCode());
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased (KeyEvent e) {
		System.out.println(e.getKeyCode());
		keys[e.getKeyCode()] = false;
	}

	public boolean spacePressed () {
		return keys[32] = true;
	}

}

