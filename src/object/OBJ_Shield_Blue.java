package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{
	
	public static final String objName = "Medieval Shield"; //bug fix
	
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		type = typeShield;
		name = objName;
		down1 = setup("/objects/shield_blue",gp.tileSize,gp.tileSize);
		defenseValue = 2;
		description = "[" + name + "]" + "\nA great shield!";
	}

}
