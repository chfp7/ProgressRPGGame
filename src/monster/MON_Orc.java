package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Orc extends Entity{
	
	GamePanel gp;
	
	public MON_Orc(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = typeMonster;// (monster)
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 8;
		defense = 2;
		xp = 5;//how much xp we get when we kill it 
		//HOW WE INSTANTIATE SOLID AREA OF ENTITY (obj npc monster)
		
		knockBackPower = 5;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		
		motion_1_duration = 35;
		
		motion_2_duration = 80;

		getImage();
		getAttackImage();
	}
	
	public void getImage() {// to load and scale slime imgs
		
		up1 = setup("/monster/orc_up_1",gp.tileSize, gp.tileSize);
		up2 = setup("/monster/orc_up_2",gp.tileSize, gp.tileSize);
		down1 = setup("/monster/orc_down_1",gp.tileSize, gp.tileSize);
		down2 = setup("/monster/orc_down_2",gp.tileSize, gp.tileSize);
		left1 = setup("/monster/orc_left_1",gp.tileSize, gp.tileSize);
		left2 = setup("/monster/orc_left_2",gp.tileSize, gp.tileSize);
		right1 = setup("/monster/orc_right_1",gp.tileSize, gp.tileSize);
		right2 = setup("/monster/orc_right_2",gp.tileSize, gp.tileSize);
		
	}
	
	public void getAttackImage() {
			attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	
	//deleted update method and replaced many methods to make them cleaner
	
	public void setAction() {
		
		
		if(onPath == true) {//monsters getting aggressive
			
			checkStopChasingOrNot(gp.player, 10, 100);
			
		searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		//shoot projectiles only when its angry

		}
		//else
		else {
			//checking when it starts chasing
			checkStartChasingOrNot(gp.player,5,100);
			
			checkRandomDirection(120);
		}
		
		//check if it attacks
		if(attacking == false) {
			checkAttackOrNot(30,gp.tileSize*4,gp.tileSize); // very aggressive we pass smaller num on rate
		}
	}
	
	
	
	public void damageReaction() {
		actionLockCounter = 0;
		//direction = gp.player.direction; if monster is passive 
		onPath = true; //if monster is passive aggressive
		
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
