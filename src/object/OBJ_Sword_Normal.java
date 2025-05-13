package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
	
	public static final String objName = "Normal Sword"; //bug fix

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		
		type = typeSword;
		name = objName;
		durability = 100;
		down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
		attackValue = 1;
		knockBackPower = 4;
		attackArea.width = 36;// a little smaller than tileSize (48)
		attackArea.height = 36;
		description = "[" + name + "]" + "\nA good old sword";
		
		motion_1_duration = 5;
		motion_2_duration = 25;
	}

}
