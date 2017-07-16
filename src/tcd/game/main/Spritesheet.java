package tcd.game.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	private String PATH;
	public int SIZE;
	private int width, height;
	public int[] pixels;
	
	
	public Spritesheet(String PATH,int SIZE){
		this.PATH = PATH;
		this.SIZE = SIZE;
		pixels = new int[SIZE * SIZE];
		
		load();
	}
	
	public Spritesheet(String PATH){
		this.PATH = PATH;
		load();
	}
	
	public BufferedImage load(){
		try {
			spritesheet = ImageIO.read(Spritesheet.class.getResource(PATH));
			width = spritesheet.getWidth();
			height = spritesheet.getHeight();
			pixels = new int[width * height];
			
			spritesheet.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spritesheet;
	}
	
	public BufferedImage getTile(int x, int y, int width, int height){
		x = x * 16;
		y = y * 16;
		BufferedImage image = spritesheet.getSubimage(x, y, width, height);
		return image;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int[] getPixels(){
		return pixels;
	}

}
