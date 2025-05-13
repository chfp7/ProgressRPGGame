package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity{
	
	GamePanel gp;

	public static final String monName = "Skeleton Lord";
	
	public MON_SkeletonLord(GamePanel gp) {
		
		super(gp);
		
		this.gp = gp;
		
		type = typeMonster;// (monster)
		boss = true;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life = maxLife;
		attack = 10;
		defense = 2;
		xp = 50;//how much xp we get when we kill it 
		//HOW WE INSTANTIATE SOLID AREA OF ENTITY (obj npc monster)
		
		knockBackPower = 5;
		
		sleep = true; //(to disable all movement)
		
		int size = gp.tileSize *5;
		
		solidArea.x = 48;
		solidArea.y = 48;
		solidArea.width = size - (48 *2);
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 170;
		attackArea.height = 170;
		
		motion_1_duration = 35;
		
		motion_2_duration = 80;

		getImage();
		getAttackImage();
		setDialogue();
	}
	
	public void getImage() {// to load and scale slime imgs
		
		int i = 5; //5 TIMES BIGGER
		
		if(inRage == false) {
		
		up1 = setup("/monster/skeletonlord_up_1",gp.tileSize*i, gp.tileSize*i);
		up2 = setup("/monster/skeletonlord_up_2",gp.tileSize*i, gp.tileSize*i);
		down1 = setup("/monster/skeletonlord_down_1",gp.tileSize*i, gp.tileSize*i);
		down2 = setup("/monster/skeletonlord_down_2",gp.tileSize*i, gp.tileSize*i);
		left1 = setup("/monster/skeletonlord_left_1",gp.tileSize*i, gp.tileSize*i);
		left2 = setup("/monster/skeletonlord_left_2",gp.tileSize*i, gp.tileSize*i);
		right1 = setup("/monster/skeletonlord_right_1",gp.tileSize*i, gp.tileSize*i);
		right2 = setup("/monster/skeletonlord_right_2",gp.tileSize*i, gp.tileSize*i);
		}
		if(inRage == true) {
			up1 = setup("/monster/skeletonlord_phase2_up_1",gp.tileSize*i, gp.tileSize*i);
			up2 = setup("/monster/skeletonlord_phase2_up_2",gp.tileSize*i, gp.tileSize*i);
			down1 = setup("/monster/skeletonlord_phase2_down_1",gp.tileSize*i, gp.tileSize*i);
			down2 = setup("/monster/skeletonlord_phase2_down_2",gp.tileSize*i, gp.tileSize*i);
			left1 = setup("/monster/skeletonlord_phase2_left_1",gp.tileSize*i, gp.tileSize*i);
			left2 = setup("/monster/skeletonlord_phase2_left_2",gp.tileSize*i, gp.tileSize*i);
			right1 = setup("/monster/skeletonlord_phase2_right_1",gp.tileSize*i, gp.tileSize*i);
			right2 = setup("/monster/skeletonlord_phase2_right_2",gp.tileSize*i, gp.tileSize*i);
		}
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Nobody can enter!";
		dialogues[0][1] = "You want to steal my treasure I've hidden";
		dialogues[0][2] = "YOU WILL PAY!";
	}
	
	public void getAttackImage() {
		
		int i = 5;
			if(inRage == false) {
			attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*2*i);
			attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*2*i);
			attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*2*i);
			attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*2*i);
			attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.tileSize*2*i, gp.tileSize*i);
			attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.tileSize*2*i, gp.tileSize*i);
			attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.tileSize*2*i, gp.tileSize*i);
			attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.tileSize*2*i, gp.tileSize*i);
			}
			if(inRage == true) {
				attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*2*i);
				attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*2*i);
				attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*2*i);
				attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*2*i);
				attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gp.tileSize*2*i, gp.tileSize*i);
				attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gp.tileSize*2*i, gp.tileSize*i);
				attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gp.tileSize*2*i, gp.tileSize*i);
				attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gp.tileSize*2*i, gp.tileSize*i);
			}
			}
	
	//deleted update method and replaced many methods to make them cleaner
	
	public void setAction() {
		
		if(inRage == false && life < maxLife/2) {
			inRage = true;
			getImage();
			getAttackImage();
			defaultSpeed++;
			speed = defaultSpeed;
			attack += 5;
		}
		
		if(getTileDistance(gp.player)<10) {//monsters getting aggressive
		//the boss cannot use the normal monsters' AI of pathfinding
			moveTowardPlayer(60);
		}
		//else
		else {
			
			checkRandomDirection(120);
		}
		
		//check if it attacks
		if(attacking == false) {
			checkAttackOrNot(60,gp.tileSize*7,gp.tileSize*5); // very aggressive we pass smaller num on rate
		}
	}
	
	
	
	public void damageReaction() {
		actionLockCounter = 0;

		
	}
	
	public void checkDrop(){
		
		gp.bossBattleOn = false;
		Progress.skeletonLordDefeated = true;
		//restore prev music
		//gp.stopMusic();
		//gp.playMusic(dungeonmusic);LATER
		
		for(int i = 0; i<gp.obj[1].length ; i++) {
			if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name == OBJ_Door_Iron.objName) {
				gp.obj[gp.currentMap][i] = null;
			}
		}
		
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
