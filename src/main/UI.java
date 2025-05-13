package main;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;

public class UI {//this class will handle all on screen UI (user interface)
	
	GamePanel gp;
	Graphics2D g2;
	public Font maruMonica, purisaB; //this is the good practice
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0; we wont use these commented anymore
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0 ; // 0 is the first screen, 1 is second screen
	public int playerSlotCol = 0;//this will determine cursor's current position
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int subState = 0;//to separate states in options State
	int counter = 0;
	public Entity npc;
	public int charIndex = 0;
	String combinedText = "";
	
	//deleted some stuff from Treasure game
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		try {				
				InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");

				maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
				
				is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
				
				purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

		}
		
		catch (IOException e) {
			
				e.printStackTrace();
				
			}
		catch(FontFormatException e) {
			
			e.printStackTrace();
			
		}
		//CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);//instantiating images (heart and mana)
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity mana = new OBJ_ManaCrystal(gp);
		crystal_full = mana.image;
		crystal_blank = mana.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
		
		}
		
	//this will give us notifications and will be called in the player- object switch.
	public void addMessage(String text) {//renamed (was showMessage)
		
//		message = text; commented in (we will use a new method)
//		messageOn = true;
		
		message.add(text);
		messageCounter.add(0);
		
	}
	
	//we will draw text and stuff on screen using this method
	public void draw(Graphics2D g2) {
	
		this.g2 = g2;
	//	g2.setFont(purisaB);if we want purisaB we uncomment this
		g2.setFont(maruMonica);
		//use rendering below for purisaB, it is to remove the text jagginess
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			
			drawTitleScreen();
			
		}
		
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			
			drawPlayerLife();
			drawPlayerMana();
			drawMonsterLife();
			drawMessage();
			
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			
			drawPausedScreen();//did a method for this so we dont make this too long
			
		}
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			
			drawDialogueScreen();
			
		}
		//CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			
			drawCharacterScreen();
			drawInventory(gp.player,true);
			
		}
		//OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		//GAME OVER STATE (DEATH SCREEN)
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		if(gp.gameState == gp.sleepState) {
			
			drawSleepScreen();
			
		}
		
	}
	
	private void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		int iconSize = 32; //was 48 before(making game better)
		if(gp.player.maxLife/2<=3) { //changed this in library 
			iconSize = 48;
		}
		//DRAW MAX LIFE
		while(i<(gp.player.maxLife/2)) {
			g2.drawImage(heart_blank, x, y,iconSize,iconSize, null);
			i++;
			x += iconSize;			
			if(i == 8) {//to jump in line
				x = gp.tileSize/2;
				y += iconSize;
			}
		}
		
		//resetting the values
		x = gp.tileSize/2;
		y = gp.tileSize/2;
	    i = 0;
	    
	    //DRAW CURRENT LIFE
	    while(i<(gp.player.life)) {
	    	g2.drawImage(heart_half, x, y,iconSize,iconSize, null);
	    	i++;
	    	if(i<gp.player.life) {
	    		g2.drawImage(heart_full, x, y,iconSize,iconSize, null);
	    	}
	    	i++;
	    	x += iconSize;
	    	
	    	if(i == 16) {//to jump in line
				x = gp.tileSize/2;
				y+= iconSize;
			}
	    }
		
	}
	
	public void drawPlayerMana() {
		
		int x = (gp.tileSize/2)-5;
		int y = (int)(gp.tileSize*1.5);
		int i = 0;
		int iconSize = 32; //was 48 before (making game better)
		if(gp.player.maxMana <= 6) {
			iconSize = 48;
		}
		//DRAW MAX MANA
		while(i<(gp.player.maxMana)) {
			g2.drawImage(crystal_blank, x, y,iconSize,iconSize, null);
			i++;
			x += iconSize - 10;//35 maybe
		}
		
		//resetting the values
		x = (gp.tileSize/2)-5;
		y = (int)(gp.tileSize*1.5);
	    i = 0;
	    
	    //DRAW CURRENT LIFE
	    while(i<(gp.player.mana)) {
	    	g2.drawImage(crystal_full, x, y,iconSize,iconSize, null);
	    	i++;
	    	x += iconSize-10;
	    }
	}
	
	public void drawMonsterLife() {
		
		for(int i = 0; i< gp.monster[1].length; i++) {
			
			Entity monster = gp.monster[gp.currentMap][i];// and replace gp.monster[gp.currentMap][i] with monster
			
			if(monster != null && monster.inCamera() == true ) {
				//HEALTHBAR (monster)
		if(monster.hpBarOn == true && monster.boss == false) {//meaning entity is a monster
			//to know how much is one tiny rectangle (one life space in a bar)
			double oneScale = (double)gp.tileSize/monster.maxLife;
			double hpBarValue = oneScale*monster.life;//onescale x life (life decrease, so does the width)
					
			g2.setColor(new Color(35,35,35));
			g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.tileSize+2, 12);
			
			g2.setColor(new Color(255,0,30));
			g2.fillRect(monster.getScreenX(), monster.getScreenY()-15, (int)hpBarValue, 10);
			monster.hpBarCounter++;
			if(monster.hpBarCounter>180) {
				monster.hpBarCounter = 0;
				monster.hpBarOn = false;
			}
		}
		else if(monster.boss == true) {
			double oneScale = (double)gp.tileSize*8/monster.maxLife;
			double hpBarValue = oneScale*monster.life;//onescale x life (life decrease, so does the width)
					
			int x = gp.screenWidth/2 - gp.tileSize*4;
			int y = gp.tileSize *10;
			
			g2.setColor(new Color(35,35,35));
			g2.fillRect(x-1, y+100, gp.tileSize*8+2, 22);
			
			g2.setColor(new Color(255,0,30));
			g2.fillRect(x, y+101, (int)hpBarValue, 20);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
			g2.setColor(Color.white);
			g2.drawString(monster.name, x + 4, y + 30);
		}
			}
		}
		
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for(int i = 0; i<message.size(); i++) {//displaying text 1 by 1
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				//each message has its own counter, we are passing the counter to each msg
				int counter = messageCounter.get(i) + 1;//and we are doing messageCounter++ (incrementing it)
				messageCounter.set(i, counter);// set the counter to the array
				messageY += 50;
				if(messageCounter.get(i) > 180){
					message.remove(i);
					messageCounter.remove(i);
				}
			}
			
		}
		
	}
	//TITLE STATE
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
		//TO DRAW BACKGROUND COLOR
		//g2.setColor(new Color(144,211,122));
		g2.setColor(new Color(0,0,0)); //I want black background so this is good
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,106F));//WAS 96
		String text = "PROGRESS!";
		int x;
		int y;
		x = getXforCenteredText(text);
		y = gp.tileSize * 3;
		//TEXT SHADOW 
		g2.setColor(Color.gray);//this is how we do text shadow
		g2.drawString(text, x+5, y+5);
		//MAIN TEXT 
		g2.setColor(Color.white);
		g2.drawString(text,x,y);
		
		//MAIN CHARACTER IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2; // to center it well
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize *4; //ryi 3.5
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x-25, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-25, y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-25, y);
		}
		}
		else if(titleScreenState == 1) {
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
			String text = "select your class";
			int x = getXforCenteredText(text);
			int y = gp.tileSize *3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.tileSize *2;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-25, y);
			}
			
			text = "Thief";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-25, y);
			}
			
			text = "Wizard";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-25, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-25, y);
			}
			
		}
	}
	
	//PAUSED STATE
	public void drawPausedScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));//to change the font settings

		String text = "PAUSED";
		//length to get real size of the string going to be a pain since we use it alot
		//int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		
		
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text,x,y);
		
	}
	
	//DIALOGUE STATE
	public void drawDialogueScreen() {
		//WINDOW, we want a big rectangle in our window to communicate with NPC
		int x = gp.tileSize * 2 ;
		int y = gp.tileSize/2 ;
		int width = gp.screenWidth - (gp.tileSize * 4);// screen width - 4 tiles
		int height = gp.tileSize * 4 ;// 4 tiles downwards
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			
		//	currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];// disable for letter by letter
			
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			//in every loop we are adding one character
			//disable if statement if you dont want letter by letter text display
			if(charIndex<characters.length) {
				
			//	gp.playSE("Text music"); LATER!!
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				
				charIndex++;
			}

			if(gp.keyH.enterPressed == true) {
				charIndex = 0;//disable these for normal text display
				combinedText = "";
				if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
					npc.dialogueIndex++;
					gp.keyH.enterPressed = false;
				}
			}
		}
		else {//if it is null
			npc.dialogueIndex = 0;
			//we need this condition since we use the same method in trade select	
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
			if(gp.gameState == gp.cutsceneState) {
				gp.cManager.scenePhase++;
			}
		}
		
		
		//since g2 doesnt read \n we have to do this for loop to separate lines
		for(String Line : currentDialogue.split("\n")) {
			g2.drawString(Line, x, y);
			y += 40;
		}	
	}
	
	public void drawCharacterScreen() {
		
		//CREATE A FRAME
		final int frameX = gp.tileSize *2;//2 tiles to the right
		final int frameY = gp.tileSize;// 1 tile down
		final int frameWidth = gp.tileSize *5;
		final int frameHeight = gp.tileSize *11;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;
		
		//NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("XP", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight+5;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight ;
		
		//VALUES
		int tailX = (frameX + frameWidth) - 30;
		//Reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life+ "/" + gp.player.maxLife);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.mana+ "/" + gp.player.maxMana);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.xp);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelXP);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforRightAlignedText(value,tailX);
		g2.drawString(value, textX, textY);
		textY += 5;
		
		g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize, textY,null);
		textY += gp.tileSize;
		
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);

	}
								//boolean cursor to see if we show cursor or not
	public void drawInventory(Entity entity, boolean cursor) {//for checking which entity is the inventory for
		
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if(entity == gp.player) {
		//FRAME
		frameX = gp.tileSize * 9;
		frameY = gp.tileSize;
		frameWidth = gp.tileSize * 6;
		frameHeight = gp.tileSize * 5;
		slotCol = playerSlotCol;
		slotRow = playerSlotRow;
		
		}
		else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		this.drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3; //increasing slot Size by a little
		
		//DRAW PLAYER'S ITEMS
		for(int i = 0; i<entity.inventory.size() ; i++) {
			
			//EQUIP CURSOR
			if(entity.inventory.get(i) == entity.currentWeapon
					|| entity.inventory.get(i) == entity.currentShield
					|| entity.inventory.get(i) == entity.currentLight) {
				
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
				
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY,null);
			
			//DISPLAY AMOUNT
			if(entity == gp.player && entity.inventory.get(i).amount>1) {
				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;
				
				String x = "" + entity.inventory.get(i).amount;
				amountX = this.getXforRightAlignedText(x, slotX + 44);
				amountY = slotY + gp.tileSize;
				
				//SHADOW
				g2.setColor(new Color(60,60,60));
				g2.drawString(x, amountX, amountY);
				
				//NUMBER
				g2.setColor(Color.white);
				g2.drawString(x, amountX-3, amountY-3);
			}
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
			
		}
		
		if(cursor == true) {
		//CURSOR
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		//DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		//DESCRIPTION FRAME
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 3;
		//DRAW DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
		
		if(itemIndex < entity.inventory.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);//drawing sub-win only when we hover 

			
			for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
				
				g2.drawString(line, textX, textY);
				textY += 32;
			}
			if(entity.inventory.get(itemIndex).durability >= 100) {//only shows durability> 100
			g2.drawString("Durability: " + entity.inventory.get(itemIndex).durability,textX, textY+100);
			}
		}
	}
		}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		//Shadow
		text = "YOU DIED";
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//RETRY
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = this.getXforCenteredText(text);
		y += gp.tileSize *4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//BACK TO TITLE SCREEN
		text = "Quit";
		x = this.getXforCenteredText(text);
		y += 55; //since our font is bigger than tileSize
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}

	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//sub window
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		this.drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX,frameY); break;
		case 1: options_fullScreenNotification(frameX,frameY); break;
		case 2: options_control(frameX,frameY);break;
		case 3: options_endGameConfirm(frameX,frameY); break;
		}
		
		gp.keyH.enterPressed = false;
		
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		//FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize *2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		//MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SOUND EFFECT
		textY += gp.tileSize;
		g2.drawString("Sound Effect", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		//CONTROL
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		//END GAME
		textY += gp.tileSize;
		g2.drawString("Quit", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		//BACK
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);//dont like this
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		//FULL SCREEN CHECKBOX
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
		}
		
		//MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, gp.tileSize/2);//120/5= 24 px for the scaling
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		//SOUND EFFECT VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, gp.tileSize/2);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();//everytime options top is called, we save config.

	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will take\neffect after restarting \nthe game";
		for(String line: currentDialogue.split("\n")) {
			
			g2.drawString(line, textX, textY);
			textY += 40;
		}
	
		//BACK
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum==0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
		
	}
	
	public void options_control(int frameX, int frameY) {
		
		int textX;
		int textY;
		//TITLE 
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize*6;
		textY = frameY +  gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;

		//BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum==0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}


	}
	
	public void options_endGameConfirm(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "Quit the game and \nreturn to the title Screen?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		//YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.resetGame(true);
				titleScreenState = 0;
			}
		}
		//NO
		 text = "No";
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
				
	}
	
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));//increases opacity by 5 each iteration
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		if(counter == 50) {//the transition is done here
			
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.prevEventX = gp.player.worldX;
			gp.eHandler.prevEventY = gp.player.worldY;
			gp.changeArea();//to make the transition before switching areas
		}
	}
	
	public void drawTradeScreen() {
		switch(subState) {
		case 0: trade_select();break;
		case 1: trade_buy();break;
		case 2: trade_sell();break;
		}
		gp.keyH.enterPressed = false;
	}
	
	public void trade_select() {
		
		npc.dialogueSet = 0;
		drawDialogueScreen();//so we can display his dialogue 
		
		//DRAW WINDOW
		int x = gp.tileSize * 15; 
		int y = gp.tileSize *4;
		int width = gp.tileSize * 3;
		int height = (int)(gp.tileSize *3.5);
		drawSubWindow(x, y, width, height);
		
		//DRAW TEXT
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-25, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}}
		y+=gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-25, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}}
		y+=gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum = 0;//resetting commandNum
				npc.startDialogue(npc, 1);
			}}

		
	}
	public void trade_buy() {
		
		//DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);//false because we dont want to show cursor
		//DRAW NPC INVENTORY (MERCHANT)
		drawInventory(npc, true);
		
		//DRAW HINT WINDOW
		int x = gp.tileSize * 2; 
		int y = gp.tileSize *9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize *2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		//DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12; 
		y = gp.tileSize *9;
		width = gp.tileSize * 6;
		height = gp.tileSize *2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins: " + gp.player.coin, x+24, y+60);
		
		//DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex<npc.inventory.size()) {//meaning there wont be price on empty slot
			x = (int)(gp.tileSize * 5.5);
			y = (int)(gp.tileSize * 5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			this.drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = this.getXforRightAlignedText(text,gp.tileSize*8-20);
			g2.drawString(text, x, y+34);
			
			//BUY AN ITEM
			if(gp.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price>gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc, 2);
				}
				else {
					if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						npc.startDialogue(npc, 3);
					}
				}
				
