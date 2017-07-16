package tcd.game.gamestate;

import java.awt.Graphics2D;

import tcd.game.main.Screen;

public class QuitState extends GameState {

		public QuitState(GameStateManager gsm) {
			super(gsm);
		}

		public void init() {
			
		}

		public void update() {
			System.exit(1);
		}

		public void render(Screen screen) {	
		}

		public void renderG(Graphics2D g) {
			
		}

	}