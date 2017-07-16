package tcd.game.entity.mob;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import tcd.game.entity.Entity;
import tcd.game.entity.mob.Mob.Direction;
import tcd.game.level.Level;
import tcd.game.main.AnimatedSprite;
import tcd.game.main.Assets;
import tcd.game.main.Keyboard;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;
import tcd.game.text.BouncyText;
import tcd.game.util.Debug;
import tcd.game.util.Vector2D;

public class Player extends Mob {
	
	private Keyboard input;
	private PlayerActions playerActions;
	private PlayerStats playerStats;
	
	private int a_state;
	private int a_speed = 180;
	
	private AnimatedSprite a_up;
	private AnimatedSprite a_down;
	private AnimatedSprite a_left;
	private AnimatedSprite a_right;
	
	private ArrayList<Sprite> up = new ArrayList<Sprite>();
	private ArrayList<Sprite> down = new ArrayList<Sprite>();
	private ArrayList<Sprite> left = new ArrayList<Sprite>();
	private ArrayList<Sprite> right = new ArrayList<Sprite>();
	
	private static Sprite ATTACK_UP;
	private static Sprite ATTACK_DOWN;
	private static Sprite ATTACK_LEFT;
	private static Sprite ATTACK_RIGHT;
	
	private static Sprite DEATH;
	
	private Sprite hp_red;
	private Sprite hp_green;
	public static CopyOnWriteArrayList<BouncyText> damagequeue = new CopyOnWriteArrayList<BouncyText>();
	
	private int xpos = 0, ypos = 0;
	
	private int timer = 0;
	private int size = 8;
	
	private Entity closest;
	
	public Player(Keyboard input){
		this.input = input;
	}
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void init(Level level){
		this.level = level;
		playerActions = new PlayerActions(level, this);
		playerStats = new PlayerStats(level, this);
		
		Sprite spawnSprite = new Sprite(0,0,16,Assets.player);
		
		up.add(new Sprite(2,0,16,Assets.player));
		up.add(new Sprite(3,0,16,Assets.player));
		down.add(new Sprite(0,0,16,Assets.player));
		down.add(new Sprite(1,0,16,Assets.player));
		left.add(new Sprite(2,1,16,Assets.player));
		left.add(new Sprite(3,1,16,Assets.player));
		right.add(new Sprite(0,1,16,Assets.player));
		right.add(new Sprite(1,1,16,Assets.player));
		
		a_up = new AnimatedSprite(up, a_speed);
		a_down = new AnimatedSprite(down, a_speed);
		a_left = new AnimatedSprite(left, a_speed);
		a_right = new AnimatedSprite(right, a_speed);
		
		ATTACK_UP = new Sprite(2,2,16,Assets.player);
		ATTACK_DOWN = new Sprite(3,2,16,Assets.player);
		ATTACK_LEFT = new Sprite(2,3,16,Assets.player);
		ATTACK_RIGHT = new Sprite(3,3,16,Assets.player);
		
		DEATH = new Sprite(4,1,16,Assets.player);
		
		hp_red = new Sprite(24,4,0xff0000);
		hp_green = new Sprite(24,4,0x00ff00);
		
		sprite = spawnSprite;
		
		initHealth(100);
	}
	
	private void proximityCheck(){
		List<Entity> elist = level.getEntities(this, 48);
		closest = null;
		double min = 0;
		for(int i = 0; i < elist.size(); i++){
			Entity ent = elist.get(i);
			double distance = Vector2D.getDistance(new Vector2D(getX(), getY()), new Vector2D(ent.getX(), ent.getY()));
			if(i == 0 || min < distance) {
				min = distance;
				closest = ent;
			}
		}
	}
	
	private void move(){
		if(input.running){
			playerActions.setMoveSpeed(playerActions.baseSpeed * 2);
		}else{
			playerActions.setMoveSpeed(playerActions.baseSpeed);
		}
		
		if(input.up) ypos-= playerActions.moveSpeed;
		if(input.down) ypos+= playerActions.moveSpeed;
		if(input.left) xpos-= playerActions.moveSpeed;
		if(input.right) xpos+= playerActions.moveSpeed;
		
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
		proximityCheck();
		
		for(BouncyText text : damagequeue){
			text.update();
		}
		
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
			a_state = 0;
		}
		if(direction == Direction.DOWN){
			a_state = 1;
		}
		if(direction == Direction.LEFT){
			a_state = 2;
		}
		if(direction == Direction.RIGHT){
			a_state = 3;
		}
		
