package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

	public class OBJ_Heart extends Entity{

		GamePanel gp;
		public static final String objName = "Heart"; //bug fix
	
	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		value = 2;
		type = typePickupOnly;
		name = objName;
		value = 2;
		down1 = setup("/objects/heart_full",gp.tileSize, gp.tileSize);
		//CHANGED (CHECK KEY)
		image = setup("/objects/heart_full",gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half",gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_blank",gp.tileSize, gp.tileSize);
		
		
	}
	
	public boolean use(Entity entity) {
		//gp.playSE("healed");implement sounds
		gp.ui.addMessage("Heart + " + value);
		gp.player.life += value;
		return true;
	}
	
}
