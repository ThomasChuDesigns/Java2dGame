package tcd.game.level;

import java.util.ArrayList;
import java.util.List;

import tcd.game.entity.Entity;
import tcd.game.entity.mob.Hoyo;
import tcd.game.entity.mob.Knight;
import tcd.game.entity.mob.Player;
import tcd.game.entity.mob.Slime;
import tcd.game.entity.mob.SmallKnight;
import tcd.game.gamestate.DeathState;
import tcd.game.gamestate.GameState;
import tcd.game.gamestate.GameStateManager;
import tcd.game.gamestate.WinState;
import tcd.game.level.Tile.TileType;
import tcd.game.main.Game;
import tcd.game.main.Screen;

public class Level {
	
	protected String level_name;
	protected int width, height;
	protected int tiles[];
	
	private static Player player;
	private List<Entity> entities = new ArrayList<Entity> ();
	
	private GameStateManager gsm;
	
	public Level(GameStateManager gsm, int width, int height) {
		this.gsm = gsm;
		this.width = width;
		this.height = height;
		tiles = new int[width *  height];
		generate();
		
	}
	
	public Level(GameStateManager gsm,String level_name,String path){
		this.gsm = gsm;
		this.level_name = level_name;
		player = new Player(Game.key);
		loadLevel(path);
		for(int i = 0; i < 5; i++){
			int rx = (int)(Math.random() * 14) + 1;
			int ry = (int)(Math.random() * 14) + 1;
			add(new Hoyo(rx, ry));
		}
		for(int i = 0; i < 3; i++){
			int rx = (int)(Math.random() * 14) + 1;
			int ry = (int)(Math.random() * 14) + 1;
			add(new Knight(rx, ry, 2));
		}
		add(new Slime(18,18,4));
		add(player);
		generate();
	}
	
	protected void loadLevel(String path){
		
	}
	
	protected void generate(){
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).init(this);
		}
	}
	
	public void update(){
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();	
		}
		
		if(entities.size() == 1){
			GameStateManager.states.push(new WinState(gsm));
			gsm.states.peek().init();
		}
		
		System.out.println(entities.size());
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 5;
		int x1 = (xScroll + screen.width + 64) >> 5;
		int y0 = yScroll >> 5;
		int y1 = (yScroll + screen.height + 64) >> 5;
		
		for(int y = y0; y< y1; y++){
			for(int x = x0; x < x1; x++){
				getTile(x,y).render(x, y, screen);
				}
 			}
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(screen);	
		}
		
		}
	
	public Tile getTile(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)return new Tile(TileType.VOID);
		int col = tiles[x + y * width] & 0xFFFFFF;
		switch(col){
		case 0x808080: return new Tile(TileType.STONE_1);
		case 0x404040: return new Tile(TileType.WALL_1).isSolid(true);
		case 0x00ff00: return new Tile(TileType.GRASS_1);
		}
		return new Tile(TileType.VOID);
	}
	
	public void add(Entity e){
		entities.add(e);
		e.init(this);
	}
	
	public static Player getPlayer(){
		return player;
	}
	
	public List<Slime> getAllSlimes(){
		List<Slime> result = new ArrayList<Slime>();
		for(int i = 0; i < entities.size(); i++){
			
			Entity e = entities.get(i);
			
			if(e instanceof Slime){
				result.add((Slime)e);
			}
		}
		return result;
	}
	
	public List<Entity> getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for(int i = 0; i < entities.size(); i++){
			Entity entity = entities.get(i);
			int x = entity.getX();
			int y = entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			
			if(distance <= radius && entity != player) result.add(entity);
			
		}
		
		return result;
	}
	
	public void removeEntity(Entity e){
		if(e instanceof Hoyo){
				add(new Slime(e.getX() >> 5, e.getY() >> 5, 2));
				add(new Slime(e.getX() + 32 >> 5, e.getY() + 32 >> 5, 2));
				add(new Slime(e.getX() >> 5, e.getY() + 32 >> 5, 2));
		}
		
		if(e instanceof Knight){
			((Knight) e).getStats().damage(getPlayer(), 25);
			add(new SmallKnight(e.getX() >> 5, e.getY() >> 5, 1));
			add(new SmallKnight(e.getX() + 32 >> 5, e.getY() + 32 >> 5, 1));
			add(new SmallKnight(e.getX() >> 5, e.getY() + 32 >> 5, 1));
		}
		if(e instanceof Player){
			GameStateManager.states.push(new DeathState(gsm));
			gsm.states.peek().init();
		}
	
		
		for(int i = 0; i < entities.size(); i++){
			Entity ent = entities.get(i);
			if(ent == e){
				entities.remove(i);
			}
		}
	}
	
	public Player getPlayerRange(Entity e, int radius){
		
		Player result = null;
		int ex = e.getX();
		int ey = e.getY();
		
		Player player = getPlayer();
		int x = player.getX();
		int y = player.getY();
		
		int dx = Math.abs(x - ex);
		int dy = Math.abs(y - ey);
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		
		if(distance <= radius) result = player;
		
		return result;
	}
	
	public void setPlayerSpawn(int x, int y){
		int xpos = x * 32;
		int ypos = y * 32;
		
		player.x = xpos;
		player.y = ypos;
		
	}
	
	public String getLevelName(){
		return level_name;
	}

}
