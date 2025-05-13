package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.NPC_OldMan;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	//SCREEN SETTINGS 
	
	public final int originalTileSize = 16; // 16x16 tile (gonna be default size for ch.. npc etc.)
	public final int scale = 3; // so characters are 16 bit but it will show as 48 in our screen
	public final int tileSize = originalTileSize * scale; // each tile 48 
	public  int maxScreenCol = 20;//26 to full screen
	public  int maxScreenRow = 14;// to make screen 4:3 // 16 to full screen
	public  int screenWidth = maxScreenCol * tileSize;//)+32; //width of screen 768px
	public  int screenHeight = maxScreenRow * tileSize;//)-40; //height of screen 576 px
	
	//WORLD SETTINGS
	public int maxWorldCol = 50;// (we can change based on map size)
	public int maxWorldRow = 50;
	public final int maxMap = 10;//we can create 10 different maps (we choose)
	public int currentMap = 0; //indicates current Map number
//	public final int worldWidth = tileSize * maxWorldCol;//we wont use both of these 
//	public final int worldHeight = tileSize * maxWorldRow; so I comment over them ig...
//	
//	//FOR FULL SCREEN (ignore these for now) Will be Done Later!
//	int screenWidth2 = screenWidth;
//	int screenHeight2 = screenWidth;
//	BufferedImage tempScreen;
//	Graphics2D g2; 
	public boolean fullScreenOn;
	
	
	//FPS
	int FPS = 60;
	
	public TileManager tileM = new TileManager(this);
	
	public KeyHandler keyH = new KeyHandler(this); //key input can be recognized when we add to cons..
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	Sound music = new Sound();	//sound for music
	Sound se = new Sound();     //sound for sound effects

	
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	
	public EventHandler eHandler = new EventHandler(this); 
	
	Config config = new Config(this);
	
	//ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	// the more objects you add, the slower the game will be.
	public Entity obj[][] = new Entity[maxMap][20]; //array of objects (used to be super obj)
	
	public Entity npc[][] = new Entity[maxMap][10]; // similar to objects, we will have many npc
	
	public Entity monster[][] = new Entity[maxMap][20];
	
	public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];//added maxMap
	
	public Entity projectile[][] = new Entity[maxMap][20];
	//public ArrayList<Entity> projectileList = new ArrayList<>(); commented and changed to normal array
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	public PathFinder pFinder = new PathFinder(this);
	
	EnvironmentManager eManager = new EnvironmentManager(this);
	
	SaveLoad sLoad = new SaveLoad(this);
	
	public EntityGenerator eGenerator = new EntityGenerator(this);
	
	public CutsceneManager cManager = new CutsceneManager(this);
	
	Map map = new Map(this);
	
	Thread gameThread;
//	//set player's default position (commented since we dont need it anymore
//	int playerX = 100; We put all these values in the Player class (constructor)
//	int playerY = 100;
//	int playerSpeed = 4;

	//GAME STATE
	public int gameState;
	public final int titleState = 0; //added a title screen!
	public final int playState = 1;//we are working with variables since me might change-
	public final int pauseState = 2;//later. There will be more states in the future
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;// to add transition effect (might cancel)
	public final int tradeState = 8;
	public final int sleepState = 9;
	public final int mapState = 10;
	public final int cutsceneState = 11;
	
	//OTHERS
	public boolean bossBattleOn = false;
	

	public int currentArea;
	public int nextArea; //to handle this so we dont have transitioning effect bugs
	public final int outside = 0;
	public final int indoor = 1;
	public final int dungeon = 2;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight)); //Preferred Size
		this.setBackground(Color.black); //unnecessary (maybe)
		this.setDoubleBuffered(true); //better rendering
		this.addKeyListener(keyH); //now this gamepanel can recognize the key input
		this.setFocusable(true);// so gamePanel can be "focused" to recieve input
	}
	
	public void setFullScreen(){//will fix stuff later
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		maxScreenCol = (int)(width/tileSize);
		maxScreenRow = (int)(height/tileSize);
		
		screenWidth = maxScreenCol* tileSize; //(int) width;
		screenHeight = maxScreenRow* tileSize;//(int) height;
		//did this method myself!!! 
		player.screenX = ((maxScreenCol * this.tileSize)/2);
		player.screenY = ((maxScreenRow * this.tileSize)/2);
		
	}
	//done but we will use this method in the future to set up other things
	public void setUpGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		eManager.setup();
		
		playMusic(0);// we pass 0 since the array is sound[0] for music
		stopMusic();// stopped the music since its annoying
		gameState = titleState; // changed default gameState (from playState)
		
		currentArea = outside;// for lighting management
		
