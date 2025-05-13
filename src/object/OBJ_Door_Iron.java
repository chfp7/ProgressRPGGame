package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity{
	
	GamePanel gp;
	
	public static final String objName = "Iron Door"; //bug fix

	
public OBJ_Door_Iron(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = typeObstacle;
		name = objName;
		
		down1 = setup("/objects/door_iron",gp.tileSize, gp.tileSize);
		
		collision = true;//since we want to make doors solid
		
		setDialogue();
		
	}

	public void setDialogue() {
		dialogues[0][0] = "It wont budge";
	}

	public void interact() {
		
		startDialogue(this,0);
		
	}
}
