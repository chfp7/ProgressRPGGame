package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{
	
	public static final String objName = "Wood Shield"; //bug fix

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		
		type = typeShield;
		name = objName;
		down1 = setup("/objects/shield_wood",gp.tileSize,gp.tileSize);
		defenseValue = 1;
		description = "[" + name + "]" + "\nA good old shield";
	}

}
