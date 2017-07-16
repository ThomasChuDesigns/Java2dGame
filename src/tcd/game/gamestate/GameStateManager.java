package tcd.game.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;

import tcd.game.level.Level;
import tcd.game.main.Screen;

public class GameStateManager {
	
	public static Stack<GameState> states;
	
	private Screen screen;

	public GameStateManager() {
		states = new Stack<GameState>();
		states.push(new StartState(this));
	}
	
	public void init(){
		states.peek().init();
	}
	
	public void update(){
		states.peek().update();
	}
	
	public void render(Screen screen){
		states.peek().render(screen);
		this.screen = screen;
	}
	
	public void renderG(Graphics2D g){
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.setColor(Color.WHITE);
		states.peek().renderG(g);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
	}
	
}
