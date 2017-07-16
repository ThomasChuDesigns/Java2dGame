package tcd.game.util;

import tcd.game.main.Screen;

public class Debug {

	public Debug() {
		
	}
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed){
		screen.drawRect(x,y,width,height,0xff0000,fixed);
	}
	public static void drawRect(Screen screen, int x, int y, int width, int height, int col, boolean fixed){
		screen.drawRect(x,y,width,height,col,fixed);
	}

}
