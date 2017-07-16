package tcd.game.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import tcd.game.main.Assets;
import tcd.game.main.Game;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public class DeathState extends GameState {

	GameStateButton respawn;
	GameStateButton quit;
	
	private String text = "You Are Dead!";
	
	private Sprite hoyohead;
	
	public DeathState(GameStateManager gsm){
		super(gsm);
	}

	public void init() {
		hoyohead = new Sprite(8, 0, 16, Assets.launcher);
		respawn = new GameStateButton((Game.WIDTH * Game.SCALE - 576) / 2, 464, new LevelState(gsm), gsm,"RESPAWN");
		quit = new GameStateButton((Game.WIDTH * Game.SCALE + 64) / 2, 464 , new QuitState(gsm), gsm, "QUIT");
	}

	public void update() {
		respawn.update();
		quit.update();
		Game.mouse.update();
	}

	public void renderG(Graphics2D g) {
		
		//g.drawImage(Assets.deadbg, 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE, null);
		g.setColor(Color.WHITE);
		g.drawString(text, (Game.WIDTH * Game.SCALE - text.length() * 12) / 2, 200);
		//g.drawImage(Assets.getbt(), (Game.WIDTH * Game.SCALE - 128) / 2, 264, 128, 128, null);
		g.setColor(Color.YELLOW);
		
		respawn.render(g);
		quit.render(g);
	}

	public void render(Screen screen) {
		screen.renderSprite((screen.width / 2) - 16, (screen.height / 2) - 16, hoyohead, 6, false);
	}
}
