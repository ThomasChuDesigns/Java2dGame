package tcd.game.entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import tcd.game.level.Level;
import tcd.game.main.AnimatedSprite;
import tcd.game.main.Assets;
import tcd.game.main.Game;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;
import tcd.game.text.BouncyText;

public class Slime extends Mob {
	
	private AnimatedSprite a_up;
	private AnimatedSprite a_down;
	private AnimatedSprite a_left;
	private AnimatedSprite a_right;
	
	private ArrayList<Sprite> list_up = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_down = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_left = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_right = new ArrayList<Sprite>();
	
	private Sprite DEATH;
	
	private Sprite hp_red;
	private Sprite hp_green;
	
	private int animationState = 1;
	
	private int timer = 0;
	private int rng;
	private int xpos = 0, ypos = 0;
	
	private int scale;
	
	private Stats stats;

	public Slime(int x, int y, int scale) {
		this.x = x << 5;
		this.y = y << 5;	
		this.scale = scale;
	}
	
	public void init(Level level){
		this.level = level;
		Sprite spawnSprite =  new Sprite(0,0,16,Assets.NPC);
		
		list_up.add(new Sprite(1,0,16,Assets.NPC));
		list_up.add(new Sprite(3,0,16,Assets.NPC));
		list_down.add(new Sprite(0,0,16,Assets.NPC));
		list_down.add(new Sprite(2,0,16,Assets.NPC));
		list_left.add(new Sprite(0,1,16,Assets.NPC));
		list_left.add(new Sprite(2,1,16,Assets.NPC));
		list_right.add(new Sprite(1,1,16,Assets.NPC));
		list_right.add(new Sprite(3,1,16,Assets.NPC));
		
		DEATH = new Sprite(4,0,16,Assets.NPC);
		
		a_up = new AnimatedSprite(list_up, 180);
		a_down = new AnimatedSprite(list_down, 180);
		a_left = new AnimatedSprite(list_left, 180);
		a_right = new AnimatedSprite(list_right, 180);
		
		hp_red = new Sprite(24,4,0xff0000);
		hp_green = new Sprite(24,4,0x00ff00);
		
		sprite = spawnSprite;
		
		initHealth(100);
		stats = new Stats(this.level, this);
	}
	
	private void move(){
		
		Player player = level.getPlayerRange(this, 80);
		
		if(player != null){
			timer = 0;
			if(x < player.getX())xpos++;
			if(x > player.getX())xpos--;
			if(y < player.getY() - 32)ypos++;
			if(y > player.getY() - 32)ypos--;	
		}else{
			timer++;
			if(timer % 60 == 0){
				rng = (int)(Math.random() * 4);
			}
			
				switch(rng){
				case 0: xpos--;
				case 1: xpos++;
				case 2: ypos--;
				case 3: ypos++;	
				}
		}
		
		if(xpos != 0 || ypos != 0){
			move(xpos,ypos);
			moving = true;
		}else{
			moving = false;
		}
		xpos = 0;
		ypos = 0;
	}
	
	public void update(){
		
		scaleHP = hp / maxHP;
		if(hp <= 0){
			dead = true;
		}
		
		if(dead){
			sprite = DEATH;
			timer++;
			if(timer % 60 == 0){
				remove();
			}
		}else{
		
		if(direction == Direction.UP){
			animationState = 1;
		}
		if(direction == Direction.DOWN){
			animationState = 2;
		}
		if(direction == Direction.LEFT){
			animationState = 3;
		}
		if(direction == Direction.RIGHT){
			animationState = 4;
		}
		
		switch(animationState){
		case 1:
			if(moving) a_up.update(System.currentTimeMillis());
			sprite = a_up.getSprite();
			break;
		case 2:
			if(moving) a_down.update(System.currentTimeMillis());
			sprite = a_down.getSprite();
			break;
		case 3:
			if(moving) a_left.update(System.currentTimeMillis());
			sprite = a_left.getSprite();
			break;
		case 4: 
			if(moving) a_right.update(System.currentTimeMillis());
			sprite = a_right.getSprite();
			break;
		}
		move();
		//System.out.println("X: " + x + ","+ xpos + " Y: " + y + ","+ ypos);
		}
	}

	public void render(Screen screen){
		int xrender = x - 8;
		int yrender = y - 8;
		
		screen.renderMob(xrender, yrender, this, scale);
		hp_green.setWidth((int)(24 * scaleHP));
		screen.renderSprite(xrender - 2, yrender - 26, hp_red, 1, true);
		screen.renderSprite(xrender - 2, yrender - 26, hp_green, 1, true);
		
	}
	
	public Stats getStats(){
		return stats;
	}
	
	public static class Stats{
		private Level level;
		private Slime slime;
		
		public static CopyOnWriteArrayList<BouncyText> list = new CopyOnWriteArrayList<BouncyText>();
		
		public Stats(Level level, Slime slime){
			this.level = level;
			this.slime = slime;
		}
		
		public void update(){
			for(BouncyText text : list){
				text.update();
			}		
		}
		
		public void render(Graphics2D g){
			
		}
		
		public void damage(int amount){
			
			String text = String.valueOf(amount).trim();
			int xrender = (slime.getX() - 8 - Screen.xOffset) + (64 * 16 / 3) + (text.length() * 32 / 3) + 32;
			int yrender = (slime.getY() - 8 - Screen.yOffset) + (12 * 16) - 40;
			
			if(slime.scaleHP > 0){
				slime.hp -= amount;
			}else{
				
			}
		}
	}

}
