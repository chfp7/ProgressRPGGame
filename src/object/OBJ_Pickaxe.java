package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity{
	//when you add new objects remember to put them on eGenerator
	public static final String objName = "Pickaxe";

	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);
		
		type = typePickaxe;
		name = objName;
		down1 = setup("/objects/pickaxe",gp.tileSize,gp.tileSize);
		price = 20;
		attackValue = 1;
		knockBackPower = 1;
		attackArea.width = 30;// smaller attack range for an axe than a sword
		attackArea.height = 30;
		description = "[" + name + "]" + "\nA good old pickaxe";
		
		motion_1_duration = 20;
		motion_2_duration = 55;
	}
	
	
}
