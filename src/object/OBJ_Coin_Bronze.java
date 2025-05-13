package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{

	GamePanel gp;
	public static final String objName = "Bronze Coin"; //bug fix
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = typePickupOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/coin_bronze",gp.tileSize, gp.tileSize);

	}
	
	public boolean use(Entity entity) {
		//gp.playSE("coin use sound");implement sounds
		gp.ui.addMessage("Coin + " + value);
		gp.player.coin += value;
		return true;
	}

}
