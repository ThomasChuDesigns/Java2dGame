package tcd.game.entity.mob;

import java.util.ArrayList;

import tcd.game.entity.mob.Mob.Direction;
import tcd.game.level.Level;
import tcd.game.main.AnimatedSprite;
import tcd.game.main.Assets;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public class Hoyo extends Mob {

	
	private Sprite UP;
	private Sprite DOWN;
	private Sprite LEFT;
	private Sprite RIGHT;
	
	private Sprite UP1;
	private Sprite DOWN1;
	private Sprite LEFT1;
	private Sprite RIGHT1;
	
	private Sprite DEATH;
	
	private AnimatedSprite a_up;
	private AnimatedSprite a_down;
	private AnimatedSprite a_left;
	private AnimatedSprite a_right;
	
	private ArrayList<Sprite> list_up = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_down = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_left = new ArrayList<Sprite>();
	private ArrayList<Sprite> list_right = new ArrayList<Sprite>();
	
	private Sprite hp_red;
	private Sprite hp_green;
	
	
	private int animationState;
	
	private int timer = 0;
	private int rng;
	private int xpos = 0, ypos = 0;

	public Hoyo(int x, int y) {
		this.x = x << 5;
		this.y = y << 5;	
	}
	
	public void init(Level level){
		this.level = level;

		UP = new Sprite(0,2,16,Assets.NPC);
		DOWN = new Sprite(1,2,16,Assets.NPC);
		LEFT = new Sprite(2,2,16,Assets.NPC);
		RIGHT = new Sprite(3,2,16,Assets.NPC);
		
		UP1 = new Sprite(0,3,16,Assets.NPC);
		DOWN1 = new Sprite(1,3,16,Assets.NPC);
		LEFT1 = new Sprite(2,3,16,Assets.NPC);
		RIGHT1 = new Sprite(3,3,16,Assets.NPC);
		
		DEATH = new Sprite(4,2,16,Assets.NPC);
		
		list_up.add(UP);
		list_up.add(UP1);
		
		list_down.add(DOWN);
		list_down.add(DOWN1);
		
		list_left.add(LEFT);
		list_left.add(LEFT1);
		
		list_right.add(RIGHT);
		list_right.add(RIGHT1);
		
		a_up = new AnimatedSprite(list_up, 500);
		a_down = new AnimatedSprite(list_down, 500);
		a_left = new AnimatedSprite(list_left, 500);
		a_right = new AnimatedSprite(list_right, 500);
		
		hp_red = new Sprite(24,5,0xff0000);
		hp_green = new Sprite(24,5,0x00ff00);
		
		sprite = DOWN;
		
		initHealth(200);
		
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
				animationState = 0;
			}
			if(direction == Direction.DOWN){
				animationState = 1;
			}
			if(direction == Direction.LEFT){
				animationState = 2;
			}
			if(direction == Direction.RIGHT){
				animationState = 3;
			}
			
			switch(animationState){
			case 0:
				if(moving) a_up.update(System.currentTimeMillis());
				sprite = a_up.getSprite();
				break;
			case 1:
				if(moving) a_down.update(System.currentTimeMillis());
				sprite = a_down.getSprite();
				break;
			case 2:
				if(moving) a_left.update(System.currentTimeMillis());
				sprite = a_left.getSprite();
				break;
			case 3: 
				if(moving) a_right.update(System.currentTimeMillis());
				sprite = a_right.getSprite();
				break;
			}
			
			move();	
		}
		
	}
	
	public void render(Screen screen){
		int xrender = x - 8;
		int yrender = y - 8;
		
		screen.renderMob(xrender, yrender, this, 4);
		
		hp_green.setWidth((int)(24 * scaleHP));
		screen.renderSprite(xrender - 2, yrender - 30, hp_red, 1, true);
		screen.renderSprite(xrender - 2, yrender - 30, hp_green, 1, true);
	}
}
