package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;// We use this to open audio files
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {//here we help instantiate new sounds
		
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
		soundURL[2] = getClass().getResource("/sound/powerup.wav");
		soundURL[3] = getClass().getResource("/sound/unlock.wav");
		soundURL[4] = getClass().getResource("/sound/fanfare.wav");
		soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[6] = getClass().getResource("/sound/recievedamage.wav");
		soundURL[7] = getClass().getResource("/sound/swingweapon.wav");
		soundURL[8] = getClass().getResource("/sound/burning.wav");
	//	soundURL[8] = getClass().getResource("/sound/burning.wav"); sound for cutting tree

		
		
		
	}
	
	public void setFile(int i) {
		
		try {
			//format to open audio files in Java
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);//to gain control to change the volume
			checkVolume();

			
		}catch(Exception e){
			
		}
			
	}
		
	
	public void play() {
		
		clip.start();
		
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	
	public void stop() {
		
		clip.stop();
		
	}
	//FloatControl accepts numbers from -80f to 6f so -80 means no sound and 6f is max vol
	//but you barely hear anything once the number becomes -30, so there isnt much diff b/w
	//-30 and -70. (less difference the lower the number)
	public void checkVolume() {
		
		switch(volumeScale) {//the volume now changes almost proportionally 
		case 0: volume = -80f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f;break;
		case 3: volume = -5f;break;
		case 4: volume = 1f; break;
		case 5: volume = 6f; break;
		}
		fc.setValue(volume);
		
	}

}
