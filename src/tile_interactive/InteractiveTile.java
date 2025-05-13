package tile_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;


public class InteractiveTile extends Entity{
	
GamePanel gp;
public boolean destructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		
		return tile;
	}
	
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		return isCorrectItem;
	}
	public void update() {
		
		if(invincible == true) {//we are limiting invincible time to approx 0.3 second
			invincibleCount++;
			
			if(invincibleCount>20) {
				invincible = false;
				invincibleCount = 0;
			}
		}
	}
	
	//if you like the other transparent damage effect comment this out but we implemented 
	//this to avoid it (we just override the method with our own rules to not mess up entity
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
		&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
			
		
		g2.drawImage(down1, screenX, screenY,null);	
	//	changeAlpha(g2,1f);
		}
		
	}

}
