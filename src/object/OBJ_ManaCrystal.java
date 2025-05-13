package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity{

	GamePanel gp;
	public static final String objName = "Mana"; //bug fix
	
public OBJ_ManaCrystal(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		name = objName;
		type = typePickupOnly;
		value = 1;
		
		down1 = setup("/objects/manacrystal_full",gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full",gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank",gp.tileSize, gp.tileSize);
		
	}

	public boolean use(Entity entity) {
	//gp.playSE("healed");implement sounds
	gp.ui.addMessage("Heart + " + value);
	gp.player.mana += value;
	return true;
	}
	
}
