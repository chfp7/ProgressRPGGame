package entity;

import main.GamePanel;
import object.OBJ_Potion_Red;

public class NPC_Merchant extends Entity{
	public NPC_Merchant(GamePanel gp) {
		
		super(gp);
		type = typeNPC;
		direction = "down";
		speed = 1; // since old man is slow
		
		getNPCImage();
		setDialogue();
		setItems();
		
	}
	
	public void getNPCImage() {//copied and pasted from Player Class
	
		up1 = setup("/npc/merchant_down_1",gp.tileSize, gp.tileSize);
		up2 = setup("/npc/merchant_down_2",gp.tileSize, gp.tileSize);
		down1 = setup("/npc/merchant_down_1",gp.tileSize, gp.tileSize);
		down2 = setup("/npc/merchant_down_2",gp.tileSize, gp.tileSize);
		left1 = setup("/npc/merchant_down_1",gp.tileSize, gp.tileSize);
		left2 = setup("/npc/merchant_down_2",gp.tileSize, gp.tileSize);
		right1 = setup("/npc/merchant_down_1",gp.tileSize, gp.tileSize);
		right2 = setup("/npc/merchant_down_2",gp.tileSize, gp.tileSize);
		
		}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Welcome traveler, how may I help you\nAre you lost?!";
		dialogues[1][0] = "Come again HEHEHE";
		dialogues[2][0] = "We have all been there man...\n but one day you will have enough!";
		dialogues[3][0] = "No place to carry more...";
		dialogues[4][0] = "You cannot sell equipped item";

	}
	
	public void setItems() {
		
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		
	}
	
	public void speak() {
		facePlayer();
		gp.gameState = gp.tradeState;//when we speak to npc we call tradeState
		gp.ui.npc = this;//this way we can quickly acces this npc
	}
}
