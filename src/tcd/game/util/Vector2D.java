package tcd.game.util;

public class Vector2D {
	private int x, y;
	
	public Vector2D(int x, int y) {
		set(x,y);
	}
	
	public Vector2D(){
		set(0,0);
	}
	
	public Vector2D(Vector2D v){
		set(v.x, v.y);
	}
	
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public Vector2D setX(int x){
		this.x = x;
		return this;
	}
	
	public Vector2D setY(int y){
		this.y = y;
		return this;
	}
	
	public static double getDistance(Vector2D v1, Vector2D v2){
		double x = v1.getX() - v2.getX();
		double y = v1.getY() - v2.getY();
		return Math.sqrt(x*x + y*y);
	}

}
