package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity{//npc because we are going to push and move the rock

	public static final String npcName = "Big Rock";
	
	public NPC_BigRock(GamePanel gp) {
		
		super(gp);
		direction = "down";
		speed = 4; // rock fast
		name = npcName;
		solidArea = new Rectangle();
		solidArea.x = 2;
		solidArea.y = 6;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width= 44;
		solidArea.height = 40;
		
		dialogueSet = -1;//so the first dialogue is 0
		
		getNPCImage();
		setDialogue();
		
	}
	
	public void getNPCImage() {//copied and pasted from Player Class
	
		up1 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		up2 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		down1 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		down2 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		left1 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		left2 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		right1 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		right2 = setup("/npc/bigrock",gp.tileSize, gp.tileSize);
		
		}
	
	public void setDialogue() {
		
		dialogues[0][0] = "ROCK IS BIG";

	}
	//disabling update
	public void update() {
		
	}
	//this method below is to move a certain object places
	public void move(String d) {//d for direction
		this.direction = d;
		
		checkCollision();
		
		if(collisionOn == false) {
			switch(direction) {
			case "up": worldY-=speed;break;
			case "down": worldY+=speed;break;
			case "left": worldX-=speed;break;
			case "right": worldX+=speed;break;
			}
		}
		
		detectPlate();
	}
	
	//this will set the character's behavior and it will be like an AI 
	public void setAction() {//overriden since each npc has different behavior unlike update
	
	}
	
	public void speak() {
		
		
		facePlayer(); //when you speak to old man, he turns himself into player direction
		startDialogue(this,dialogueSet);
		
		dialogueSet++;
		//we can set conditions on each dialogue... if the player has low life, old man gives advice
		if(dialogues[dialogueSet][0] == null) {
			dialogueSet = 0;//if we reach a slot that has no text inside. go back to 0
			//dialogueSet--;
		}

		
	}
	
	public void detectPlate() {// this method will help to detect linked Entities
		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();
		
		//CREATE A PLATELIST 
		for(int i = 0; i<gp.iTile[1].length; i++) {
			if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null &&gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}
		
		//CREATE A ROCKLIST
		for(int i = 0; i<gp.npc[1].length; i++) {
			if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
				rockList.add(gp.npc[gp.currentMap][i]);
			}
		}
		
		int count = 0;
		
		//SCAN PLATELIST
		for(int i = 0; i<plateList.size(); i++) {
			//DISTANCE BETWEEN ROCK AND PLATE
			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			
			if(distance< 8) {//if distance is within 8 pixels, then we consider that the rock is on the plate
				if(linkedEntity == null) {//to make the sound effect play only when the rock is in the 
				linkedEntity = plateList.get(i);
			//	gp.playSE("Rock activated"); 	LATERR
				}
			}
			else {
				if(linkedEntity == plateList.get(i)) {
				linkedEntity = null;
				}
			}

		}
		
		//SCAN THE ROCK LIST
		for(int i = 0; i< rockList.size();i++) {
			//COUNT THE ROCKS ON THE PLATES
			if(rockList.get(i).linkedEntity != null) {
				count++;
			}
		}
		//IF ALL THE ROCKS ARE ON THE PLATE, THE IRON DOOR OPENS
		if(count == rockList.size()) {
			for(int i = 0; i< gp.obj[1].length;i++) {
				
				if(gp.obj[gp.currentMap][i] != null &&gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
					gp.obj[gp.currentMap][i] = null;
					//gp.playSE(""); IRON DOOR OPENS SOUND LATERR
				}
				
			}
		}
	}
}
