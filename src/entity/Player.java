package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import tile.Tile;

//Character will be made using piskel (sprite)
public class Player extends Entity{

	//GamePanel gp; deleted since we dont need it anymore, its included in entity class
	KeyHandler keyH;
	
	public int screenX; // final variables dont change (I removed final in (for full screen)
	public int screenY; //removed final
	
	int standCounter= 0;// counter to count frames stood
	
	public boolean attackCanceled = false;// to cancel attack when pressing enter
	
	public boolean lightUpdated = false; 
	
	//moved inventory stuff to Entity class
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);// we take the gp from the entity super class
		
		//this.gp = gp; since we called super(gp) (we are taking super class' gamepanel)
		
		this.keyH = keyH;
		//(dont have to write this. when its not the same name) 
		this.screenX = gp.screenWidth/2 - (gp.tileSize/2); // added gptile to center right
		//both will return the halfway point of the screen.
		this.screenY = gp.screenHeight/2 - (gp.tileSize/2);//(keep in mind x and y are
		//dependent of top left corner of the screen so thats why we subtract
		// so screen x and y are static. worldxy moves.
		
		//these are all values for the collision rectangle
		solidArea = new Rectangle();
		solidArea.x = 8; //8 px from the left
		solidArea.y = 16;// 16 px from the top
		solidAreaDefaultX = solidArea.x;//added default x... in  (object interaction)
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		//we can change the range of the attack by increasing these below
		attackArea.width = 36;// a little smaller than tileSize (48)
		attackArea.height = 36;
		
		setDefaultValues();
		
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23; // these used to be x and y before (camera change)
		worldY = gp.tileSize * 21; // these are th starting positions of boy in the map
