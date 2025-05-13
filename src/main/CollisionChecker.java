package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
		
	}
	
	public void checkTile(Entity entity) {
		
		//based on these coordinates we will find out the col and row nbs of collision;
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		//USE A TEMPORAL DIRECTION WHEN IT'S BEING KNOCKED BACK
		String direction = entity.direction;
		
		if(entity.knockBack == true) {
			direction = entity.knockBackDirection;
		}
		
		switch(direction) {
		
		case "up"://used speed to predict where the collision will happen
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow]; //tile on top left (since we are moving up)
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow]; // tile on top right (since we are moving up)
				//if the collision on tile1 or tile2 are true then entity cannot pass.
				if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
					
					entity.collisionOn = true; // if true player cant move
					
				}
				
			break;
		case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow]; //tile on top left (since we are moving up)
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow]; // tile on top right (since we are moving up)
				//if the collision on tile1 or tile2 are true then entity cannot pass.
				if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
					
					entity.collisionOn = true; // if true player cant move
					
				}
			break;
		case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow]; //tile on top left (since we are moving up)
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow]; // tile on top right (since we are moving up)
				//if the collision on tile1 or tile2 are true then entity cannot pass.
				if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
					
					entity.collisionOn = true; // if true player cant move
					
				}
			break;
		case "right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow]; //tile on top left (since we are moving up)
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow]; // tile on top right (since we are moving up)
				//if the collision on tile1 or tile2 are true then entity cannot pass.
				if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
					
					entity.collisionOn = true; // if true player cant move
					
				}
			break;
		
		
		}
		
	}
	//we are checking Object for collision
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		//USE A TEMPORAL DIRECTION WHEN IT'S BEING KNOCKED BACK
				String direction = entity.direction;
				
				if(entity.knockBack == true) {
					direction = entity.knockBackDirection;
				}
		
		for(int i = 0; i<gp.obj[1].length; i++) {
			//we will iterate through the used array indexes
			if(gp.obj[gp.currentMap][i] != null) {
				
				//Get entity's solid Area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				//Get object's solid Area position
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
				
				switch(direction) {
				
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.y += entity.speed;
					break;
				}
				//intersects is a simpler way to check collisions. (Rectangle method) (collisions between 2 rectangles)
				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					//to check if the object is solid, we use same method collisionOn
					if(gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
					}
					//if it is an npc we dont do anything, player we return index i
					if(player == true) {
						index = i;// i is going to be the object[i] that we picked
					}		
				}
				//we reset the values to Default X etc... after the switch
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
				
			}
			
			
		}
		
		return index;
	}
	
	/**
	 * @param entity
	 * @param target
	 * @return
	 */
	public int checkEntity(Entity entity, Entity[][] target) {
		//copied all this from checkObject()
		int index = 999;
		
		//USE A TEMPORAL DIRECTION WHEN IT'S BEING KNOCKED BACK
				String direction = entity.direction;
				
				if(entity.knockBack == true) {
					direction = entity.knockBackDirection;
				}
		
		for(int i = 0; i<target[1].length; i++) {
			//we will iterate through the used array indexes
			if(target[gp.currentMap][i] != null) {
				
				//Get entity's solid Area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				//Get object's solid Area position
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
				
				switch(direction) {
				
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
					
				}
				//intersects is a simpler way to check collisions. (Rectangle method) (collisions between 2 rectangles)
				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					if(target[gp.currentMap][i] != entity) {
						
						entity.collisionOn = true;
					
						index = i;
					
					}
				}
				//we reset the values to Default X etc... after the switch
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
				
			}
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		//Get object's solid Area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entity.direction) {//cleaned this code
		
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.y += entity.speed;
			break;
		}
		
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			
			entity.collisionOn = true;
			contactPlayer = true;
		
		}
		//we reset the values to Default X etc... after the switch
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
		
	}
	

}
