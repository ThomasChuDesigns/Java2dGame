package tcd.game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static boolean up,down,left,right;
	public static boolean running;
	
	public void update(){
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		
		case KeyEvent.VK_ESCAPE: System.exit(0); break;
		
		case KeyEvent.VK_SHIFT: running = true; break;
		case KeyEvent.VK_W: up = true; break;
		case KeyEvent.VK_S: down = true; break;
		case KeyEvent.VK_A: left = true; break;
		case KeyEvent.VK_D: right = true; break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_SHIFT: running = false; break;
		case KeyEvent.VK_W: up = false; break;
		case KeyEvent.VK_S: down = false; break;
		case KeyEvent.VK_A: left = false; break;
		case KeyEvent.VK_D: right = false; break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
