package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	public static final String objName = "Healing Potion"; //bug fix

	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = typeConsumable;
		name = objName;
		value = 2;
		down1 = setup("/objects/potion_red",gp.tileSize,gp.tileSize);
		description = "[" + name + "]" + "\nConsume to feel better";
		price = 25;
		stackable = true;
		
		setDialogue();

	}
	
	public void setDialogue() {
		dialogues[0][0] = "You drink the " + name + "!\n" + "Your life has been recovered by " + value;
		
	}
	
	public boolean use(Entity entity) {
		
		startDialogue(this,0);
		entity.life += value;
		//gp.playSE(HEALING); when I implement sounds
		return true;
	}
	
}
