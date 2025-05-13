package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Bat extends Entity{

GamePanel gp;
	
	public MON_Bat(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = typeMonster;// (monster)
		name = "Bat";
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxLife = 3;
		life = maxLife;
		attack = 3;
		defense = 0;
		xp = 2;//how much xp we get when we kill it 
		//projectile = new OBJ_Rock(gp); no projectile for now
		//HOW WE INSTANTIATE SOLID AREA OF ENTITY (obj npc monster)
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
		
	}
	
	public void getImage() {// to load and scale slime imgs
		
		up1 = setup("/monster/bat_down_1",gp.tileSize, gp.tileSize);
		up2 = setup("/monster/bat_down_2",gp.tileSize, gp.tileSize);
		down1 = setup("/monster/bat_down_1",gp.tileSize, gp.tileSize);
		down2 = setup("/monster/bat_down_2",gp.tileSize, gp.tileSize);
		left1 = setup("/monster/bat_down_1",gp.tileSize, gp.tileSize);
		left2 = setup("/monster/bat_down_2",gp.tileSize, gp.tileSize);
		right1 = setup("/monster/bat_down_1",gp.tileSize, gp.tileSize);
		right2 = setup("/monster/bat_down_2",gp.tileSize, gp.tileSize);
		
	}
	
	//deleted update method and replaced many methods to make them cleaner
	
	public void setAction() {
		
		
		if(onPath == true) {//monsters getting aggressive
			
//			checkStopChasingOrNot(gp.player, 10, 100);
//			
//		searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//		//shoot projectiles only when its angry
//		checkShootOrNot(100,60);//rate is 100 and shotInterval is 60
		}
		else {
			//checking when it starts chasing
			//checkStartChasingOrNot(gp.player,5,100);
			
			checkRandomDirection(30);
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		//direction = gp.player.direction; if monster is passive 
	//	onPath = true; //if monster is passive aggressive
		
	}
	
	public void checkDrop(){
		//cast a die, based on number we decide what to drop, if its a high level monster, good items will likely drop
		int i = new Random().nextInt(100)+1;
		
		//SET THE MONSTER DROP
		if(i<50) {
			dropItem(new OBJ_Coin_Bronze(gp));//50% chance
		}
		if(i>=50 && i<75) {
			dropItem(new OBJ_Heart(gp));//25% chance
		}
		if(i>75) {
			dropItem(new OBJ_ManaCrystal(gp));//25% chance
		}
		
	}
	
}
