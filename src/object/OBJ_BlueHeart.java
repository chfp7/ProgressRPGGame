package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity{
	
	GamePanel gp;
	public static final String objName = "Blue Heart";
	
	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = typePickupOnly;
		name = objName;
		down1 = setup("/objects/blueheart",gp.tileSize,gp.tileSize);
		
		setDialogues();
	}
	
	public void setDialogues() {
		
		dialogues[0][0] = "You pick up a beautiful blue gem.";
		dialogues[0][1] = "You have found the One Piece";
		
	}
	
	public boolean use(Entity entity) {
		gp.gameState = gp.cutsceneState;
		gp.cManager.sceneNum = gp.cManager.ending;
		return true;
	}

}
