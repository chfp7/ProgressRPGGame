package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

	GamePanel gp;
	
	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp,col,row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		this.solidArea.height= 0;
		this.solidArea.width = 0;
		
		 
		down1 = setup("/tile_interactive/trunk",gp.tileSize,gp.tileSize);
	}
	
}
