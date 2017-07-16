package tcd.game.main;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static Spritesheet launcher;
	public static Spritesheet block;
	public static Spritesheet player;
	public static Spritesheet NPC;
	
	private static BufferedImage BUTTON_DEFAULT;
	private static BufferedImage BUTTON_OVER;
	
	private static BufferedImage MOUSE_UNPRESSED;
	private static BufferedImage MOUSE_PRESSED;
	
	public static Sprite VOID;
	public static Sprite STONE_1;
	public static Sprite GRASS_1;
	public static Sprite WALL_1;
	
	
	public Assets(){
	}
	
	public void init(){
		launcher = new Spritesheet("/textures/launchersheet.png",256);
		block = new Spritesheet("/textures/spritesheet.png",256);
		player = new Spritesheet("/textures/playersheet.png",256);
		NPC = new Spritesheet("/textures/mobSheet.png", 256);
		
		BUTTON_DEFAULT = launcher.getTile(4, 0, 64, 16); 
		BUTTON_OVER = launcher.getTile(4, 1, 64, 16);
		
		MOUSE_UNPRESSED = player.getTile(0, 2, 16, 16);
		MOUSE_PRESSED = player.getTile(1, 2, 16, 16);
		
		VOID = new Sprite(16, 0x2e4858);
		STONE_1 = new Sprite(0,0,16,block);
		GRASS_1  = new Sprite(2,0,16,block);
		WALL_1 = new Sprite(1,0,16,block);
		
	}
	
	public static BufferedImage getMOUSE_UNPRESSED(){
		return MOUSE_UNPRESSED;
	}
	
	public static BufferedImage getMOUSE_PRESSED(){
		return MOUSE_PRESSED;
	}
	
	public static BufferedImage getBUTTON_DEFAULT(){
		return BUTTON_DEFAULT;
	}
	
	public static BufferedImage getBUTTON_OVER(){
		return BUTTON_OVER;
	}
}
