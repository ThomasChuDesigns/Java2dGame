package tcd.game.manager;

import java.awt.Graphics2D;
import java.awt.Polygon;

import tcd.game.level.Level;
import tcd.game.main.Game;

public class HUD {
	
	private static Polygon up;
	private static Polygon down;
	private static Polygon left;
	private static Polygon right;
	
	private Level level;
	
	public HUD(Level level) {
		this.level = level;
	}
	
	public void update(){
	}
	
	public void render(Graphics2D g){
		int[] ux = new int[]{Game.WIDTH * Game.SCALE - 1,Game.WIDTH * Game.SCALE / 2,Game.WIDTH * Game.SCALE / 2,0};
		int[] uy = new int[]{0,Game.HEIGHT * Game.SCALE / 2,Game.HEIGHT * Game.SCALE / 2,0};
		up = new Polygon(ux,uy,ux.length);
		
		int[] dx = new int[]{Game.WIDTH * Game.SCALE - 1,Game.WIDTH * Game.SCALE / 2,Game.WIDTH * Game.SCALE / 2,0};
		int[] dy = new int[]{Game.HEIGHT * Game.SCALE - 1,Game.HEIGHT * Game.SCALE / 2,Game.HEIGHT * Game.SCALE / 2,Game.HEIGHT * Game.SCALE - 1};
		down = new Polygon(dx,dy,dx.length);
		
		int[] lx = new int[]{0,Game.WIDTH * Game.SCALE / 2,Game.WIDTH * Game.SCALE / 2,0};
		int[] ly = new int[]{Game.HEIGHT * Game.SCALE,Game.HEIGHT * Game.SCALE / 2,Game.HEIGHT * Game.SCALE / 2,0};
		left = new Polygon(lx,ly,lx.length);
		
		int[] rx = new int[]{Game.WIDTH * Game.SCALE - 1,Game.WIDTH * Game.SCALE / 2,Game.WIDTH * Game.SCALE / 2, Game.WIDTH * Game.SCALE - 1};
		int[] ry = new int[]{Game.HEIGHT * Game.SCALE,Game.HEIGHT * Game.SCALE / 2,Game.HEIGHT * Game.SCALE / 2,0};
		right = new Polygon(rx,ry,rx.length);
		
		g.drawString(level.getLevelName(),18,30);
		g.drawString("X: " + level.getPlayer().getX() + " Y: " + level.getPlayer().getY(), 18, 48);
	}
	
	public static Polygon getUpPol(){
		return up;
	}
	public static Polygon getDownPol(){
		return down;
	}
	public static Polygon getLeftPol(){
		return left;
	}
	public static Polygon getRightPol(){
		return right;
	}

}
