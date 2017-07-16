package tcd.game.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tcd.game.main.Assets;
import tcd.game.main.Game;
import tcd.game.main.Screen;
import tcd.game.main.Spritesheet;

public class StartState extends GameState {

	GameStateButton startGame;
	GameStateButton quit;
	
	private BufferedImage menu;
	
	public StartState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
		try {
			menu = ImageIO.read(Spritesheet.class.getResource("/textures/menu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		startGame = new GameStateButton((Game.WIDTH * Game.SCALE - 256) / 2, 300, new LevelState(gsm), gsm,"PLAY");
		quit = new GameStateButton((Game.WIDTH * Game.SCALE - 256) / 2, 396 , new QuitState(gsm), gsm, "QUIT");
	}

	public void update() {
		Game.mouse.update();
		startGame.update();
		quit.update();
	}

	public void renderG(Graphics2D g) {
		g.drawImage(menu,0,0,1280,720,null);
		g.drawImage(Assets.launcher.getTile(0, 0, 64, 32), (Game.WIDTH * Game.SCALE - 512) / 2, 50, 512,256,null);

		quit.render(g);
		startGame.render(g);
	}

	public void render(Screen screen) {
		
	}
}
