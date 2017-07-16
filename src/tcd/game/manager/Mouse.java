package tcd.game.manager;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import tcd.game.level.Level;
import tcd.game.main.Assets;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener  {
	
	public int mouseMovedX, mouseMovedY;
	public Point mouse;
	public boolean pressed;
	
	public Mouse() {
		
	}
	
	public void update(){
		mouse = new Point(mouseMovedX, mouseMovedY);
		if(Level.getPlayer() != null){
			if(Level.getPlayer().getPlayerActions().isComplete()){
				if(!Level.getPlayer().getPlayerActions().hasAttacked()){
					
					if(HUD.getUpPol() != null){
						if(HUD.getUpPol().contains(mouse)){
							if(pressed){
								Level.getPlayer().setDirection(0);
								Level.getPlayer().getPlayerActions().attackUP();
								Level.getPlayer().getPlayerStats().damage(Level.getPlayer().getClosestMob(),20);
								pressed = false;
							}
						}
					}
					
					if(HUD.getDownPol() != null){
						if(HUD.getDownPol().contains(mouse)){
							if(pressed){
								Level.getPlayer().setDirection(1);
								Level.getPlayer().getPlayerActions().attackDOWN();
								Level.getPlayer().getPlayerStats().damage(Level.getPlayer().getClosestMob(),20);
								pressed = false;
							}
						}
					}
					
					if(HUD.getLeftPol() != null){
						if(HUD.getLeftPol().contains(mouse)){
							if(pressed){
								Level.getPlayer().setDirection(2);
								Level.getPlayer().getPlayerActions().attackLEFT();
								Level.getPlayer().getPlayerStats().damage(Level.getPlayer().getClosestMob(),20);
								pressed = false;

							}
						}
					}
					
					if(HUD.getRightPol() != null){
						if(HUD.getRightPol().contains(mouse)){
							if(pressed){
								Level.getPlayer().setDirection(3);
								Level.getPlayer().getPlayerActions().attackRIGHT();
								Level.getPlayer().getPlayerStats().damage(Level.getPlayer().getClosestMob(),20);
								pressed = false;
							}
						}
					}
					
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(pressed){
			g.drawImage(Assets.getMOUSE_PRESSED(), mouseMovedX, mouseMovedY, 32, 32, null);
		}else{
			g.drawImage(Assets.getMOUSE_UNPRESSED(), mouseMovedX, mouseMovedY, 32, 32, null);
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			pressed = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

}
