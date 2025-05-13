package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		
		super(gp);
		direction = "down";
		speed = 1; // since old man is slow
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width= 30;
		solidArea.height = 30;
		
		dialogueSet = -1;//so the first dialogue is 0
		
		getNPCImage();
		setDialogue();
		
	}
	
	public void getNPCImage() {//copied and pasted from Player Class
	
		up1 = setup("/npc/oldman_up_1",gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2",gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1",gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2",gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1",gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2",gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1",gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2",gp.tileSize, gp.tileSize);
		
		}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Hello, lad.";
		dialogues[0][1] = "So you've come to this island to find the treasure.";
		dialogues[0][2] = "I used to be a great rizzler, but now...\n I suck toes for a living";
		dialogues[0][3] = "Good luck on your journey BOY";
		dialogues[0][4] = "Much love from Mr. Rizz";
		
		dialogues[1][0] = "If you feel hurt... go drink some of the lake's water";
		dialogues[1][1] = "It might heal you, if you protect it";
		dialogues[1][2] = "Try to go easy on yourself...";
		
		dialogues[2][1] = "I wonder how could I open that door?";
		dialogues[2][2] = "How did I survive out here?";
		dialogues[2][3] = "I dont even remember but the monsters like me\n for some reason...";




	}
	
	//this will set the character's behavior and it will be like an AI 
	public void setAction() {//overriden since each npc has different behavior unlike update
		
		if(onPath == true) {
			
//			int goalCol = 12;
//			int goalRow = 9;
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
		searchPath(goalCol, goalRow);
			
		}
		else {
		actionLockCounter++;
		//every 120 frames the direction will be decided (2 seconds)
		if(actionLockCounter == 120) {
		Random random = new Random();
		int i = random.nextInt(100)+1;// we get a random number from 1  to 100
		
		//this is the simplest AI with 25% move direction only
		if(i<=25) {
			direction = "up";
		}
		if(i>25 && 50>=i) {
			direction = "down";
		}
		if(i>50 && 75>=i) {
			direction = "left";
		}
		if(i> 75 && i<=100) {
			direction = "right";
		}
		
		actionLockCounter = 0;
		
		}
		}
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
//		if(gp.player.life<gp.player.maxLife/3) { we need to comment out dialogueSet++
//			dialogueSet = 1;
//		}
		
		//onPath = true;
		
	}

	

}
