package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
//Projectile is a subclass of entity, and fireball is sub of proj so proj is sub of entity
public class OBJ_Fireball extends Projectile{
	
	GamePanel gp;
	public static final String objName = "Fireball"; //bug fix

	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		knockBackPower = 0; // we dont need this since entity default is 0
		useCost = 1;
		alive = false;
		getImage();
	}

	private void getImage() {
		
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);

	}
	
	public boolean haveResource(Entity user) {//if player has mana, he is capable of firing
		boolean haveResource = false;//could be arrows, or bullets, but fireball needs mana
		
		if(user.mana>=useCost) {
			haveResource = true;
		}
		
		return haveResource;
	}
	
	//to subtract resource
	public void minusResource(Entity user) {
		user.mana -= useCost;
	}
	
	//we choose color size speed maxlife etc from each subclass
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxLife() {//determines how long are particles in the air
		int maxLife = 20;
		return maxLife;
	}
	
	
}
