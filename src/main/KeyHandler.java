package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{//we need to implement keyListener
	
	GamePanel gp;
	//if true we are pressing those keys.
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,shootPressed,
	spacePressed;
	boolean showDebugText = false; // for debug
	public boolean superModeOn = false;//debug to not die
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//we wont use this one
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Moving Keys
		int code = e.getKeyCode(); //each key has a keycode
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
		
			titleState(code);
			
		}
		
		//PLAY STATE
		else if(gp.gameState == gp.playState) { // these keys only work in playState
		
			playState(code);
		
		}
		
		//PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			
			pauseState(code);
			
		}
		
		//DIALOGUE STATE OR CUTSCENE STATE
		else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
			
			dialogueState(code);
			
		}
		
		//CHARACTER STATE
		else if(gp.gameState == gp.characterState) {
			
			characterState(code);
			
		}
		
		//OPTIONS STATE
		else if(gp.gameState == gp.optionsState) {
					
			optionsState(code);
					
		}
		else if(gp.gameState == gp.gameOverState) {
			
			gameOverState(code);
					
		}
		else if(gp.gameState == gp.tradeState) {
			
			tradeState(code);
					
		}
		else if(gp.gameState == gp.mapState) {
			
			mapState(code);
					
		}
		
	}
	

	public void titleState(int code) {
		
		if(gp.ui.titleScreenState == 0) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>2) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_ENTER) {//if we press enter on play, playState
			if(gp.ui.commandNum == 0) {
				gp.ui.titleScreenState = 1;
				//gp.playMusic(0); I hate this music, when I change uncomment
			}
			if(gp.ui.commandNum == 1) {
				
				gp.gameState = gp.playState;
				gp.sLoad.load();
			}
			if(gp.ui.commandNum == 2) {//if we press enter on quit, game closes
				System.exit(0);
			}
		}
		
	}
		
		else if(gp.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum>3) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {//if we press enter on play, playState
				if(gp.ui.commandNum == 0) {//we will do fighter specific stats
					System.out.println("A FIGHTER!");
					gp.gameState = gp.playState;
					gp.player.dexterity = 2;
					gp.player.strength = 2;
					//gp.playMusic(0); I hate this music, when I change uncomment
				}
				if(gp.ui.commandNum == 1) {//player chose thief
					System.out.println("A thief...");
					gp.gameState = gp.playState;
					gp.player.defaultSpeed = 2;
					//gp.playMusic(0); I hate this music, when I change uncomment
				}
				if(gp.ui.commandNum == 2) {//player is wizard		
					System.out.println("A WIZARD OF OZZ");
					gp.gameState = gp.playState;
					gp.player.maxMana = 5;
					gp.playMusic(0);// I hate this music, when I change uncomment
					//make sure music changes from different classes
				}
				if(gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
				}
			}
		}
			
		
	}
	public void playState(int code) {
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {//if 
				gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(code == KeyEvent.VK_F) {
			shootPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.mapState;
		}
		if(code == KeyEvent.VK_X) {
			if(gp.map.miniMapOn == false) {
				gp.map.miniMapOn = true;
			}
			else {
				gp.map.miniMapOn = false;
			}
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
		
		//DEBUGKey is T, this shows how much the game is optimized
		if(code == KeyEvent.VK_T) {
			
			if(showDebugText == false) {
				showDebugText = true;
			}
			
			else if(showDebugText == true) {
				showDebugText = false;
				}
			
		}
		if(code == KeyEvent.VK_R) {
			switch(gp.currentMap) {
			case 0 : gp.tileM.loadMap("/maps/worldV3.txt",0);break;
			case 1 : gp.tileM.loadMap("/maps/interior01.txt",0);break;
//			case 2 : gp.tileM.loadMap("/maps/worldV2.txt",0);break;
//			case 3 : gp.tileM.loadMap("/maps/worldV2.txt",0);break;
//			case 4 : gp.tileM.loadMap("/maps/worldV2.txt",0);break;
//			case 5 : gp.tileM.loadMap("/maps/worldV2.txt",0);break;
//			case 6 : gp.tileM.loadMap("/maps/worldV2.txt",0);break;

			}
			}
		if(code == KeyEvent.VK_G) {
			if(superModeOn == false) {
				superModeOn = true;
			}
			
			else if(superModeOn == true) {
				superModeOn = false;
				}
		}
	}
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			
			gp.gameState = gp.playState;
	
	}
	}
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			
			enterPressed = true;
			
		}
	}
	public void characterState(int code) {
		
		if(code == KeyEvent.VK_C) {
			
			gp.gameState = gp.playState;
			
		}
		//moved to method (playerInventory()) (WASD)
	
		if(code == KeyEvent.VK_ENTER) {//when we press enter item gets selected
			gp.player.selectItem();		
			}
		
		playerInventory(code); //called method here 
		
	}
	
	public void optionsState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0:maxCommandNum = 5;break;
		case 3:maxCommandNum = 1;break;
		}
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			//gp.playSE("cursor sound"); later
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_A) {
			
			if(gp.ui.subState ==0) {
				if(gp.ui.commandNum==1 && gp.music.volumeScale>0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					//gp.playSE("Volume change"); later implementation
				}
				if(gp.ui.commandNum==2 && gp.se.volumeScale>0) {
					gp.se.volumeScale--;
					//gp.playSE("Volume change"); later implementation
				}
			}
			
		}
		if(code == KeyEvent.VK_D) {
			
			if(gp.ui.subState ==0) {
				if(gp.ui.commandNum==1 && gp.music.volumeScale<5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();//only for music because its already being played
					//gp.playSE("Volume change"); later implementation
				}
				if(gp.ui.commandNum==2 && gp.se.volumeScale<5) {
					gp.se.volumeScale++;
					//gp.playSE("Volume change"); later implementation
				}
			}
			
		}
		
	}
	
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			//gp.playSE("cursor sound"); later
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum = 1;
			}
			//gp.playSE("Something");
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>1) {
				gp.ui.commandNum = 0;
			}
			//gp.playSE("Something");
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0){
				gp.gameState = gp.playState;
				gp.resetGame(false);//will modify this in the future
			//	gp.stopMusic(); to stop death music
			//	gp.playMusic("alive music")); we might hold an array for music later (method)
			}
			if(gp.ui.commandNum ==1) {
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0; 
				gp.resetGame(true); //will modify this in the future
			}
		}
		
	}
	
	public void tradeState(int code) {
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(gp.ui.subState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum = 2;
				}
			//	gp.playSE(9);//scrolling SE LATER
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum>2) {
					gp.ui.commandNum = 0;
				}
			//	gp.playSE(9);//scrolling SE LATER
			}
		}
		
		if(gp.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if(gp.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		
		
		
	}
	
	public void mapState(int code) {
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	
	public void playerInventory(int code) {
		
		if(code == KeyEvent.VK_W) {//if row is b/w 0 and 3 its ok and col b/w 0 and 4
			if(gp.ui.playerSlotRow != 0) {
			gp.ui.playerSlotRow--;
			//gp.playSE(cursor) maybe index 9 
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.playerSlotCol != 0) {
			gp.ui.playerSlotCol--;
			//gp.playSE(cursor)
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.playerSlotRow != 3) { //depends on the row size
			gp.ui.playerSlotRow++;
			//gp.playSE(cursor)
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.playerSlotCol != 4) {//depends on col size
			gp.ui.playerSlotCol++;
			//gp.playSE(cursor) this will be a cursor (hover) sound effect
			}
		}
	}
	
	public void npcInventory(int code) {
		
		if(code == KeyEvent.VK_W) {//if row is b/w 0 and 3 its ok and col b/w 0 and 4
			if(gp.ui.npcSlotRow != 0) {
			gp.ui.npcSlotRow--;
			//gp.playSE(cursor) maybe index 9 
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.npcSlotCol != 0) {
			gp.ui.npcSlotCol--;
			//gp.playSE(cursor)
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.npcSlotRow != 3) { //depends on the row size
			gp.ui.npcSlotRow++;
			//gp.playSE(cursor)
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.npcSlotCol != 4) {//depends on col size
			gp.ui.npcSlotCol++;
			//gp.playSE(cursor) this will be a cursor (hover) sound effect
			}
		}
		
	}
	
	
	

	@Override
	public void keyReleased(KeyEvent e) {
		//if we release the key
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_F) {
			shootPressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		
	}

}
