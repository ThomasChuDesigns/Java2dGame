package tcd.game.main;

import tcd.game.entity.mob.Mob;
import tcd.game.level.Tile;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	private final int mapSize = 8;
	
	public static int xOffset, yOffset;
	
	public int[] tiles = new int[mapSize * mapSize];
	
	public Screen(int width,int height){
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile, int scale){
		xp -= xOffset;
		yp -= yOffset;
		
		int scaleMap = scale - 1;
		
		for(int y = 0; y < tile.sprite.SIZE; y ++){
			int yy = y + yp;
			int yPixel = yy + (y *scaleMap) - ((scaleMap << 4) / 2);
			for(int x = 0; x < tile.sprite.SIZE; x++){
				int xx = x + xp;
				int xPixel = xx + (x *scaleMap) - ((scaleMap << 4) / 2);
				
				int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				
				for(int yScale = 0; yScale < scale; yScale++){
					for(int xScale = 0; xScale < scale; xScale++){
						if(xPixel + xScale < -32 || xPixel + xScale >= width || yPixel + yScale < 0 || yPixel + yScale >= height) break;
						if(xPixel + xScale < 0) xPixel = 0;
						pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
					}
				}
				
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob, int scale){
		xp -= xOffset;
		yp -= yOffset;
		
		int scaleMap = scale - 1;
		
		for(int y = 0; y < mob.getSprite().SIZE; y ++){
			int yy = y + yp;
			int yPixel = yy + (y * scaleMap) - ((scaleMap << 4) / 2);
			for(int x = 0; x < mob.getSprite().SIZE; x++){
				int xx = x + xp;
				int xPixel = xx + (x * scaleMap) - ((scaleMap << 4) / 2);
				
				int col = mob.getSprite().pixels[x + y * mob.getSprite().SIZE];
				
				for(int yScale = 0; yScale < scale; yScale++){
					for(int xScale = 0; xScale < scale; xScale++){
						if(xPixel + xScale < -mob.getSprite().SIZE || xPixel + xScale >= width || yPixel + yScale < 0 || yPixel + yScale >= height) break;
						if(xPixel + xScale < 0) xPixel = 0;
						if(col != 0xFFFF00FF) pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
					}
				}
				
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int scale){
		xp -= xOffset;
		yp -= yOffset;
		
		int scaleMap = scale - 1;
		
		for(int y = 0; y < sprite.SIZE; y ++){
			int yy = y + yp;
			int yPixel = yy + (y * scaleMap) - ((scaleMap << 4) / 2);
			for(int x = 0; x < sprite.SIZE; x++){
				int xx = x + xp;
				int xPixel = xx + (x * scaleMap) - ((scaleMap << 4) / 2);
				
				int col = sprite.pixels[x + y * sprite.SIZE];
				
				for(int yScale = 0; yScale < scale; yScale++){
					for(int xScale = 0; xScale < scale; xScale++){
						if(xPixel + xScale < -sprite.SIZE || xPixel + xScale >= width || yPixel + yScale < 0 || yPixel + yScale >= height) break;
						if(xPixel + xScale < 0) xPixel = 0;
						if(col != 0xFFFF00FF) pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
					}
				}
				
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, int scale, boolean fixed){
		
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;	
		}
	
		int scaleMap = scale - 1;
		
		for(int y = 0; y < sprite.getHeight(); y ++){
			int yy = y + yp;
			int yPixel = yy + (y * scaleMap) - ((scaleMap << 4) / 2);
			for(int x = 0; x < sprite.getWidth(); x++){
				int xx = x + xp;
				int xPixel = xx + (x * scaleMap) - ((scaleMap << 4) / 2);
				
				int col = sprite.pixels[x + y * sprite.getWidth()];
				
				for(int yScale = 0; yScale < scale; yScale++){
					for(int xScale = 0; xScale < scale; xScale++){
						if(xPixel + xScale < -sprite.SIZE || xPixel + xScale >= width || yPixel + yScale < 0 || yPixel + yScale >= height) continue;
						if(xPixel + xScale < 0) xPixel = 0;
						if(col != 0xFFFF00FF) pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
					}
				}
				
			}
		}
	}
	
	public void renderTextCharacter(int xp, int yp, Sprite sprite,int color, int scale, boolean fixed){
		
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;	
		}
	
		int scaleMap = scale - 1;
		
		for(int y = 0; y < sprite.getHeight(); y ++){
			int yy = y + yp;
			int yPixel = yy + (y * scaleMap) - ((scaleMap << 4) / 2);
			for(int x = 0; x < sprite.getWidth(); x++){
				int xx = x + xp;
				int xPixel = xx + (x * scaleMap) - ((scaleMap << 4) / 2);
				
				int col = sprite.pixels[x + y * sprite.getWidth()];
				
				for(int yScale = 0; yScale < scale; yScale++){
					for(int xScale = 0; xScale < scale; xScale++){
						if(xPixel + xScale < -sprite.SIZE || xPixel + xScale >= width || yPixel + yScale < 0 || yPixel + yScale >= height) continue;
						if(xPixel + xScale < 0) xPixel = 0;
						if(col != 0xFFFF00FF) pixels[(xPixel + xScale) + (yPixel + yScale) * width] = color;
					}
				}
				
			}
		}
	}
	
	public void drawRect(int xp, int yp, int width, int height, int col, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;	
		}
		for(int x = xp; x < xp + width; x++){
			if(x < 0 || x >= this.width || yp >= this.height)continue;
			if(yp > 0)pixels[x + yp * this.width] = col;
			if(yp + height >= this.height)continue;
			if(yp +  height > 0)pixels[x + (yp + height) * this.width] = col;
		}
		for(int y = yp; y <= yp + height; y++){
			if(xp >= this.width || y < 0 || y >= this.height)continue;
			if(xp > 0)pixels[xp + y * this.width] = col;
			if(xp + width >= this.width)continue;
			if(xp + width > 0)pixels[(xp + width) + y * this.width] = col;
		}
		
	}
	
	public void setOffset(int xOff, int yOff){
		this.xOffset = xOff;
		this.yOffset = yOff;
	}
	
	public int getOffsetX(){
		return xOffset;
	}
	
	public int getOffsetY(){
		return yOffset;
	}

}