//		worldX = gp.tileSize * 10; // these used to be x and y before (camera change)
//		worldY = gp.tileSize * 9; these are the interior coordinates
//		gp.currentMap = 1;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
		//PLAYER STATUS
		level = 1;
		maxLife = 6; //6 lives means 3 hearts since one heart is 2 lives, (half = 1 life)
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		strength = 1;//the more strength player has, the more damage he gives
		dexterity = 1;//the more dex he has the less damage he recieves
		xp = 0;
		nextLevelXP = 5;
		coin = 50;
		currentWeapon = new OBJ_Sword_Normal(gp);//these are entities(objects so we can instantiate them)
		currentShield = new OBJ_Shield_Wood(gp);
		currentLight = null;
		projectile = new  OBJ_Fireball(gp);//this means the character has this skill at beggining of game
		attack = getAttack();//total attack is decided by strength + weapon
		defense = getDefense();//total defense is decided by dex + shield
		
		getPlayerImage();
		getPlayerAttackImage();
		getPlayerGuardImage();
		setItems(); 
		setDialogue();
	}
	
	public void setDefaultPositions() {
		gp.currentMap = 3;//to always respawn in map 0
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}
	
	public void setDialogue() {
		dialogues[0][0] = "YOU ARE LEVEL " + level + " NOW \n you feel stronger now...";
	}
	
	public void restoreStatus() {
		life = maxLife;
		mana = maxMana;
		speed = defaultSpeed;
		invincible = false;
		transparent = false;
		attacking = false;
		guarding = false;
		knockBack = false;
		lightUpdated = true;
	}
	
	public void restoreLifeandMana() {
		life = maxLife;
		mana = maxMana;
		invincible = false;
		transparent = false;
	}
	
	public void setItems() {//method to set the items in player inventory
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		
	}
	
	public int getAttack() {//these values will change throughout the game
		attackArea = currentWeapon.attackArea;
		motion_1_duration = currentWeapon.motion_1_duration;
		motion_2_duration =  currentWeapon.motion_2_duration;
		return attack = strength*currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity*currentShield.defenseValue;
	}
	
	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for(int i = 0; i< inventory.size(); i++) {
			if(inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		return currentWeaponSlot;
	}
	
	public int getCurrentShieldSlot() {
		int currentShieldSlot = 0;
		for(int i = 0; i< inventory.size(); i++) {
			if(inventory.get(i) == currentShield) {
				currentShieldSlot = i;
			}
		}
		return currentShieldSlot;
	}
	
//	public void getPlayerImage() {
//!		I did this when the getClass Resource etc was not working, This method below is not good since it only works in my Laptop
//		try {
//			
//			up1 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_up_1.png"));
//			up2 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_up_2.png"));
//			down1 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_down_1.png"));
//			down2 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_down_2.png"));
//			left1 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_left_1.png"));
//			left2 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_left_2.png"));
//			right1 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_right_1.png"));
//			right2 = ImageIO.read(new File("C:\\Users\\MACBOOK\\Desktop\\programming practice and projects\\Uni workspace\\My2DGame\\res\\player\\boy_right_2.png"));
//
//			
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
	
	public void getPlayerImage() {
		
	//Before Optimization
//		try {
//			
//			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
//			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
//			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
//			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
//			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
//			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
//			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
//
//		}catch(IOException e){
//			e.printStackTrace();
//		} AFTER OPTIMIZATION (DEBUGGING)
	up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
	up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
	down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
	down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
	left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
	left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
	right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
	right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);


	
	
	}
	
	public void getPlayerSleepingImage(BufferedImage image) {
		up1 = image;//the tent image
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 = image;
		right1 = image;
		right2 = image;
	}

	public void getPlayerAttackImage() {//setting up attack images
		
		if(currentWeapon.type == typeSword) {
		attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		else if (currentWeapon.type == typeAxe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
		else if (currentWeapon.type == typePickaxe) {
			attackUp1 = setup("/player/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
		}
	}
	
	public void getPlayerGuardImage(){
		
		guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
		guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
		guardLeft = setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
		guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);

	}

	//deleted setup from here moved it to Entity class
	
	//to update the information, we will use the 2 methods below in our while loop.
		public void update() {
			
			if(knockBack== true) {
				

				//CHECKING FOR TILE COLLISION
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				//CHECK FOR OBJECT COLLISION
				//based on int objIndex we will determine whats going to happen if player collides with the object
				gp.cChecker.checkObject(this, true);//true since this is the player class
				
				//CHECK FOR NPC COLLISION
				gp.cChecker.checkEntity(this, gp.npc);
				
				//CHECK FOR MONSTER COLLISION
				 gp.cChecker.checkEntity(this, gp.monster);
				
				//CHECK FOR INTERACTIVE TILE COLLISION
				gp.cChecker.checkEntity(this, gp.iTile);
								
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
			
			else if(keyH.spacePressed == true) {
				guarding = true;
				guardCounter++;
			}
			else if(keyH.upPressed == true||keyH.downPressed == true||keyH.rightPressed == true||keyH.leftPressed == true||keyH.enterPressed == true) {
			 //java up left corner X = 0, Y = 0. As X++, X go right, as Y++ Y go down.
				if(keyH.upPressed == true) {
					direction = "up"; //so we can recognize which pos so the img change
					//worldY -= speed; implemented collision so we change these.
				}
				else if(keyH.downPressed == true) {
					direction = "down";
					//worldY += speed; we put these below in the if collisionOn statement
				}
				else if(keyH.leftPressed == true) {
					direction = "left";
					//worldX -= speed;
				}
				else if(keyH.rightPressed == true) {
					direction = "right";
					//worldX += speed;
				}
				
				
				//CHECKING FOR TILE COLLISION
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				//CHECK FOR OBJECT COLLISION
				//based on int objIndex we will determine whats going to happen if player collides with the object
				int objIndex = gp.cChecker.checkObject(this, true);//true since this is the player class
				pickUpObject(objIndex);
				
				//CHECK FOR NPC COLLISION
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				
				//CHECK FOR MONSTER COLLISION
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				contactMonster(monsterIndex);
				
				//CHECK FOR INTERACTIVE TILE COLLISION
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				
				//CHECK FOR EVENT
				gp.eHandler.checkEvent();
								
				//If collisionOn is false , player can move
				if(collisionOn == false && keyH.enterPressed == false) {
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
				
				if(gp.keyH.enterPressed == true && gp.player.attackCanceled == false) {
					
					//gp.playSE(swing);add in future
					attacking = true;
					spriteCounter = 0;
					
					//TO DECREASE DURABILITY OF SWORD
					currentWeapon.durability--;
					
				}
				
				attackCanceled = false;
				
				gp.keyH.enterPressed = false; //after everything we have done we cancel

				guarding = false;
				guardCounter = 0;
				
				//for the animation
				spriteCounter++;
				if(spriteCounter>10) { //I can adjust the speed here (10 to 30) slower
					if(spriteNum == 1) {
						spriteNum = 2;
					}
					else if(spriteNum == 2) {
						spriteNum = 1;
					}
				   spriteCounter = 0; //not putting "else" since I just want to reset it
				}
			}
			
			else {standCounter++;
				if(standCounter == 20) { // each 20 frames this if statement happens
				spriteNum = 1;//so the player's default position is first sprite (at rest)
				standCounter = 0; // count reset every 20 frames
				}
				guarding = false;
				guardCounter = 0;
			}
			//if we press f and projectile is not alive, we can shoot. only 1 proj at a time
			if(gp.keyH.shootPressed == true && projectile.alive == false && shotCounter == 60 && projectile.haveResource(this) == true) {
				//set default coordinates, direction, and user
				projectile.set(worldX,worldY,direction,true,this);
				//subtracting mana when using projectile
				projectile.minusResource(this);
				//adding projectile to the list
				//check vacancy
				for(int i = 0; i< gp.projectile[1].length; i++) {
					if(gp.projectile[gp.currentMap][i] == null) {
						gp.projectile[gp.currentMap][i] = projectile;
						break;
					}
				}
				//gp.projectileList.add(projectile); commented in (array change)
				
				gp.playSE(8);// implement sounds later
				
				shotCounter = 0;
				
				
			}
			
			//this needs to be outside of key if statement
			if(invincible == true) {//we are limiting invincible time to 1 second
				invincibleCount++;
				
				if(invincibleCount>60) {
					invincible = false;
					transparent = false;
					invincibleCount = 0;
				}
				
			}
			if(shotCounter < 60) {
				shotCounter++;
			}
			if(life > maxLife) {
				life = maxLife;
			}
			if(mana > maxMana) {
				mana = maxMana;
			}
			if(keyH.superModeOn == false) {
			if(life <= 0) {//when player dies gameState is now gameOver
				gp.gameState = gp.gameOverState;
				gp.stopMusic();
			//	gp.playMusic("Died music"); later
				gp.ui.commandNum = -1; //acts as a placeholder
				//gp.playSE("Died sound"); later
			}
			}
			}
		
		
		public void pickUpObject(int i) {
			//deleted treasure game stuff
			if(i != 999) {
				
				//PICKUP ONLY ITEMS
				if(gp.obj[gp.currentMap][i].type == typePickupOnly) {
					
					gp.obj[gp.currentMap][i].use(this);
					gp.obj[gp.currentMap][i] = null;
					
				}
				else if(gp.obj[gp.currentMap][i].type == typeObstacle) {
					if(keyH.enterPressed == true) {
						gp.obj[gp.currentMap][i].interact();
						attackCanceled = true;
					}
				}
				else {//INVENTORY ITEMS
					String text;
				
				if(canObtainItem(gp.obj[gp.currentMap][i]) == true) {					
					//gp.playSE(coin effect); when sound finally works
					text = "You got a " + gp.obj[gp.currentMap][i].name + "!";
				}
				else {
					text = "You can't carry anymore!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
				}
			}	
		}
		
		public void interactNPC(int i) {
	
			if(i != 999) {
				
				if(gp.keyH.enterPressed == true) {
				// debug : System.out.println("You are hitting npc");
				attackCanceled = true;
				//gp.gameState = gp.dialogueState; //when we collide with npc Dstate occurs//no need anymore
								
				gp.npc[gp.currentMap][i].speak();
				
			}
			
			gp.npc[gp.currentMap][i].move(direction);
				
			}
			
			//gp.keyH.enterPressed = false; error happened in so we moved it
			
		}
		
		public void contactMonster(int i) {
			
			if(i != 999) {
				//so player doesnt recieve damage when hes invincible or monster is dying
				if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
					
				//gp.playSE(6); // recieve damage sound got a null problem with 6 and 7
					
					int damage = gp.monster[gp.currentMap][i].attack - gp.player.defense;
					if(damage<1) {
						damage =1;
					}
				life -=  damage;//the monster is attacking 60 times/sec so we do inv time
				invincible = true;
				transparent = true;
				}
				
			}
			
		}
		
		public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {//added attack parameter in (notes) and kbp in 41
			
			if(i != 999) {
			
				if(gp.monster[gp.currentMap][i].invincible == false) {
					
					gp.playSE(5);
					if(knockBackPower>0) {
					setKnockBack(gp.monster[gp.currentMap][i],attacker,knockBackPower);
					}
					
					if(gp.monster[gp.currentMap][i].offBalance == true) {
						attack *=2;
					}
					
					//making stats work 
					int damage = attack - gp.monster[gp.currentMap][i].defense;
					if(damage<0) {
						damage = 0;
					}
					gp.monster[gp.currentMap][i].life -= damage;//now damage depends on stats
					gp.ui.addMessage(damage + " damage!"); // will remove this soon 
					
					gp.monster[gp.currentMap][i].invincible = true;
					gp.monster[gp.currentMap][i].damageReaction();//I dont align with this method
					
					if(gp.monster[gp.currentMap][i].life <= 0) {
						gp.monster[gp.currentMap][i].dying = true;
						gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
						xp += gp.monster[gp.currentMap][i].xp;
						gp.ui.addMessage("gained " + gp.monster[gp.currentMap][i].xp + "XP" );
						checkLevelUp();
					}
				}
				
			}
			
		}
		

		//very useful for item placement and item removal also. same mech
		public void damageInteractiveTile(int i) {
			
			if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
				//gp.iTile[i].playSE(); when sounds are implemented
				gp.iTile[gp.currentMap][i].life -= strength;
				gp.iTile[gp.currentMap][i].invincible = true;//if we dont put true counter wont work and tree will take too many hits
				
				//generate particles
				generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
				
				if(gp.iTile[gp.currentMap][i].life==0) {
					//gp.iTile[gp.currentMap][i].checkDrop();LATER
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();}//this returns the trunk in its place
			}
			
		}
		
		public void damageProjectile(int i) {
			
			if(i!=999) {
				Entity projectile = gp.projectile[gp.currentMap][i];
				projectile.alive = false;
				generateParticle(projectile,projectile);
			}
			
		}
		
		//see how level up changes
		public void checkLevelUp() {
			if(xp>=nextLevelXP) {
				level++;
				nextLevelXP = nextLevelXP *2;//we can change this later
				maxLife += 2; // might change this later , we can increase strength maybe
				strength++;
				dexterity++;
				attack = getAttack();
				defense = getDefense();
				//gp.playSE(soundeffect for future)
				gp.gameState = gp.dialogueState;
				
				setDialogue();
				startDialogue(this,0);
			}
			
			
		}
		
		public void selectItem() {
			
			int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
			
			if(itemIndex < inventory.size()) {
				Entity selectedItem = inventory.get(itemIndex);
				
				if(selectedItem.type == typeSword||selectedItem.type == typeAxe||selectedItem.type == typePickaxe) {
					currentWeapon = selectedItem;
					attack = getAttack();
					getPlayerAttackImage();
				}
				if(selectedItem.type == typeShield) {
					currentShield = selectedItem;
					defense = getDefense();
				}
				if(selectedItem.type == typeLight) {
					if(currentLight == selectedItem) {
						currentLight = null;
					}
					else {
						currentLight = selectedItem;
					}
					lightUpdated = true;
				}
				if(selectedItem.type == typeConsumable) {
					if(selectedItem.use(this) == true) {
						if(selectedItem.amount>1) {
							selectedItem.amount--;
						}
						else {
					inventory.remove(itemIndex);
						}
					}
				}
			}
			
		}
		//if there is an item with the same name, we can stack it (if stackable)
		public int searchItemInInventory(String itemName) {//to scan items in inventory
			
			int itemIndex = 999;
			
			for(int i = 0; i < inventory.size() ; i++) {
				
				if(inventory.get(i).name.equals(itemName)) {
					itemIndex = i;break;
				}
			}
			return itemIndex;	
		}
		
		public boolean canObtainItem(Entity item) {//check if we can obtain item,
			
			boolean canObtain = false;
			
			Entity newItem = gp.eGenerator.getObject(item.name);
			
			//checking if item is stackable
			if(newItem.stackable == true) {
				int index = searchItemInInventory(newItem.name);
				if(index != 999) {
					inventory.get(index).amount++;
					canObtain = true;
					}
				else {//new item so need to check vacancy
					if(inventory.size() != inventorySize) {
						inventory.add(newItem);
						canObtain = true;
					}
				}
			}
			else {//not stackable, check vacancy
				if(inventory.size() != inventorySize) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
			
			return canObtain;
			
		}

		public void draw(Graphics2D g2) {
			//these were for test
			  //g2.setColor(Color.white);
			 
			  //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			BufferedImage image = null;
			int tempScreenX = screenX;//since we have an issue with attack up and left
			int tempScreenY = screenY;
			
			switch(direction) {
			case "up":   if(attacking == false) {
				if(spriteNum == 1) { image = up1;}
			    if(spriteNum == 2) { image = up2;}}
					     if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
			    if(spriteNum == 1) { image = attackUp1;}
				if(spriteNum == 2) { image = attackUp2;}}
					     if(guarding == true) {
						    	image = guardUp;
						    }
				break;
			   
			case "down": if(attacking == false) {
				if(spriteNum == 1) { image = down1;}
			    if(spriteNum == 2) { image = down2;}}
					     if(attacking == true) {
			    if(spriteNum == 1) { image = attackDown1;}
				if(spriteNum == 2) { image = attackDown2;}}
					     if(guarding == true) {
						    	image = guardDown;
						    }
			    break;
			case "left": if(attacking == false) {
				if(spriteNum == 1) { image = left1;}
			    if(spriteNum == 2) { image = left2;}}
					     if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
			    if(spriteNum == 1) { image = attackLeft1;}
				if(spriteNum == 2) { image = attackLeft2;}}
					     if(guarding == true) {
						    	image = guardLeft;
						    }
			    break;
			case "right": if(attacking == false) {
				if(spriteNum == 1) { image = right1;}
			    if(spriteNum == 2) { image = right2;}}
					      if(attacking == true) {
			    if(spriteNum == 1) { image = attackRight1;}
				if(spriteNum == 2) { image = attackRight2;}}
					      if(guarding == true) {
						    	image = guardRight;
						    }
			    break;
			}
			//change player opacity when player is invincible (he was hit)
			if(transparent == true) {
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
				
			}
			
			if(drawing == true) {
			//make player move around use x, y. To make him static in middle use screenxy
			g2.drawImage(image, tempScreenX, tempScreenY, null);// we deleted scaling (gp.tilesize)
			}
			//reset alpha composite (opacity)
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			
		}
	
}
