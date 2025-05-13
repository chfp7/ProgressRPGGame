package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{
	
	GamePanel gp;
	
	public static final String objName = "Boots";

	
public OBJ_Boots(GamePanel gp) {//we did this class, we want to add sounds with powering up
		
		super(gp);
		name = objName;
		price = 120;
		down1 = setup("/objects/boots",gp.tileSize, gp.tileSize);
		
		
	}

}
