package tcd.game.level;

import tcd.game.main.Assets;
import tcd.game.main.Screen;
import tcd.game.main.Sprite;

public class Tile {
	
	public TileType tile;
	public Sprite sprite;
	
	private boolean isSolid;
	
	public Tile(TileType tile) {
		this.tile = tile;
		init();
	}
	
	public void init(){
		switch(tile){
		case STONE_1: sprite = Assets.STONE_1; break;
		case GRASS_1: sprite = Assets.GRASS_1; break;
		case VOID: sprite = Assets.VOID; break;
		case WALL_1: sprite = Assets.WALL_1; break;
		}
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 5, y << 5, this, 2);
	}
	
	public Tile isSolid(boolean solid){
		this.isSolid = solid;
		return this;
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public enum TileType{
		STONE_1,
		GRASS_1,
		VOID,
		WALL_1
	}

}
