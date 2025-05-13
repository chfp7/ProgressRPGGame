package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][]; // was 2D Array to store all the txt values to create the map, now 3D Array, first dimension willstore the map index the rest are col and row
	boolean drawPath = false;// to draw the path to debug
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();

	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		//READ TILE DATA FILE TILE MANAGER (NOT OBLIGATORY)
		InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		//GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
		String line;
		
		try {
		while((line = br.readLine()) != null) {
			fileNames.add(line);
			collisionStatus.add(br.readLine());
		}
		br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		tile = new Tile[fileNames.size()]; // if we need more we just increase 
		//to use world map we change from maxScreenCol to maxWorldCol etc..
		getTileImage();
		
		//GET THE MAXWORLDCOL AND MAXWORLD ROW
		is = getClass().getResourceAsStream("/maps/worldmap.txt");
		br = new BufferedReader(new InputStreamReader(is));
		try {
			
			String line2 = br.readLine();
			String maxTile[] = line2.split(" ");
			
			gp.maxWorldCol = maxTile.length;
			gp.maxWorldRow = maxTile.length;
			
			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];//3D
			
			br.close();
			
		}catch(IOException e){
			System.out.println("EXCEPTION!");
		}
		
		
		loadMap("/maps/worldmap.txt",0); // load any map you want from txt in your path
		loadMap("/maps/indoor01.txt",1);
		loadMap("/maps/dungeon01.txt",2);
		loadMap("/maps/dungeon02.txt",3);
		
	}
	
	public void getTileImage() {
		
		
		
		for(int i = 0; i < fileNames.size(); i++) {
			String fileName;
			boolean collision;
			
			//GET A FILE NAME
			fileName = fileNames.get(i);
			
			//GET A COLLISION STATUS
			if(collisionStatus.get(i).equals("true")) {
				collision = true;
			}
			else {
				collision = false;
			}
			setup(i,fileName,collision);
		}
		
		
		
		
		
		
//	//	try {
//			//this is the new method that takes less time to operate. 
//			//we are instantiating the scale method using a utility tool prior to the image
//			//processing
//			setup(0,"grass00", false);//0 is  index pic, "=" is the path, bool is collision
//			setup(1,"grass00", false);
//			setup(2,"grass00", false);//index 0 to 9 are placeholders
//			setup(3,"grass00", false);
//			setup(4,"grass00", false);//they are placeholders to not disturb how map txt
//			setup(5,"grass00", false);//looks
//			setup(6,"grass00", false);
//			setup(7,"grass00", false);
//			setup(8,"grass00", false);
//			setup(9,"grass00", false);// we will start from index 10
//			//PLACEHOLDERSEPARATION
//			setup(10,"grass00", false);
//			setup(11,"grass01", false);
//			setup(12,"water00", true);
//			setup(13,"water01", true);
//			setup(14,"water02", true);
//			setup(15,"water03", true);
//			setup(16,"water04", true);
//			setup(17,"water05", true);
//			setup(18,"water06", true);
//			setup(19,"water07", true);
//			setup(20,"water08", true);
//			setup(21,"water09", true);
//			setup(22,"water10", true);
//			setup(23,"water11", true);
//			setup(24,"water12", true);
//			setup(25,"water13", true);
//			setup(26,"road00", false);
//			setup(27,"road01", false);
//			setup(28,"road02", false);
//			setup(29,"road03", false);
//			setup(30,"road04", false);
//			setup(31,"road05", false);
//			setup(32,"road06", false);
//			setup(33,"road07", false);
//			setup(34,"road08", false);
//			setup(35,"road09", false);
//			setup(36,"road10", false);
//			setup(37,"road11", false);
//			setup(38,"road12", false);
//			setup(39,"earth", false);
//			setup(40,"wall", true);
//			setup(41,"tree", true);
//			setup(42,"hut", false);
//			setup(43,"floor01", false);
//			setup(44,"table01", true);


		

			
			//This is the code before tile building optimization
//			tile[0] = new Tile();
//			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
//			//we dont have to add collision false to this b/c its added by default.
//			
//			tile[1] = new Tile();
//			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
//			tile[1].collision = true;// true means there is collision. so its solid
//			
//			tile[2] = new Tile();// we started here but our map should be bigger so we add.
//			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
//			tile[2].collision = true; // water we cant pass through it yet (cant swim)
//			
//			tile[3] = new Tile();
//			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
//			
//			tile[4] = new Tile();
//			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
//			tile[4].collision = true;
//			
//			tile[5] = new Tile();
//			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
//			
//		}catch(IOException e){
//			e.printStackTrace();
//		}
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath));//usually  + ".png" but after tile editor no more
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath, int map) {
		//what this method does is it scans the txt file line by line and adds all the 
		//tiles that you want based on their number that you assigned it, and applies it
		//using the while loop method we used previously. That I will comment out.
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			//also here we change maxScreenRow and Col to maxWorldCol etc...
			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				//the buffreader will read each line by line (before we ++row)
				String line = br.readLine();
				
				while(col<gp.maxWorldCol) {
					// we are going to get number by number (without the spaces) in array
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);// to convert string to int
					
					mapTileNum[map][col][row] = num;
					col++;
					
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//we can do this but it is not efficient so well do a while loop to automate things
//		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);
		
		//int col = 0; commented out to use new worldCol. to recognize them
		//int row = 0;
		//int x = 0; commented these since we are not using them before (new Mechanic)
		//int y = 0;
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];//gp.crnt map
			//these are all new  x y etc..
			int worldX = worldCol * gp.tileSize; // with all these changes camera works
			int worldY = worldRow * gp.tileSize;
			//we subtract world x... I didnt get this but return to camera.
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//We do this to render more efficiently (Only use with large maps)
			//what the if statement is doing is its creating a boundary, to only show what's on the screen
			//this way we are saving alot of memory and we are programming faster
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
			&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
				//in draw instead of screenX, screenY just x and y as before 
			g2.drawImage(tile[tileNum].image, screenX, screenY, null); // in we deleted the scaling (gp.tileSize) to optimize
			//x += gp.tileSize; deleted for implementing the camera
			}
			
			worldCol++;
			if(worldCol==gp.maxWorldCol) {
				worldCol = 0;
				//x = 0; also this for camera
				worldRow++;
				//y += gp.tileSize; camera too 
			}
				}
		
		if(drawPath == true) {
				g2.setColor(Color.red);
				for(int i = 0; i< gp.pFinder.pathList.size();i++) {
					int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize; // with all these changes camera works
					int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
					int screenX = worldX - gp.player.worldX + gp.player.screenX;
					int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
					g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
				}
			}

		
	}

	
}
