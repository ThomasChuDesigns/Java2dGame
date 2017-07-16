package tcd.game.gamestate;

import java.awt.Graphics2D;
import java.util.List;

import tcd.game.entity.mob.Player;
import tcd.game.entity.mob.Slime;
import tcd.game.level.Level;
import tcd.game.level.SpawnLevel;
import tcd.game.main.Game;
import tcd.game.main.Screen;
import tcd.game.manager.HUD;

public class LevelState extends GameState {
	
	public static Level level;
	private GameStateManager gsm;
	private String level_name, level_path;
	
	private Player player;
	private HUD hud;
	
	int x = 0, y = 0;
	
	public LevelState(GameStateManager gsm) {
		super(gsm);
	}

	public LevelState(GameStateManager gsm, String level_name, String level_path) {
		super(gsm);
		this.level_name = level_name;
		this.level_path = level_path;
	}

	public void init() {
		level = new SpawnLevel(gsm,"SPAWN","/levels/spawn.png");
		level.setPlayerSpawn(16, 16);
		hud = new HUD(level);
	}

	public void update() {
		level.update();
		hud.update();
		Game.mouse.update();
	}

	public void render(Screen screen) {
		int xScroll = level.getPlayer().x - Game.WIDTH / 2;
		int yScroll =  level.getPlayer().y  - Game.HEIGHT / 2;
		level.render(xScroll, yScroll, screen);
	}
	
	public void renderG(Graphics2D g){
		//g.drawString("X: " + player.getPlayerX() + " Y: " + player.getPlayerY(), Game.WIDTH * Game.SCALE / 2, Game.HEIGHT * Game.SCALE  / 2);
		hud.render(g);
		
		List<Slime> slimes = level.getAllSlimes();
		for(int i = 0; i < slimes.size(); i++){
			slimes.get(i).getStats().render(g);
		}
		
	}

}
