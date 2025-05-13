package main;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	
	public static JFrame window = new JFrame(); //creating a window

	
	public static void main(String[] args) {
		//JFrame window = new JFrame(); //creating a window (testing now) (it works)
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //so we can exit the program
		window.setResizable(false); //so we cant resize the window
		window.setTitle("Progress");
		new Main().setIcon();
				
		GamePanel gamepanel = new GamePanel(); //we create the panel using OOP
		window.add(gamepanel); // add panel to window
		
		gamepanel.config.loadConfig();
		
		if(gamepanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}
		
		window.pack(); // so we pack and finish (Now we see the window)
		
		window.setLocationRelativeTo(null); //so location is at the center of screen
		window.setVisible(true);
		
		gamepanel.setUpGame(); //adds up important stuff to the game
		
		gamepanel.startGameThread(); //keeps the game running on the screen
	}
	
	public void setIcon() {
		try (InputStream is = getClass().getResourceAsStream("/player/boy_down_1.png")) {
		    if (is == null) {
		        System.out.println("Image not found!");
		    } else {
		        ImageIcon icon = new ImageIcon(ImageIO.read(is));
		        window.setIconImage(icon.getImage());
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}

}
