package main;

import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_SkeletonLord;
import object.OBJ_Axe;
import object.OBJ_BlueHeart;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Tent;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {
	//we created this class to handle object placement and NPC placement
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp){
		
		this.gp = gp;
		
	}
	//in this method we will instantiate objects to place them on the map.
	public void setObject() {
		//commented since we wont use them in new game concept
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = 23 * gp.tileSize;// never forget tile size
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize; // never forget panel start from top left corner
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);//add as many objects as you like it will be added
		gp.obj[mapNum][i].worldX = 12 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 34 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 19 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = 33 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = 35 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = 24 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = 25 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = 20 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = 14 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 28 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = 12 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 12 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX = 12 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 13 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX = 32 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = 12 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 10 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Lantern(gp);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 20 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		
		mapNum = 2;
		i = 0;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
		gp.obj[mapNum][i].worldX = 40 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 41 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = 18 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 23 * gp.tileSize;
		
		mapNum = 3;
		i = 0;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = 25 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
		gp.obj[mapNum][i].worldX = 25 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
	}
	
	public void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 21;
		gp.npc[mapNum][i].worldY = gp.tileSize * 21;
		
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
		
		mapNum = 2;
		i = 0;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 20;
		gp.npc[mapNum][i].worldY = gp.tileSize * 25;
		i++;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 11;
		gp.npc[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 23;
		gp.npc[mapNum][i].worldY = gp.tileSize * 14;
		
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i = 0;
				
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 23;
		gp.monster[mapNum][i].worldY = gp.tileSize * 36;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 26;
		gp.monster[mapNum][i].worldY = gp.tileSize * 37;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 24;
		gp.monster[mapNum][i].worldY = gp.tileSize * 37;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 34;
		gp.monster[mapNum][i].worldY = gp.tileSize * 42;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 38;
		gp.monster[mapNum][i].worldY = gp.tileSize * 42;
		i++;
		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 12;
		gp.monster[mapNum][i].worldY = gp.tileSize * 33;
//		i++;
//		gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize * 12;
//		gp.monster[mapNum][i].worldY = gp.tileSize * 33;
		
		mapNum = 2;
		i = 0;
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 34;
		gp.monster[mapNum][i].worldY = gp.tileSize * 39;
		i++;
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 36;
		gp.monster[mapNum][i].worldY = gp.tileSize * 25;
		i++;
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 39;
		gp.monster[mapNum][i].worldY = gp.tileSize * 26;
		i++;
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 28;
		gp.monster[mapNum][i].worldY = gp.tileSize * 11;
		
		
		mapNum = 3;
		i = 0;
		if(Progress.skeletonLordDefeated == false) {
		gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 23;
		gp.monster[mapNum][i].worldY = gp.tileSize * 16;
		}
		
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,27,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,28,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,29,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,30,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,31,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,32,12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,33,12);
		
		mapNum = 2;
		i = 0;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,30);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,31);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,32);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,34);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,34);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,33);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,22);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,24);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,18);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,19);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,20);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,21);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,13);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,14);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,22,28);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,30,28);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,32,28);
		
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,20,22);i++;
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,8,17);i++;
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,39,31);i++;
	
		
		

	}

}
