package tcd.game.text;

import tcd.game.level.Level;
import tcd.game.main.Game;
import tcd.game.main.Screen;

public class BouncyText {

	private Level level;
	
	private int x, y;
	private String text;
	
	private boolean isAlive,jump;
	
	private float lifeTime = 1.5f;
	private float maxJump = 1f;
	private float currentJump;
	
	private int color;
	
	public BouncyText(int x, int y, int color, String text, Level level) {
		this.level = level;
		
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
		
		this.isAlive = true;
		this.jump = true;
	}
	
	public void update(){
		if(isAlive){
			if(lifeTime <= 0) isAlive = false;
			if(lifeTime != 0) lifeTime -= 0.1;
			if(jump){
				if(currentJump >= maxJump) jump = false;
				if(currentJump < maxJump){
					currentJump += 0.5;
					this.y += currentJump;
				}
			}
			if(!jump){
				if(currentJump != 0){
					currentJump += 0.05;
					y += currentJump;
				}
				if(currentJump <= 0){
					maxJump -= 0.5;
					jump = true;
				}
			}
		}else{
			if(this != null){
				level.getPlayer().damagequeue.remove(this);
			}
		}
	}
	
	public void render(Screen screen){
		if(isAlive){
			Game.font.render(x, y - 64, text, color, screen);
		}
	}

}