//		if(fullScreenOn == true) {
//			setFullScreen();//in future
//		}
	//	tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
	//	g2 = (Graphics2D)tempScreen.getGraphics();for full screen lets do it later! I did it without any help
		
	}
	
	public void resetGame(boolean restart) { // replaced reset restart methods
		stopMusic();
		currentArea = outside;
		removeTempEntity();
		bossBattleOn = false;
		player.setDefaultPositions();
		player.restoreStatus();
		player.resetCounter();
		aSetter.setNPC();
		aSetter.setMonster();
		
		if(restart == true) {
		player.setDefaultValues();
		aSetter.setInteractiveTile();
		aSetter.setObject();
		eManager.lighting.resetDay();
		}
	}
	
	
	public void startGameThread() {//a thread is to run with time (FPS logic)
		gameThread = new Thread(this); //"this" means this class
		gameThread.start(); //auto calls run method
	}

//!this is the sleep method one of the methods, I commented it for since delta accumulator
//	@Override //we will create a game loop, which is the core of our game using run method.
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS; //1B nanosec = 1 sec. therefore we draw 
//		double nextDrawTime = System.nanoTime() + drawInterval; //when the next draw occur
//		
//	//as long as the game thread exists we will repeat the same process in the while loop
//		while(gameThread!=null) {//! so as long as we have the app opened it will work.
//			//STEPS
//			//1- We will update information such as character positions
//			update();
//			//2- Drawing the screen with the updated information
//			//   we basically update the character's coordinates based on which key 
//			//   the character is pressing, ex: if down key 3x update the y axis by 3
//			repaint();// that is how we call the method Paint below.
//			
//			//sleep method
//			try {
//				
//				double remainingTime = nextDrawTime - System.nanoTime(); 
//				remainingTime = remainingTime/1000000; //convert to milli for sleep method
//				
//				if(remainingTime<0) {// this wont happen in small games but just in case
//					remainingTime = 0;
//				}
//				
//				//we let the thread sleep during the remaining time for another frame
//				Thread.sleep((long)remainingTime);
//				
//				//when sleep time is over and thread awakens we will add new draw interval
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				
//				e.printStackTrace();
//				
//			}
//		}
//		}
	//delta method
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	//to update the information, we will use the 2 methods below in our while loop.
	public void update() {
		 //java up left corner X = 0, Y = 0. As X++, X go right, as Y++ Y go down.
		//added this if gameState == 1 game updates
		if(gameState == playState) {
			
		
		
		//!remember we moved everything to Player Class so we just invoke the method
			//PLAYER
			player.update();
			//NPC
			for(int i = 0 ;i <npc[1].length; i++) {
				
				if(npc[currentMap][i] != null) {//we added currentMap in  to recognize which map
					npc[currentMap][i].update();//update npc is responsible for all actions taken by it
				}
				
			}
			
			for(int i = 0 ;i <monster[1].length; i++) {
				
				if(monster[currentMap][i] != null) {
					if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
					monster[currentMap][i].update();//if monster is alive we update
					}
					if(monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();//we check monster drop before it dies
						monster[currentMap][i] = null;
						}
				}
				
			}
			
			for(int i = 0 ;i < projectile[1].length; i++) {
				
				if(projectile[currentMap][i] != null) {
					if(projectile[currentMap][i].alive == true) {
						projectile[currentMap][i].update();
					}
					if(projectile[currentMap][i].alive == false) {
						projectile[currentMap][i] = null;//if projectile not alive we'll remove it
						}
				}
				
			}
			//same as projectiles, since projectiles also disappear when they die
			for(int i = 0 ;i < particleList.size(); i++) {
				
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if(particleList.get(i).alive == false) {
						particleList.remove(i);//if particle not alive we'll remove it
						}
				}
				
				
				
			}
			
			//updating interactive tiles (drawing them on screen and their functions
			for(int i = 0; i < iTile[1].length; i++) {
			if(iTile[currentMap][i] != null) {
				iTile[currentMap][i].update();
				}
			}
			
			eManager.update();
			
			
			//only to clean stuff make it more organized
			
		}
		if(gameState == pauseState) {
			//nothing
		}
		
		if(fullScreenOn == true) {
			setFullScreen();//in future
		}
		
		}
			//paintComponent is built in and we can use it to draw (change tiles)
	public void paintComponent(Graphics g) { //Graphics g is like our pencil / pen
		super.paintComponent(g); // we type this whenever we want to use JPanel method. 
		
		Graphics2D g2 = (Graphics2D)g; // we want to use 2D because of its additional funxs
		
		//DEBUG start (see how fast is our code)
		long drawStart = 0;
		if(keyH.showDebugText == true) {
		drawStart = System.nanoTime();
		}
	
		//TITLE STATE
		if(gameState == titleState) {
			
			ui.draw(g2);
			
		}
		
		//MAP SCREEN
		else if(gameState == mapState) {
			map.drawFullMapScreen(g2);
		}
		
		//OTHER
		
		else {
		
		//TILE
		tileM.draw(g2); //we make sure tiles are drawn before player to layer
		
		//interactive tile drawing
		for(int i = 0; i < iTile[1].length ; i++) {
			if(iTile[currentMap][i] != null) {
				iTile[currentMap][i].draw(g2);
			}
		}
		
		//deleted and changed to arrayListcheck notes
		
		entityList.add(player);
		//ADDING ENTITIES TO THE LIST
		for(int i = 0; i < npc[1].length ; i++) {
			
			if(npc[currentMap][i] != null) {
				
				entityList.add(npc[currentMap][i]);
				
			}
			
		}
		
		for(int i = 0 ; i < obj[1].length ; i++) {
			
			if(obj[currentMap][i] != null) {
				
				entityList.add(obj[currentMap][i]);
				
			}
			
		}
		
		for(int i = 0 ; i < monster[1].length ; i++) {
			
			if(monster[currentMap][i] != null) {
				
				entityList.add(monster[currentMap][i]);
				
			}
			
		}
		
		for(int i = 0 ; i < projectile[1].length ; i++) {
			
			if(projectile[currentMap][i] != null) {
				
				entityList.add(projectile[currentMap][i]);
				
			}	
		}
		//also same as projectileList
		for(int i = 0 ; i < particleList.size() ; i++) {
			
			if(particleList.get(i) != null) {
				
				entityList.add(particleList.get(i));
				
			}	
		}
		
		//SORT
		entityList.sort(Comparator.comparingInt(entity -> entity.worldY)); // to sort 
		
		//DRAW ENTITIES
		for(int i = 0; i<entityList.size() ; i++) {
			
			entityList.get(i).draw(g2);
			
		}
		//We empty entityList here to not make the loop larger and larger every frame
		entityList.clear();
		
		//ENVIRONMENT 
		eManager.draw(g2);
		
		//MINIMAP
		
		map.drawMiniMap(g2);
		
		
		
		//we ui this after player since ui shouldnt be hidden by tiles, player. (layer 1)
		ui.draw(g2);
		
		// CUTSCENE
		cManager.draw(g2);

		}
		//DEBUG end 
		if(keyH.showDebugText == true) {
		long drawEnd;
		drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
		
		g2.setFont(new Font("Arial",Font.PLAIN,20));;
		g2.setColor(Color.white);
		
		int x = 10;
		int y = 400;
		int lineHeight = 20;
		g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
		g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
		g2.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize, x, y);y += lineHeight;
		g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize, x, y);y += lineHeight;	
		g2.drawString("Draw Time: " + passed, x, y);y += lineHeight;
		g2.drawString("FPS: "+ FPS, x, y);y += lineHeight;
		g2.drawString("Super Mode is  "+ keyH.superModeOn, x, y);y += lineHeight;
		System.out.println("Draw Time: " + passed);
		
		}
		
		g2.dispose();// saving memory
	}
	
	
	public void playMusic(int i) {
		//first we set the file, then we play, then we loop it.
		music.setFile(i);
		music.play();
		music.loop();
		
	}
	
	public void stopMusic() {
		
		music.stop();
		
	}
	
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
		
	}
		
	public void changeArea() {// to handle transition effect properly
		
		if(nextArea != currentArea) {
			stopMusic();
			
			if(nextArea == outside) {
				//playMusic(0);make new music for outside
			}
			if(nextArea == outside) { 
				//playMusic(lala);make new music for indoor
			}
			if(nextArea == outside) {
				//playMusic(lalala);make new music for dungeon
			}
			
			aSetter.setNPC();//if we dont want all npcs to reset position, we handle
			// a different method for rocks and stuff
		}
		
		currentArea = nextArea;
		//aSetter.setMonster();later to make monsters alive again, not cool though 
		
	}
	
	public void removeTempEntity() {
		
		for(int mapNum = 0; mapNum < maxMap ; mapNum++) {
			for(int i = 0; i <obj[1].length;i++) {
				if(obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
					obj[mapNum][i] = null;//if theres an object and temp is true, delete obj
				}
			}
		}
		
	}
	
}
