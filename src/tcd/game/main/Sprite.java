package tcd.game.main;

public class Sprite {
	
	private int x,y;
	public final int SIZE;
	private int WIDTH, HEIGHT;
	
	public int[] pixels;
	private Spritesheet sheet;
	
	public Sprite(int x, int y, int SIZE, Spritesheet sheet) {
		this.SIZE = SIZE;
		this.WIDTH = SIZE;
		this.HEIGHT = SIZE;
		pixels = new int[WIDTH * HEIGHT];
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		
		load();
	}
	
	public Sprite(int width, int height, int color){
		SIZE = -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		setColor(color);
	}
	
	
	public Sprite(int size, int color){
		this.SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height){
		SIZE = (width == height) ? width : -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++){
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite[] split(Spritesheet sheet){
		int amount = (sheet.getWidth() * sheet.getHeight()) / 256;
		Sprite[] sprites = new Sprite[amount];
		
		int current = 0;
		int[] pixels = new int[256];
		
		for(int yp = 0; yp < sheet.getHeight() / 16; yp++){
			for(int xp = 0; xp < sheet.getWidth() / 16; xp++){
				
				for(int y = 0; y < 16; y++){
					for(int x = 0; x < 16; x++){
						int xoff = x + xp * 16;
						int yoff = y + yp * 16;
						
						pixels[x + y * 16] = sheet.getPixels()[xoff + yoff * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, 16, 16);
			}
		}
		
		return sprites;
	}
	
	public void setColor(int color){
		for(int i = 0; i < WIDTH * HEIGHT; i++){
			pixels[i] = color;
		}
	}
	
	public int getWidth(){
		return WIDTH;
	}
	public int getHeight(){
		return HEIGHT;
	}
	
	public void setWidth(int width){
		this.WIDTH = width;
	}
	public void setHeight(int height){
		this.HEIGHT = height;
	}
	
	public void load(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}

}
