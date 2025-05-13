package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
//each new object needs a new class to keep everything organized
public class OBJ_Key extends Entity{
	
	//we deleted SuperObject and many things in part21
	
	GamePanel gp;
	public static final String objName = "Key"; //bug fix after part 51

	
	public OBJ_Key(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		name = objName;
		type = typeConsumable;
		down1 = setup("/objects/key",gp.tileSize, gp.tileSize);
		description = "[" + name + "]" + "\nYou need to open doors \nin life";
		price = 100;
		stackable = true;
		
		
//		try {We dont need this anymore since now we do scaling using Entity's setup method
//			
//			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
//
//			uTool.scaleImage(image, gp.tileSize, gp.tileSize);//new scaling (part 11)
//			
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
		
		setDialogue();
		
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You used the key to open this door";
		
		dialogues[1][0] = "What are you doing?";
	}
	
	public boolean use(Entity entity) {
		
		int objIndex = getDetected(entity,gp.obj,"Door");
		
		if(objIndex != 999) {
			startDialogue(this, 0);
			gp.playSE(3);//door opens sound
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			startDialogue(this, 1);
			return false;
		}
		
	}

}
