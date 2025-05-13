package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	//DAY AND NIGHT IMPLEMENTATION
	public int dayCounter;
	public float filterAlpha = 0f;//public so we can use it from the UI Class
	
	public final int day = 0;
	public final int dusk = 1;
	public final int night = 2;
	public final int dawn = 3;
	
	public int dayState = day;
	
public Lighting(GamePanel gp) { // revised one 
		
		this.gp = gp;
		setLightSource();
		
	}

	
//	public Lighting(GamePanel gp,int circleSize) {//circle: size of lighting area 
//		
//		//create a BuffImg
//		darknessFilter = new BufferedImage(gp.screenWidth,gp.screenHeight,BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();//everthing g2 draws will be recorded on darkness filter
//		//create a screen sized rectangle
//		Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth,gp.screenHeight));
//		
//		//get the center x and y of the light circle
//		int centerX = gp.player.screenX + (gp.tileSize/2);
//		int centerY = gp.player.screenY + (gp.tileSize/2);
//		//get the top left x and y of the circle 
//		double x = centerX - circleSize/2;
//		double y = centerY - circleSize/2;
//		
//		//CREATE A LIGHT CIRCLE SHAPE
//		Shape circleShape = new Ellipse2D.Double(x,y,circleSize,circleSize);
//		
//		//CREATE A LIGHT CIRCLE AREA
//		Area lightArea = new Area(circleShape);
//		
//		//Subtract circle area from the screen rectangle
//		screenArea.subtract(lightArea);
//		
//		//CREATE A GRADATION EFFECT (the further the darker)
//		Color color[] = new Color[5];//we can set any size as long as color and float have same nb
//		float fraction[] = new float[5];
//		
//		color[0] = new Color(0,0,0,0f);
//		color[1] = new Color(0,0,0,0.25f);
//		color[2] = new Color(0,0,0,0.5f);
//		color[3] = new Color(0,0,0,0.75f);
//		color[4] = new Color(0,0,0,0.95f);
//		
//		fraction[0] = 0f;
//		fraction[1] = 0.25f;
//		fraction[2] = 0.5f;
//		fraction[3] = 0.75f;
//		fraction[4] = 1f;
//		
//		//CREATE A GRADATION PAINT SETTINGS FOR THE LIGHT CIRCLE
//		RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,(circleSize/2),fraction,color);
//
//		//SET THE GRADIENT DATA ON G2
//		g2.setPaint(gPaint);
//		
//		//DRAW THE LIGHT CIRCLE
//		g2.fill(lightArea);
//		
////		//set a color (black) to draw we wont need this since we made a gradient one
////		g2.setColor(new Color(0,0,0,0.95f));
//		
//		//we draw the screen rectangle without the light circle area
//		g2.fill(screenArea);
//		
//		g2.dispose();
//		
//	}

	public void setLightSource() {
		// Create a buffered image
				darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
				
				if(gp.player.currentLight == null) {
					g2.setColor(new Color(0,0,0,0.98f)); // keep in mind we can change a lil bit the color ex: B:0.01 makes everything pretty
				}
				else {
					// Get the center x and y of the light circle
				int centerX = gp.player.screenX + (gp.tileSize)/2;
				int centerY = gp.player.screenY + (gp.tileSize)/2;
				
				// Create a gradation effect
				Color color[] = new Color[12];
				float fraction[] = new float[12];
				
				color[0] = new Color(0,0,0,0.1f);
				color[1] = new Color(0,0,0,0.42f);
				color[2] = new Color(0,0,0,0.52f);
				color[3] = new Color(0,0,0,0.61f);
				color[4] = new Color(0,0,0,0.69f);
				color[5] = new Color(0,0,0,0.76f);
				color[6] = new Color(0,0,0,0.82f);
				color[7] = new Color(0,0,0,0.87f);
				color[8] = new Color(0,0,0,0.91f);
				color[9] = new Color(0,0,0,0.94f);
				color[10] = new Color(0,0,0,0.96f);
				color[11] = new Color(0,0,0,0.98f);
				
				fraction[0] = 0f;
				fraction[1] = 0.4f;
				fraction[2] = 0.5f;
				fraction[3] = 0.6f;
				fraction[4] = 0.65f;
				fraction[5] = 0.7f;
				fraction[6] = 0.75f;
				fraction[7] = 0.8f;
				fraction[8] = 0.85f;
				fraction[9] = 0.9f;
				fraction[10] = 0.95f;
				fraction[11] = 1f;
				
				// Create a gradation paint settings
				RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (gp.player.currentLight.lightRadius/2), fraction, color);
				
				// Set the gradient data on g2
				g2.setPaint(gPaint);
			}
				g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

				g2.dispose();	
	}
	
	public void resetDay() {
		dayState = day;
		filterAlpha = 0f;
	}
	
	public void update() {
		
		if(gp.player.lightUpdated == true) {// when player equips and unequips we change
			setLightSource();
			gp.player.lightUpdated = false;
		}
		
		if(dayState == day) {
			
			dayCounter++;
			
			if(dayCounter > 3600) {//these determine the time
				dayState = dusk;
				dayCounter = 0;
			}
		}
		
		if(dayState == dusk) {
			filterAlpha += 0.0001f; //making screen darker over time, also these determine dusk time
			if(filterAlpha>1f) {
				filterAlpha = 1f;//if its more than 1 it returns error
				dayState = night;
			}
		}
		
		if(dayState == night) {
			dayCounter++;
			
			if(dayCounter>600) {
				dayState = dawn;//nighttime is over
				dayCounter = 0;
			}
		}
		
		if(dayState == dawn) {
			
			filterAlpha -= 0.001f; //making screen brighter over time
			
			if(filterAlpha < 0f) {
				filterAlpha = 0f;
				dayState = day;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		if(gp.currentArea == gp.outside) {//when we are outside we handle this over time (filterAlpha) 
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,filterAlpha));
		}
		if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {//dungeon has pure darkness
			g2.drawImage(darknessFilter, 0, 0, null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		//and indoors will not be affected since its in neither of these lighting effects
		//DEBUG 
		String situation = "";
		
		switch(dayState) {
		case 0: situation = "Day";break;
		case 1: situation = "Dusk";break;
		case 2: situation = "Night";break;
		case 3: situation = "Dawn";break;
		}
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(50f));
		g2.drawString(situation, 800, 500);
		
	}
	
	
}
