package engine.utils;

import engine.client.AbstractGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.event.MouseInputListener;

public class Input implements KeyListener, MouseMotionListener,
		MouseInputListener, MouseListener {
	AbstractGame game;
	private int mouseX;
	private int mouseY;
	private Hashtable<Integer, Boolean> keys = new Hashtable<Integer, Boolean>();
	private Hashtable<Integer, Boolean> wPressed = new Hashtable<Integer, Boolean>();
	private boolean lmb = false;
	private boolean rmb;

	public Input(AbstractGame game) {
		this.game = game;
		game.getCanvas().addKeyListener(this);
		game.getCanvas().addMouseListener(this);
		game.getCanvas().addMouseMotionListener(this);
	}

	public int getMouseX() {
		return Math.round(mouseX / game.getMulti());
	}

	public int getMouseY() {
		return Math.round(mouseY / game.getMulti());
	}

	public Vector getMouse() {
		return new Vector(getMouseX(), getMouseY());
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {

	}
	
	public boolean leftMouseButtonDown(){
		return lmb;
	}
	
	public boolean rightMouseButtonDown(){
		return rmb;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			lmb = true;
		if (e.getButton() == MouseEvent.BUTTON2)
			rmb = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			lmb = false;
		if (e.getButton() == MouseEvent.BUTTON2)
			rmb = false;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void keyPressed(KeyEvent e) {
		keys.put(e.getKeyCode(), true);
		wPressed.put(e.getKeyCode(), false);
	}

	public void keyReleased(KeyEvent e) {
		keys.put(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public boolean isKeyDown(int keyCode) {
		if (keys.get(keyCode) != null)
			return keys.get(keyCode);
		return false;
	}

	public boolean wasPressed(int keyCode) {
		if (wPressed.get(keyCode) == null) {
			wPressed.put(keyCode, true);
			return false;
		} else {
			if (!wPressed.get(keyCode)) {
				wPressed.put(keyCode, true);
				return false;
			} else
				return true;
		}
	}

	public boolean jPressed(int keyCode) {
		if (isKeyDown(keyCode) && !wasPressed(keyCode))
			return true;
		else
			return false;
	}

}
