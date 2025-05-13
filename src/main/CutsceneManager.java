package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.PlayerFake;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;//we are going to divide the cutScene stages using this
	int counter = 0;
	float alpha = 0f;
	int y;
	String endCredit;
	
	//Scene Number
	public final int NA = 0;
	public final int skeletonLord = 1;
	public final int ending = 2;
	
	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
		
		endCredit = "Program/Music/Art \n"
				+ "Ferylo"
				+ "\n\n\n\n\n\n\n\n"
				+ "Special Thanks to \n"
				+ "Someone \n"
				+ "Someone \n"
				+ "Someone \n"
				+ "And you!"
				+ "Thanks for playing!";
				
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch(sceneNum) {
		case skeletonLord: sceneSkeletonLord();break;
		case ending : sceneEnding();break;
		
		}
	}

	public void sceneSkeletonLord() {
		
		if(scenePhase == 0) {
			
			gp.bossBattleOn = true;
			
			//Shut the iron door
			for(int i = 0; i <gp.obj[1].length; i++) {//can use this to place objects
				if(gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
					gp.obj[gp.currentMap][i].temp = true; //so we can delete when boss dies
					//gp.playSE(21);when we implement sounds
					break;
				}
			}
			for(int i = 0; i<gp.npc[1].length;i++) {
				if(gp.npc[gp.currentMap][i] == null) {
					gp.npc[gp.currentMap][i] = new PlayerFake(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
			
			gp.player.drawing = false;
			
			scenePhase ++;
		}
		if(scenePhase == 1) {
			gp.player.worldY -= 2;//dynamically moving the camera
			
			if(gp.player.worldY < gp.tileSize *16) {
				scenePhase++;//so it stops at row 16
			}
		}
		
		if(scenePhase == 2) {
			//Search the boss
			for(int i = 0; i<gp.monster[1].length ; i++) {
				if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {
					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc =	gp.monster[gp.currentMap][i];
					scenePhase++;
					break;
				}
			}
		}
		if(scenePhase == 3) {
			
			//The Boss Speaks
//			gp.ui.npc.startDialogue(gp.ui.npc, 0); MAYBE GETTING ANOTHER GLITCH 
//			if(gp.keyH.enterPressed == true) {
//				gp.ui.npc.dialogueIndex++;
//			}
			gp.ui.drawDialogueScreen();
		}
		if(scenePhase == 4) {
			//RETURNING TO THE PLAYER
			//search the dummy
			
			for(int i = 0; i<gp.npc[1].length ; i++) {
				if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name == PlayerFake.npcName) {
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					gp.npc[gp.currentMap][i] = null;
					System.out.println("FOUND");
					break;
				}
			}
			//draw player again
			gp.player.drawing = true;
			
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;
			
			//change music
			gp.stopMusic();
			//gp.playMusic(BOSS BATTLE); later
			
		}
		
	}
	
	public void sceneEnding() {
		
		if(scenePhase == 0) {
			
			gp.stopMusic();
			gp.ui.npc = new OBJ_BlueHeart(gp);
			scenePhase++;
			
		}
		if(scenePhase == 1) {
			//Display dialogues
			gp.ui.drawDialogueScreen();
		}
		if(scenePhase == 2) {
			//play fanfare (dk)
			//gp.playSE("youpii"); LATER
			scenePhase++;
			
		}
		if(scenePhase == 3) {
			//wait until SE ends
			if(counterReached(300)) {//5 seconds
				scenePhase++;
			}
		}
		if(scenePhase == 4) {
			//the screen gets darker
			alpha += 0.005f;
			if(alpha> 1f) {
				alpha = 1f;
			}
			drawBlackBackground(alpha);
			
			if(alpha == 1f) {
				alpha = 0;
				scenePhase++;
			}
		}
		if(scenePhase == 5) {
			drawBlackBackground(1f);
			alpha += 0.005f;
			if(alpha> 1f) {
				alpha = 1f;
			}
			
			String text = "After the fierce battle with the Skeleton Lord \n" + 
			"The Boy found his true self, time to get on the adventure!";
			
			drawString(alpha,38f,200,text,70);
			
			if(counterReached(600) == true) {
				//gp.playMusic(ending);LATER ENDING MUSIC
				scenePhase++;
			}
		}
		
		if(scenePhase == 6) {
			drawBlackBackground(1f);
			
			drawString(1f, 120f, gp.screenHeight/2, "PROGRESS",40);
			
			if(counterReached(360)) {
				scenePhase++;
			}
		}
		if(scenePhase == 7) {
			drawBlackBackground(1f);
			
			y = gp.screenHeight/2;
			
			drawString(1f, 38f, y,endCredit,40);
			
			if(counterReached(360)) {
				scenePhase++;
			}

		}
		if(scenePhase == 8) {
			drawBlackBackground(1f);
			//scrolling
			y--;
			drawString(1f,38f,y,endCredit,40);
		}
		
	}
	
	public boolean counterReached(int target) {
		boolean counterReached = false;
		counter++;
		if(counter>target) {
			counterReached = true;
			counter = 0;
		}
		
		return counterReached;
	}
	
	public void drawBlackBackground(float alpha) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
	}
	
	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		
		for(String line: text.split("\n")) {
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y+= lineHeight;
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

	}
	
}
