package tcd.game.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import tcd.game.main.Assets;
import tcd.game.main.Game;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public class WinState extends GameState {
	
	GameStateButton restart;
	GameStateButton quit;
	
	String victory = "Congratulations, you have slain all enemies!";
	
	private Sprite hoyohead;
	
	public WinState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		hoyohead = new Sprite(9, 0, 16, Assets.launcher);
		restart = new GameStateButton((Game.WIDTH * Game.SCALE - 576) / 2, 464, new LevelState(gsm), gsm,"REPLAY");
		quit = new GameStateButton((Game.WIDTH * Game.SCALE + 64) / 2, 464 , new QuitState(gsm), gsm, "QUIT");
	}

	public void update() {
		restart.update();
		quit.update();
		Game.mouse.update();
	}

	public void render(Screen screen) {
		screen.renderSprite((screen.width / 2) - 16, (screen.height / 2) - 16, hoyohead, 6, false);
	}

	public void renderG(Graphics2D g) {
		
		g.setColor(Color.YELLOW);
		g.drawString(victory, (Game.FULL_WIDTH - victory.length() * 9) / 2, 164);
		
		restart.render(g);
		quit.render(g);
	}

}
