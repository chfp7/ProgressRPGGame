package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity{

	Entity generator;//this is the entity that produces the particle
	Color color;
	int size;
	int xd;//like the directions of the particle
	int yd;//if i want top right direction it is xd=1 and yd = 1
	
	public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife,int xd, int yd) {
		super(gp);
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;
		
		life = maxLife;
		int offset = (gp.tileSize/2) - (size/2);//to center the particles
		worldX = generator.worldX + offset;//so location is whoever is generating the particle location 
		worldY = generator.worldY + offset;
	}
	
	public void update() {
		
		life--;
		worldX += xd*=speed;//nice diagonal effect 
		worldY += yd*=speed;
		
		if(life < maxLife/3) {
			yd++;// the direction is getting down when maxLife is at half (gravity effect)
		}
		if(life == 0) {
			alive = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);//we draw particle as rectangle but we can use drawImg

	}
	

}
