package tcd.game.gamestate;

import java.awt.Graphics2D;

import tcd.game.main.Screen;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Screen screen);
	public abstract void renderG(Graphics2D g);

}
