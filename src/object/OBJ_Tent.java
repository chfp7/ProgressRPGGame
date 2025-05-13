package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity{

	GamePanel gp;
	
	public static final String objName = "Tent"; //bug fix
	
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = typeConsumable;
		name = objName;
		down1 = setup("/objects/tent",gp.tileSize,gp.tileSize);
		description = "[Tent]\nYou can sleep until\nnext morning";
		price = 300;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		
		gp.gameState = gp.sleepState;
		//gp.playSE("SLEEP SOUND"); LATER
		//return true; if I want this to disappear after use
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		gp.player.getPlayerSleepingImage(down1);//tent image 
		return true;//to use it many times
	}

}