//				else if(gp.player.inventory.size() == gp.player.inventorySize) { commented for now since now items are stackable
//					subState = 0;
//					gp.gameState = gp.dialogueState;
//					currentDialogue = "No place to carry more...";
//					this.drawDialogueScreen();
//				}
//				else {
//					gp.player.coin -= npc.inventory.get(itemIndex).price;
//					gp.player.inventory.add(npc.inventory.get(itemIndex));
//				}
			}
		}
	}
	public void trade_sell() {
		
		//DRAW PLAYER INVENTORY
		this.drawInventory(gp.player, true); //cursor can move in player
		
		//DRAW HINT WINDOW
		int x = gp.tileSize * 2; 
		int y = gp.tileSize *9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize *2;	
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		//DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12; 
		y = gp.tileSize *9;
		width = gp.tileSize * 6;
		height = gp.tileSize *2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins: " + gp.player.coin, x+24, y+60);
				
				//DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex<gp.player.inventory.size()) {//meaning there wont be price on empty slot
			x = (int)(gp.tileSize * 15.5);
			y = (int)(gp.tileSize * 5.5);
			width = (int)(gp.tileSize * 2.5);
			height = gp.tileSize;
			this.drawSubWindow(x, y, width, height);
		    g2.drawImage(coin, x+10, y+8, 32, 32, null);
				
			int price = gp.player.inventory.get(itemIndex).price/2;//making game more difficult by selling half the price
			String text = "" + price;
			x = this.getXforRightAlignedText(text,gp.tileSize*18-20);
			g2.drawString(text, x, y+34);
			//SELL AN ITEM
			if(gp.keyH.enterPressed == true) {
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon||
					 gp.player.inventory.get(itemIndex) == gp.player.currentShield){
					
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 4);
				}
				else {
					if(gp.player.inventory.get(itemIndex).amount >1) {
						gp.player.inventory.get(itemIndex).amount--;
					}
					else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coin += price;
				}
			}
					
		}
	}
	
	public void drawSleepScreen() {
		
		counter++;
		
		if(counter < 120) {
			gp.eManager.lighting.filterAlpha += 0.01f;
			if(gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		if(counter >= 120) {
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if(gp.eManager.lighting.filterAlpha <=0) {
				gp.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.eManager.lighting.dayCounter = 0;
				gp.gameState = gp.playState;
				gp.player.getPlayerImage(); //resetting the sprites
			}
		}
		
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {// this method is to find the item index in inventory
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,220); //RGB colors last int is opacity
		g2.setColor(c); //setting the color of our rectangle (sub window)
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));//width of the stroke (Border width)
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25); // this is not filled so its border
		
	}
	
	//this method is to get the x when drawing UI String since its a hassle to do it
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	public int getXforRightAlignedText(String text,int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		
		return x;
	}
	
}
