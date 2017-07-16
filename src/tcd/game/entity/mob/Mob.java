package tcd.game.entity.mob;

import tcd.game.entity.Entity;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public abstract class Mob extends Entity {
	
	protected Direction direction;
	protected boolean moving = false;
	
	protected float hp;
	protected float maxHP;
	protected float scaleHP;
	
	
	protected enum Direction{
		UP,DOWN,LEFT,RIGHT
	}
	
	protected static int anim = 0;
	
	public void move(int xpos, int ypos){
		
		if(xpos != 0 && ypos != 0){
			move(xpos,0);
			move(0,ypos);
			return;
		}
		
		if(ypos < 0)direction = Direction.UP;
		if(ypos > 0)direction = Direction.DOWN;
		if(xpos < 0)direction = Direction.LEFT;
		if(xpos > 0)direction = Direction.RIGHT;
		
		if(!collision(xpos,ypos)){
			x += xpos;
			y += ypos;	
		}
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	public boolean collision(int xpos, int ypos){
		boolean solid = false;
		for(int corner = 0; corner < 4; corner++){
			int xt = ((x + xpos) + corner % 2 * 16) / 32;
			int yt = ((y + ypos) + corner / 2 * 32 - 8) / 32;
			if(level.getTile(xt, yt).isSolid()) solid = true;	
		}
		return solid;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	//STATS MODIFICATION
	public void initHealth(float maxHP){
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.scaleHP = this.hp / this.maxHP;
	}
	public float getHP(){
		return hp;
	}
	
	public void setHP(float amount){
		hp = amount;
	}

}
