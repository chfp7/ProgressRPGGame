package tile_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTile{
	GamePanel gp;
	
	public static String itName = "Metal Plate";//we need it to create the puzzle
	
	public IT_MetalPlate(GamePanel gp, int col, int row) {
		super(gp,col,row);
		this.gp = gp;
		
		name = itName;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		this.solidArea.height= 0;
		this.solidArea.width = 0;
		
		 
		down1 = setup("/tile_interactive/metalplate",gp.tileSize,gp.tileSize);
	}
}
