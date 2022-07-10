package bubble.game.music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BGM {
	
	Clip clip;
	AudioInputStream ais;
	
	public BGM() {
		try {
			ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
			clip = AudioSystem.getClip();
			clip.open(ais);
			//소리설정
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			//볼륨조절
			gainControl.setValue(-20.0f);
			clip.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameOverBGM() {
		clip.close();
		try {
			ais = AudioSystem.getAudioInputStream(
				        new File("sound/gameOver.wav").getAbsoluteFile());
			clip.open(ais);
			clip.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//offBGM();
	}

	private void offBGM() {
		clip.close();
	}
}
