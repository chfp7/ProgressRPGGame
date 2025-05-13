package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {//this is an abstract super class since we only instantiate Player- 
	//class, NPC class etc... not the actual Entity class, it is only a blueprint.
	
	GamePanel gp;
	
	//public int x, y; we wont need this anymore since the x and y of player are static.
	public int worldX, worldY;// we will use this and screen. so the world moves. !the boy
	public int speed;
	
	//BuffImg describes an img with an accessible img data. We'll use it to store img files
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
	attackLeft2, attackRight1, attackRight2,//attack images (32x16,16x32)
	guardUp,guardDown,guardLeft,guardRight;
	public String direction = "down";//default direction is down, since objects dont change
	
	public Entity attacker;
	
	
	public int spriteNum = 1; // default start frame (there will be 2 in this game)
	
	 // this is going to be the entity's solidArea (it collides)
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default SArea for entity
	public Rectangle attackArea = new Rectangle(0,0,0,0);//hit area detection
	public int solidAreaDefaultX, solidAreaDefaultY; //added
	public boolean collisionOn = false;
	
	
	public boolean invincible = false;//to make the player invincible after hit
	public boolean attacking = false;
	public boolean alive = true; //alive by default, could die though...
	public boolean dying = false;
	public boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockBack = false;
	public boolean guarding = false;
	public boolean transparent = false;
	public boolean offBalance = false;;
	public boolean inRage = false;
	public boolean sleep = false;
	public boolean drawing = true; // for cutscene, player to become invisible to move cam
	
	public boolean boss;

	
	public String knockBackDirection;
	
	public int dyingCounter = 0;
	public int invincibleCount;
	public int hpBarCounter = 0;
	public int shotCounter = 0;
	public int knockBackCounter = 0;
	public int guardCounter = 0;
	public int offBalanceCounter = 0;
	public int actionLockCounter;//this is to slow down the NPC AI 
	public int spriteCounter = 0; // to make him walk (animation)

	
	public String dialogues[][] = new String[20][20];// the dialogues that will be displayed in sub win 2D array now
	public int dialogueSet = 0; 
	public int dialogueIndex = 0;
	//MOVED FROM SUPEROBJECT TO HERE
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;

	//PLAYER ATTRIBUTES
	public int defaultSpeed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int ammo;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int xp;
	public int nextLevelXP;
	public int coin;
	public int motion_1_duration;
	public int motion_2_duration;
	public Entity currentWeapon;
	public Entity currentShield;//we can add more like currentHelmet, current etc.. we will add in future
	public Entity currentLight;
	public Projectile projectile;
	public Entity linkedEntity;// to link an Entity to another entity
	public boolean temp = false;
	
	//ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();//moved inventory here so we can use on merchant
	public final int inventorySize = 20; //max inventory size
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int price;
	public int knockBackPower = 0;
	public boolean stackable = false;
	public int amount = 1;
	public int lightRadius; //tells us how much light item emits
	public int durability;//for swords
	//CHEST
	public Entity loot;
	public boolean opened = false;
	
	//TYPE
	public int type; //to check what type of collision (player-npc)(player-monster)
	public final int typePlayer = 0;
	public final int typeNPC = 1;
	public final int typeMonster = 2;
	public final int typeSword = 3;
	public final int typeAxe = 4;
	public final int typeShield = 5;
	public final int typeConsumable = 6;
	public final int typePickupOnly = 7;
	public final int typeObstacle = 8;
	public final int typeLight = 9;
	public final int typePickaxe = 10;
	
	
	
	public Entity(GamePanel gp) {//par
		
		this.gp = gp;
		
	}
	
	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		return screenX;
	}
	
	public int getScreenY() {
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		return screenY;
	}
	
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	
	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}
	
	public int getTopY() {
		return worldY + solidArea.y;
	}
	
	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}
	
	public int getCol() {
		return (worldX + solidArea.x)/gp.tileSize;
	}
	
	public int getRow() {
		return (worldY + solidArea.y)/gp.tileSize;
	}
	
	public int getCenterX() {
		int centerX = worldX + left1.getWidth()/2;
		return centerX;
	}
	
	public int getCenterY() {
		int centerY = worldY + up1.getHeight()/2;
		return centerY;
	}
	
	public int getXDistance(Entity target) {//changed from WorldX to method getCX()
		int xDistance = Math.abs(getCenterX() - target.getCenterX());
		return xDistance;
	}
	
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(getCenterY() - target.getCenterY());
		return yDistance;
	}
	
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target))/gp.tileSize;
		return tileDistance;
	}
	
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.solidArea.x)/gp.tileSize;
		return goalCol;
	}
	
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.solidArea.y)/gp.tileSize;
		return goalRow;
	}
	
	public void resetCounter() {
		dyingCounter = 0;
		invincibleCount = 0;
		hpBarCounter = 0;
		shotCounter = 0;
		knockBackCounter = 0;
		guardCounter = 0;
		offBalanceCounter = 0;
		actionLockCounter = 0;//this is to slow down the NPC AI 
		spriteCounter = 0; // to make him walk (animation)
	}
	
	public void setLoot(Entity loot) {
		
	}
	
	public void move(String direction) {
		
	}
	
	public void setAction() {
		
	}
	
	
	public void damageReaction() {//this is used for the AI behavior when monster is damaged
		
	}
	
	public void speak() {
		
//		if(dialogues[dialogueIndex] == null) { WILL DELETE AFTER FINISHED THE NEW ONE
//			dialogueIndex = 0;
//		}
//		
//		gp.ui.currentDialogue = dialogues[dialogueIndex];
//		// setting the current dialogue from npc to ui
//		
//		dialogueIndex++;
		
	}
	
	public void startDialogue(Entity entity, int setNum) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		dialogueSet = setNum;
		
	}
	
	public void facePlayer() {
		
		switch(gp.player.direction) {
		
		case "up": direction = "down";
			break;
		case "down": direction = "up";
			break;
		case "right": direction = "left";
			break;
		case "left": direction = "right";
			break;
		}
	}
	
	public void interact() {
		
	}
	
	public boolean use(Entity entity) {
		return false;
	}
	
	public void checkDrop() {}
	
	public void dropItem(Entity droppedItem) {
		
		for(int i = 0; i< gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {//we scan array, if we find open slot we assign the item
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;// the dead monster's worldX and worldY
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;//important since when we find the spot, we get out of the loop (efficiency)
			}
		}
		
	}
	//these are just a blueprint for sub classes
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	
	public int getParticleMaxLife() {//determines how long are particles in the air
		int maxLife = 0;
		return maxLife;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		//if we want particle to move to top left direction xd and yd are both -1 
		Particle p1 = new Particle(gp,target,color,size,speed,maxLife, -2, -1);//put generator instead of target when you want the fireball to blast
		Particle p2 = new Particle(gp,target,color,size,speed,maxLife, 2, -1);
		Particle p3 = new Particle(gp,target,color,size,speed,maxLife, -2, 1);
		Particle p4 = new Particle(gp,target,color,size,speed,maxLife, 2, 1);

		gp.particleList.add(p1);//adding particle to particle list
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
		
	}
	
	public void checkCollision() {//moved from update to here 
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);//all entity collision w/npc
		gp.cChecker.checkEntity(this, gp.monster);//all entity collision w/ monster
		gp.cChecker.checkEntity(this, gp.iTile);//all entitycollision with interactive tile
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		//damage player)
		if(this.type == typeMonster && contactPlayer == true) {//when monster contacts player
		
			damagePlayer(attack);
			
		}
	}
	
	public void update() {
		
		if(sleep == false) {
		if(knockBack== true) {
			
			checkCollision();
			
			if(collisionOn == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			else if(collisionOn == false) {
				
				switch(knockBackDirection) {
			case "up":worldY -= speed;break;
			case "down":worldY += speed;break;
			case "left":worldX -= speed;break;
			case "right":worldX += speed;break;
				}
			}
			
			knockBackCounter++;
			if(knockBackCounter == 10) {//the more we increase this nb the more the distance
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			
		}
		else if(attacking == true) {
			attacking();
		}
		else {
		
		setAction();
		
		checkCollision();
		
		//If collisionOn is false , npc can move copied from player class in 
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		case "right":
			worldX += speed;
			break;
			}
		}
		
		//for the animation
		spriteCounter++;
		if(spriteCounter>24) { //I can adjust the speed here (10 to 30) slower
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
		   spriteCounter = 0; //not putting "else" since I just want to reset it
		}
		}
		
		if(invincible == true) {//we are limiting invincible time to approx 0.6 second
			invincibleCount++;
			
			if(invincibleCount>40) {
				invincible = false;
				invincibleCount = 0;
			}
			
		}
		if(shotCounter < 60) {
			shotCounter++;
		}
		
		if(offBalance == true) {
			offBalanceCounter++;
			if(offBalanceCounter>60) { //one second off balance
				offBalance = false;
				offBalanceCounter = 0;
			}
		}
		}
	}
	
	//the monster will only swing it's weapon only at certain distance, horizontally or vertically (straight)
	public void checkAttackOrNot(int rate, int straight, int horizontal) {
		
		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);
		
		switch(direction) {
		case "up": 
			if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
			targetInRange = true;
		}break;
		case "down":
			if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}break;
		case "left":
			if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}break;
		case "right":
			if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}break;
		}
		
		if(targetInRange == true) {
			//check if it initiates an attack
			int i = new Random().nextInt(rate);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				
				
				
				
				shotCounter = 0;
			}
		}
		
	}
	
	public void checkShootOrNot(int rate, int shotInterval) {
		//shooting projectiles
		int i = new Random().nextInt(rate);
		if(i==0 && projectile.alive == false && shotCounter == shotInterval) {
			projectile.set(worldX, worldY, direction, true, this);
			for(int ii = 0; ii< gp.projectile[1].length; ii++) {
				if(gp.projectile[gp.currentMap][ii] == null) {
					gp.projectile[gp.currentMap][ii] = projectile;
					break;
				}
			}
			shotCounter = 0;
		}
	}
	
	public void checkStartChasingOrNot(Entity target, int distance, int rate) {
		
		if(getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = true;
			}
		}
	}
	
	public void checkStopChasingOrNot(Entity target, int distance, int rate) {
		
		if(getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = false;
			}
		}
	}
	
	public void checkRandomDirection(int interval) {
		
		actionLockCounter++;
		//every 120 frames the direction will be decided (2 seconds)
		if(actionLockCounter > interval) {
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
	
	public void moveTowardPlayer(int interval) {
		
		actionLockCounter++;
		if(actionLockCounter > interval) {
			if(getXDistance(gp.player) > getYDistance(gp.player)) {
				
				if(gp.player.getCenterX() < getCenterX()) {
					direction = "left";
				}
				else {
					direction = "right";
				}
				
			}
			else if(getXDistance(gp.player) < getYDistance(gp.player)) {
				if(gp.player.getCenterY() < getCenterY()) {
					direction = "up";
				}
				else {
					direction = "down";
				}
			}
			actionLockCounter = 0;
		}
		
	}
	
	public String getOppositeDirection(String direction) {
		
		String oppositeDirection = "";
		
		switch(direction) {
		case "up": oppositeDirection = "down";break;
		case "down": oppositeDirection = "up";break;
		case "left": oppositeDirection = "right";break;
		case "right": oppositeDirection = "left";break;
		}
		
		return oppositeDirection;
		
	}
	
	public void attacking() {//(check Notes)
		
		spriteCounter++;
		
		if(spriteCounter <= motion_1_duration) {//we show attacking_1 during first 5 frames
			spriteNum = 1;
		}
		if(spriteCounter > motion_1_duration && spriteCounter <= motion_2_duration) {
			spriteNum = 2; // we show attacking 2 the next 20 frames,
			
			//saving current data before modifying them temporarily
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight= solidArea.height;
			
			//Adjust player's worldX/Y for the attackArea
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			//attack area becomes solid area
			solidArea.width = attackArea.width;	
			solidArea.height = attackArea.height;
			
			if(type == typeMonster) {
				if(gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attack);
				}
			}
			else {//FOR PLAYER
				//Checking monster collision with updated world X, worldY, and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			gp.player.damageMonster(monsterIndex,this,attack,currentWeapon.knockBackPower);
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			gp.player.damageInteractiveTile(iTileIndex);
			
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			gp.player.damageProjectile(projectileIndex);
			}
			
			
			
			//after checking collision, restore original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		
		if(spriteCounter>motion_2_duration) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	
	public void damagePlayer(int attack) {
		
		if(gp.player.invincible == false) {
			
			int damage = attack - gp.player.defense;
			//get an opposite direction of attacker 
			
			String canGuardDirection = getOppositeDirection(direction);
			
			if(gp.player.guarding == true && gp.player.direction == canGuardDirection) {
				
				//PARRY 
				if(gp.player.guardCounter < 10) {
					damage = 0;
					//gp.playSE("PARRY"); LATER!!!
					setKnockBack(this, gp.player, knockBackPower);
					offBalance = true;
					spriteCounter -= 60; //MONSTER WILL LOOK FROZEN SINCE HE IS OFF BALANCE
				}else {
				damage/=3;
				//gp.playSE("GUARD"); LATER
				}
			}
			else {
				//NOT GUARDING
				//gp.playSE(6);got a null problem with 6 and 7
				
			if(damage<1) {//WAS 1 we will see how this checks out
				damage = 1;
			}
			}
			
			if(damage != 0 ) {
				gp.player.transparent = true;
				setKnockBack(gp.player,this,knockBackPower);//target is player and monste is attacker
			}
			
			
			gp.player.life -= damage;
			gp.player.invincible = true;
			
		}
	}
	
	public void setKnockBack(Entity target,Entity attacker, int knockBackPower) {
		
		this.attacker = attacker;
		target.knockBackDirection = attacker.direction;
		target.speed += knockBackPower = 10;
		target.knockBack = true;
		
	}
	
	public boolean inCamera() {//great way to portray if Statement methods
		boolean inCamera = false;
		if(worldX + gp.tileSize *5 > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize *5> gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
			inCamera = true;
		}
		
		return inCamera;
					
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		//very similar draw method as superobject, only difference is switch, (direction)
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		//we can replace it with if(in camera)
		if(worldX + gp.tileSize *5 > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
		&& worldY + gp.tileSize *5> gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
			//we can use getScreenX and getScreenY but I prefer this to read it better 
			int tempScreenX = screenX;//since we have an issue with attack up and left
			int tempScreenY = screenY;
			
			switch(direction) {
			case "up":   if(attacking == false) {
				if(spriteNum == 1) { image = up1;}
			    if(spriteNum == 2) { image = up2;}}
					     if(attacking == true) {
				tempScreenY = screenY - up1.getHeight();//was tileSize instead of up1
			    if(spriteNum == 1) { image = attackUp1;}
				if(spriteNum == 2) { image = attackUp2;}}
			    break;
			case "down": if(attacking == false) {
				if(spriteNum == 1) { image = down1;}
			    if(spriteNum == 2) { image = down2;}}
					     if(attacking == true) {
			    if(spriteNum == 1) { image = attackDown1;}
				if(spriteNum == 2) { image = attackDown2;}}
			    break;
			case "left": if(attacking == false) {
				if(spriteNum == 1) { image = left1;}
			    if(spriteNum == 2) { image = left2;}}
					     if(attacking == true) {
				tempScreenX = screenX - left1.getWidth();
			    if(spriteNum == 1) { image = attackLeft1;}
				if(spriteNum == 2) { image = attackLeft2;}}
			    break;
			case "right": if(attacking == false) {
				if(spriteNum == 1) { image = right1;}
			    if(spriteNum == 2) { image = right2;}}
						if(attacking == true) {
			    if(spriteNum == 1) { image = attackRight1;}
				if(spriteNum == 2) { image = attackRight2;}}
			    break;
			}
//			//HEALTHBAR (monster)
//			if(type == 2 && hpBarOn == true) {//meaning entity is a monster
//				//to know how much is one tiny rectangle (one life space in a bar)
//				double oneScale = (double)gp.tileSize/maxLife;
//				double hpBarValue = oneScale*life;//onescale x life (life decrease, so does the width)
//						
//				g2.setColor(new Color(35,35,35));
//				g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
//				
//				g2.setColor(new Color(255,0,30));
//				g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
//				hpBarCounter++;
//				if(hpBarCounter>180) {
//					hpBarCounter = 0;
//					hpBarOn = false;
//				}
//			}
			
			if(invincible == true) {
				
				hpBarOn = true;
				hpBarCounter = 0;//since we hitting the monster again so counter is reset
				changeAlpha(g2,0.4f);
				
			}
			
			if(dying == true) {
				
				dyingAnimation(g2);
				
			}
			
		g2.drawImage(image, tempScreenX, tempScreenY,null);
		
		changeAlpha(g2,1f);

		}
		}
	//cute lil dying blinking animation
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		int i = 5; //cleaner way to write to change easier
		if(dyingCounter <= i) {	changeAlpha(g2,0f);}
		if(dyingCounter > i && dyingCounter <=i*2) {changeAlpha(g2,1f);}//could be custom images(sprites) instead of changeAlpha
		if(dyingCounter > i*2 && dyingCounter <=i*3) {changeAlpha(g2,0f);}
		if(dyingCounter > i*3 && dyingCounter <=i*4) {changeAlpha(g2,1f);}
		if(dyingCounter > i*4 && dyingCounter <=i*5) {changeAlpha(g2,0f);}
		if(dyingCounter > i*5 && dyingCounter <=i*6) {changeAlpha(g2,1f);}
		if(dyingCounter > i*6 && dyingCounter <=i*7) {changeAlpha(g2,0f);}
		if(dyingCounter > i*7 && dyingCounter <=i*8) {changeAlpha(g2,1f);}
		if(dyingCounter>i*8) {
			alive = false;
		}
	}
	//to change image opacity 
	public void changeAlpha(Graphics2D g2,float alphaValue) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
		
	}
	
	//we moved setup here since we will do this to all entities, we only change the path now
	public BufferedImage setup(String imagePath, int width, int height) {//copied and pasted from Player Class and changed
		
		UtilityTool uTool = new UtilityTool();
		
		BufferedImage image = null;
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image, width, height);//changed in from gp.tileSize to width and height since the image formats will differ in the future

		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
		
	}
	
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.pFinder.search() == true) {
			
			//NEXT WORLDX AND WORLDY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			//ENTITY'S SOLID AREA POSITION
			int enLeftX =  worldX + solidArea.x;
			int enRightX =  worldX + solidArea.x + solidArea.width;
			int enTopY =  worldY + solidArea.y;
			int enBottomY =  worldY + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX<nextX + gp.tileSize) {
				
				direction = "up";
				
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX<nextX + gp.tileSize) {
				
				direction = "down";
				
			}//checked till here
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				//left or right
				if(enLeftX>nextX) {
				direction = "left";
				}
				if(enLeftX<nextX) {
					direction = "right";
					}
			}//checked
			else if(enTopY>nextY && enLeftX> nextX) {
				//up or left
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}//checked
			else if(enTopY>nextY && enLeftX<nextX) {
				//up or right
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}//checked
			else if(enTopY<nextY && enLeftX>nextX) {
				//down or left
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}//checked till here
			else if(enTopY<nextY && enLeftX<nextX) {
				//down or right
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			//if we find the goal, we stop search disable it if you want npc to follow you
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow = gp.pFinder.pathList.get(0).row;
//			if(nextCol == goalCol && nextRow == goalRow) {
//				onPath = false;
//			}
			
		} 
	}
	
	public int getDetected(Entity user, Entity target[][],String targetName ) {
		int index = 999;
		//Check the surrounding objects
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		switch(user.direction) {//if an object is adjecent to the user, it gets detected
		case "up": nextWorldY = user.getTopY() - user.speed; break;
		case "down": nextWorldY = user.getBottomY() + user.speed;break;
		case "left": nextWorldX = user.getLeftX() - user.speed;break;
		case "right": nextWorldX = user.getRightX() + user.speed;break;

		}
		int col = nextWorldX/gp.tileSize;
		int row = nextWorldY/gp.tileSize;
		
		for(int i = 0; i<target[1].length; i++) {
			if(target[gp.currentMap][i] != null) {
				if(target[gp.currentMap][i].getCol() == col && 
						target[gp.currentMap][i].getRow() == row &&
						target[gp.currentMap][i].name.equals(targetName)) {
					
					index = i;
					break;
					
				}
			}
		}
		return index;
	}
	
}
