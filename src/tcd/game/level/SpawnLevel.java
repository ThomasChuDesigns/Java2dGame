package tcd.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tcd.game.entity.mob.Player;
import tcd.game.gamestate.GameStateManager;
import tcd.game.level.Tile.TileType;

public class SpawnLevel extends Level {
	
	public SpawnLevel(GameStateManager gsm,String level_name, String path) {
		super(gsm,level_name,path);
	}
	
	protected void loadLevel(String path){
		try {
			BufferedImage img = ImageIO.read(SpawnLevel.class.getResource(path));
			width = img.getWidth();
			height = img.getHeight();
			tiles = new int[width * height];
			img.getRGB(0, 0, width, height,tiles,0,width);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("No LEvels loaded");
		}
	}

}
