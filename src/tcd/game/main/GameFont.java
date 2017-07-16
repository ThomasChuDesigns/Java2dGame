package tcd.game.main;

public class GameFont {
	
	private static Spritesheet font = new Spritesheet("/textures/fonts.png");
	private static Sprite[] characters = Sprite.split(font);
	
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + //
								  "abcdefghijklmnopqrstuvwxyz" + //
								  "1234567890";
	
	public GameFont() {
		
	}
	public void render(int x, int y, String text, int color,Screen screen){
		render(x, y, -4,text, color, screen);
		
	}
	public void render(int x, int y, int spacing, String text, int color, Screen screen){

		for(int i = 0; i < text.length(); i++){
			int yoff = 0;
			char currentChar = text.charAt(i);
			if(currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j')yoff = 4;
			int index = chars.indexOf(currentChar);
			if(index == -1)continue;
			screen.renderTextCharacter(x + i * (16 + spacing - 4), y + yoff, characters[index], color , 1, true);
		}
		
	}

}
