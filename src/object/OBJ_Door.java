package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
	
	GamePanel gp;
	public static final String objName = "Door"; //bug fix

	
public OBJ_Door(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = typeObstacle; 
		name = objName;
		
		down1 = setup("/objects/door",gp.tileSize, gp.tileSize);
		
		collision = true;//since we want to make doors solid
		
		setDialogue();
		
	}

	public void setDialogue() {
		dialogues[0][0] = "you need a key";
	}

	public void interact() {
		
		startDialogue(this,0);
		
	}

}
