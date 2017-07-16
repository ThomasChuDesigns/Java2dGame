package tcd.game.gamestate;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tcd.game.main.Assets;
import tcd.game.main.Game;
import tcd.game.manager.Mouse;
import tcd.game.util.Vector2D;

public class GameStateButton extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private Vector2D pos = new Vector2D();
	
	private GameState gamestate;
	private GameStateManager gsm;
	private BufferedImage defaultImage;
	private String buttonText;
	
	private int width = 32 * 8;
	private int height = 32 * 2;
	
	private boolean isHeldOver;
	
	Font font = new Font("Arial",Font.BOLD,24);
	
	public GameStateButton(int x, int y, GameState gamestate, GameStateManager gsm, String buttonText) {
		this.gamestate = gamestate;
		this.gsm = gsm;
		pos.setX(x);
		pos.setY(y);
		this.buttonText = buttonText;
		defaultImage = Assets.getBUTTON_DEFAULT();
		setBounds(pos.getX(), pos.getY(), width, height);
	}
	
	public void update(){
		setBounds((int)pos.getX(), (int)pos.getY(), width, height);
		if(getBounds().contains(Game.mouse.mouse)){
			isHeldOver = true;
		}else{
			isHeldOver = false;
		}
		if(isHeldOver){
			if(defaultImage != Assets.getBUTTON_OVER()){
				defaultImage = Assets.getBUTTON_OVER();
			}
		}else {
			if(defaultImage != Assets.getBUTTON_DEFAULT()){
				defaultImage = Assets.getBUTTON_DEFAULT();
			}
		}
		
		if(gamestate != null){
			if(isHeldOver){
				if(isPressed()){
					gsm.states.push(gamestate);
					gsm.states.peek().init();
					isHeldOver = false;
					Game.mouse.pressed = false;
				}
			}
		}
	}
	public void render(Graphics2D g){
		g.setFont(font);
		font.deriveFont(Font.PLAIN, 24);
		g.drawImage(defaultImage, pos.getX(), pos.getY(), width, height, null);
		g.setFont(font);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		int tw = (int)font.getStringBounds(buttonText, frc).getWidth();
		
		g.drawString(buttonText, pos.getX() + width / 2 - tw / 2 , pos.getY() + height / 2 + 8);
	}
	
	public boolean isHeldOver(){
		return isHeldOver;
	}
	
	public boolean isPressed(){
		return Game.mouse.pressed;
	}


}
