package tcd.game.main;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import tcd.game.gamestate.GameStateManager;
import tcd.game.manager.Mouse;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 400;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 3;
	
	public static int FULL_WIDTH = WIDTH * SCALE;
	public static int FULL_HEIGHT = HEIGHT * SCALE;
	
	public static final String TITLE = "League of the Ancients";
	
	public static boolean isRunning;	
	
	public Graphics2D g;
	
	private Screen screen;
	private Thread thread;
	private JFrame frame;
	public static Keyboard key;
	public static Mouse mouse;
	private Assets assets;
	public static GameFont font;
	private GameStateManager gsm;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(){
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0,0), "cursor");
		
		screen = new Screen(WIDTH, HEIGHT);
		frame = new JFrame();
		key = new Keyboard();
		mouse = new Mouse();
		assets = new Assets();
		assets.init();
		font = new GameFont();
		gsm = new GameStateManager();
		gsm.init();
		
		setCursor(cursor);
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

	}
	
	public synchronized void start(){
		isRunning = true;
		thread = new Thread(this, "DISPLAY");
		thread.start();
		
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				update();
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frame.setTitle(TITLE + " | TPS: " + ticks + ", FPS: " + frames);
				frames = 0;
				ticks = 0;
			}
			
		}
	}
	
	public void update(){
		key.update();
		gsm.update();
	}
	
	public void render(){
		BufferStrategy bs =  getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		g = (Graphics2D) bs.getDrawGraphics();
		
		gsm.render(screen);
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		g.drawImage(image,0, 0,getWidth(), getHeight(),null); // Draw Screen
		gsm.renderG(g);
		mouse.render(g);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game game = new Game();
		
		game.frame.setResizable(false);
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();

	}

}
