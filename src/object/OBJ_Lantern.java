package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity{

	GamePanel gp;
	public static final String objName = "Lantern"; //bug fix 
	
	public OBJ_Lantern(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = typeLight;
		name = objName;
		down1 = setup("/objects/lantern",gp.tileSize,gp.tileSize);
		description = "[Lantern]\nIlluminates your\nsurroundings";
		price = 200;
		lightRadius = 250;
	}
}
