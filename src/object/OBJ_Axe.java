package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{
	
	public static final String objName = "Axe";

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = typeAxe;
		name = objName;
		down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
		price = 20;
		attackValue = 1;
		knockBackPower = 2;
		attackArea.width = 30;// smaller attack range for an axe than a sword
		attackArea.height = 30;
		description = "[" + name + "]" + "\nA good old axe";
		
		motion_1_duration = 15;
		motion_2_duration = 45;
	}
	
	
}
