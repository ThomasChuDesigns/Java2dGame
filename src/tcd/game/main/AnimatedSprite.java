package tcd.game.main;

import java.util.ArrayList;

public class AnimatedSprite {
	
	private ArrayList<Sprite> sprites;
	private Sprite currentSprite;
	
	private long prevTime, speed;
	private int currentFrame, frameAtPause;
	
	private volatile boolean isRunning = false;
	
	public AnimatedSprite(ArrayList<Sprite> sprites, long speed){
		this.sprites = sprites;
		this.speed = speed;
		
		currentSprite = sprites.get(0);
		play();
	}
	
	
	public void setSpeed(long speed){
		this.speed = speed;
	}
	
	public void update(long time){
		if(isRunning){
			if(time - prevTime >= speed ){
				currentFrame++;
				try{
					if(currentFrame <= sprites.size()) currentSprite = sprites.get(currentFrame);
					else reset();
				}catch(IndexOutOfBoundsException e){
					reset();
					currentSprite = sprites.get(currentFrame);
				}
				prevTime = time;
			}
		}
	}
	
	public void play(){
		isRunning = true;
		prevTime = 0;
		currentFrame = 0;
		frameAtPause = 0;
	}
	
	public void stop(){
		isRunning = false;
		prevTime = 0;
		currentFrame = 0;
		frameAtPause = 0;
	}
	
	private void reset(){
		currentFrame = 0;
	}
	
	public Sprite getSprite(){
		return currentSprite;
	}
	
	
	
}
