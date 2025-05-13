package entity;

import main.GamePanel;

public class Projectile extends Entity{

	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
		
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
		
	}
	
	public void update() {
		
		if(user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex,this,1+attack*(gp.player.level/2),knockBackPower);
				generateParticle(user.projectile,gp.monster[gp.currentMap][monsterIndex]);
				alive = false;//if projectile hits monster, it dies (disappears)
			}
		}
		if(user != gp.player) {
			//checking if player has contact with projectile
			boolean contactPlayer =gp.cChecker.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				generateParticle(user.projectile,gp.player);
				alive = false;
			}
			
		}
		
		switch(direction) {
		case "up": worldY-=speed;break;
		case "down": worldY+=speed;break;
		case "left": worldX-=speed;break;
		case "right": worldX+=speed;break;	
		}
		
		life--;
		if(life<=0) {//when we shoot a projectile it gradually loses its life, when 0 it disappears
			alive = false;
		}
		
		spriteCounter++;
		if(spriteCounter>10) { //I can adjust the speed here (10 to 30) slower
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
		   spriteCounter = 0; //not putting "else" since I just want to reset it
		}
		
	}
	
	public boolean haveResource(Entity user) {//if player has mana, he is capable of firing
		boolean haveResource = false;//could be arrows, or bullets, but fireball needs mana
		return haveResource;
	}
	public void minusResource(Entity user) {}

}
