package tcd.game.entity;

import java.util.Random;

import tcd.game.level.Level;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public abstract class Entity {
	
	public int x,y;
	private boolean removed = false;
	protected Sprite sprite;
	protected Level level;

	protected boolean dead;
	
	protected final Random random = new Random();
	
	public void init(Level level){
		this.level = level;
	}
	public abstract void update();
	
	public void render(Screen screen){
		
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void remove(){
		removed = true;
		level.removeEntity(this);
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public boolean isDead(){
		return dead;
	}

}
