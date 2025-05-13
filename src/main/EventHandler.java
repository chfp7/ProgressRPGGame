package main;

import data.Progress;
import entity.Entity;

public class EventHandler{
	
	GamePanel gp;     //we also added a dimension(map transition)
	EventRect eventRect[][][];//changed from Rectangle to EventRect which is same but better
	Entity eventMaster; //instead of extending entity to access methods
	
	int prevEventX, prevEventY;
	boolean canTouchEvent = false;
	int tempMap, tempCol, tempRow;//trans effect
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		
		eventMaster = new Entity(gp);
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
		eventRect[map][col][row] = new EventRect();//this rectangle is tiny at the center of the tile
		eventRect[map][col][row].x = 23;// at the center of the tile 
		eventRect[map][col][row].y = 23;
		eventRect[map][col][row].width = 2;//2 pix width and height
		eventRect[map][col][row].height = 2;
		eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
		eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
		
		col++;
		if(col==gp.maxWorldCol) {
			col = 0;
			row++;
			
			if(row == gp.maxWorldRow) {
				row = 0;
				map++;//so we create events for all maps
			}
		}
		
		}
		
		setDialogue();
	}
	
	public void setDialogue() {
		
		eventMaster.dialogues[0][0] = "you have fallen into a pit!";
		eventMaster.dialogues[1][0] = "*you drink from the pond's water* \n This water is AMAZING WATER\n YOU HAVE SAVED PROGRESS";
		eventMaster.dialogues[2][0] = "INSTANT TRANSMISSION!";

	}
	
	public void checkEvent() {
		//check if player is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX-prevEventX);
		int yDistance = Math.abs(gp.player.worldY-prevEventY);
		int distance = Math.max(xDistance, yDistance);// we want to take greater distance
		if(distance>gp.tileSize) { // this means player is more than a tile away
			canTouchEvent = true;
		}

		if(canTouchEvent == true) {
		//if in certain coordinates and direction there is collision with event. it occurs.
		if(hit(0,27,16,"right") == true) {//0 since first map, col, row and direction
			//event happens, maybe damage, maybe win health
			damagePit(gp.dialogueState);//this will damage the player, we can see that we organize events
		}
		else if(hit(0,23,12,"up") == true) { healingPool(gp.dialogueState);}
		else if(hit(0,26,12,"right") == true) {teleport(gp.dialogueState);}
		else if(hit(0,23,16,"any") == true) {damagePit(gp.dialogueState);}//test
		else if(hit(0,23,16,"any") == true) {damagePit(gp.dialogueState);}//test
		else if(hit(0,10,39,"any") == true) {teleports(1,12,13,gp.indoor);}
		else if(hit(1,12,13,"any") == true) {teleports(0,10,39,gp.outside);}
		else if(hit(1,12,9,"up") == true) {speak(gp.npc[1][0]);}
		else if(hit(0,12,9,"any") == true) {teleports(2,9,41,gp.dungeon);}//this first to dungeon
		else if(hit(2,9,41,"any") == true) {teleports(0,12,9,gp.outside);}//now this from dun to home
		else if(hit(2,8,7,"any") == true) {teleports(3,26,41,gp.dungeon);}//dun1 to dun2
		else if(hit(3,26,41,"any") == true) {teleports(2,8,7,gp.dungeon);}//dun2 to dun1
		else if(hit(3,25,27,"any") == true) {skeletonLord();}//BOSS

	}}
	
	public boolean hit(int map, int col, int row, String reqDirection) {//map so each map has unique events
		
		boolean hit = false;
		
		if(map == gp.currentMap) {
		//getting player's solidArea position (x and y)
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		//Getting events' area position
		eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].y;
		eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
		//check collision
		if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
		//see if the direction is equal to the required direction of the event (maybe any)
			if(gp.player.direction.contentEquals(reqDirection) ||
					reqDirection.contentEquals("any") ) {
				
				hit = true;
				
				prevEventX = gp.player.worldX;
				prevEventY = gp.player.worldY;
				
			}
		}
		
		//RESETTING VALUES
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
		eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		return hit;
		
	}
	
	//this is when the player falls into a pit
	public void damagePit(int gameState) {// we check which state we are in. 
		
		gp.gameState = gameState;
		
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
	//	eventRect[col][row].eventDone = true; // to mark that its one time only
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) { 
		
		if(gp.keyH.enterPressed == true) {
		gp.gameState = gameState;
		gp.player.attackCanceled = true;
		//gp.playSE(watersound);do it in the future
		eventMaster.startDialogue(eventMaster, 1);
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		gp.sLoad.save(); //saving when we drink from healing pool
		}
	}
	
	public void teleport(int gameState) {
		//gp.playSE do it in future also
		gp.gameState = gameState;
		eventMaster.startDialogue(eventMaster, 2);
		gp.player.worldX = gp.tileSize * 37;
		gp.player.worldY = gp.tileSize * 10;
		
	}
	
	public void teleports(int map,int col, int row,int area) {
		
		gp.gameState = gp.transitionState;//new method for transition 
		gp.nextArea = area;
		this.tempMap = map;
		this.tempCol = col;
		this.tempRow = row;
		
//		gp.currentMap = map;
//		gp.player.worldX = gp.tileSize * col;
//		gp.player.worldY = gp.tileSize * row;	temporarily cancelling for another method
//		prevEventX = gp.player.worldX;	
//		prevEventY = gp.player.worldY;
		canTouchEvent = false;
		//gp.playSE("change rooms");

	}
	
	public void speak(Entity entity) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
		
	}
	
	public void skeletonLord() {
		
		if(gp.bossBattleOn == false && Progress.skeletonLordDefeated == false) {
			gp.gameState = gp.cutsceneState;
			gp.cManager.sceneNum = gp.cManager.skeletonLord;
		}
		
	}
	

}