		switch(a_state){
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
		
		playerActions.update();
		move();
		
		}
		
	}
	
	public void render(Screen screen){ 
		
		int xrender = x - size;
		int yrender = y - size;
		
		if(playerActions.getAttackState() != null){
			if(!playerActions.isComplete()){
				if(playerActions.hasAttacked()){
					if(playerActions.getAttackState() == ATTACK_UP)screen.renderSprite(xrender, yrender - 16, playerActions.getAttackState(), 3, true);	
					if(playerActions.getAttackState() == ATTACK_DOWN) screen.renderSprite(xrender, yrender + 16, playerActions.getAttackState(), 3, true);
					if(playerActions.getAttackState() == ATTACK_LEFT) screen.renderSprite(xrender - 16, yrender + 2, playerActions.getAttackState(), 3, true);
					if(playerActions.getAttackState() == ATTACK_RIGHT) screen.renderSprite(xrender + 16, yrender + 2, playerActions.getAttackState(), 3, true);
				}
			}
		}
		
		screen.renderMob(xrender, yrender, sprite, 2);
		hp_green.setWidth((int)(24 * scaleHP));
		screen.renderSprite(xrender - 4, yrender - 14, hp_red, 1, true);
		screen.renderSprite(xrender - 4, yrender - 14, hp_green, 1, true);
		for(BouncyText text : damagequeue){
			text.render(screen);
		}
		
	}
	
	public void setDirection(int dir){
		switch(dir){
		case 0: direction = Direction.UP; break;
		case 1: direction = Direction.DOWN; break;
		case 2: direction = Direction.LEFT; break;
		case 3: direction = Direction.RIGHT; break;
		}
	}
	
	public PlayerActions getPlayerActions(){
		return playerActions;
	}
	
	public PlayerStats getPlayerStats(){
		return playerStats;
	}
	
	public Entity getClosestMob(){
		return closest;
	}
	
	
	public static class PlayerActions{
		
		private Level level;
		private Player player;
		private Sprite attack_state;
		
		private static boolean hasCompleted = true;
		private static boolean attack = false;
		
		private double attackSpeed = 2;
		private double baseSpeed = 1;
		private double moveSpeed = baseSpeed;
		
		public PlayerActions(Level level, Player player){
			this.level = level;
			this.player = player;
		}
		
		public void update(){
			if(!hasCompleted){
				if(attack){
					startAttack();	
				}
			}
		}
		
		public void setMoveSpeed(double speed){
			moveSpeed = speed;
		}
		
		public void startAttack(){
			if(attackSpeed != 0) attackSpeed -= 0.2;
			if(attackSpeed <= 0){
				attack = false;
				hasCompleted = true;
				attack_state = null;
				
				attackSpeed = 1;
			}
		}
		
		public void attackUP(){
			attack_state = Player.ATTACK_UP;
			attack = true;
			hasCompleted = false;
		}
		public void attackDOWN(){
			attack_state = Player.ATTACK_DOWN;
			attack = true;
			hasCompleted = false;
		}
		public void attackLEFT(){
			attack_state = Player.ATTACK_LEFT;
			attack = true;
			hasCompleted = false;
		}
		public void attackRIGHT(){
			attack_state = Player.ATTACK_RIGHT;
			attack = true;
			hasCompleted = false;
		} 
		
		public Sprite getAttackState(){
			return attack_state;
		}
		
		public boolean isComplete(){
			return hasCompleted;
		}
		
		public boolean hasAttacked(){
			return attack;
		}
		
	}	
	
	public static class PlayerStats{
		private Level level;
		private Player player;
		

		
		public PlayerStats(Level level, Player player){
			this.level = level;
			this.player = player;
		}
		
		public void damage(Entity e, int amount){
			
			int damage = (int)((Math.random() * amount) + (amount / 2));
			double rng = Math.random();
			boolean crit = false;
			
			if(rng <= 0.2){
				crit = true;
				damage *= 2;
			}
			
			
			String text = String.valueOf(damage).trim();
			if(e != null && e instanceof Mob && ((Mob) e).getHP() > 0){
				if(crit)player.damagequeue.add(new BouncyText(e.getX(), e.getY(), 0xff9600, text, level));
				else player.damagequeue.add(new BouncyText(e.getX(), e.getY(), 0x950000, text, level));
				((Mob) e).setHP(((Mob) e).getHP() - damage);
				
				Mob ent = (Mob)e;
				knockback(ent, 8);
				
			}
		}
		
		public void knockback(Mob mob,int amount){
			if(mob.getDirection() == Direction.UP){
				mob.move(0,-amount);
			}
			if(mob.getDirection() == Direction.DOWN){
				mob.move(0,-amount);
			}
			if(mob.getDirection() == Direction.LEFT){
				mob.move(amount,0);
			}
			if(mob.getDirection() == Direction.RIGHT){
				mob.move(-amount,0);
			}
		}
		
		
	}
}
