package bubble.game.music;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BGM {
	public String bgmFolder = "sound";
	public Clip clip;
	public boolean isPlayed = true;

	public void stopBGM(){
		if(clip != null){
			clip.stop();
			System.out.println("음악이 멈추었습니다.");
		}
	}

	public void playBGM(String bgmName){
		if(!isPlayed){
			return;
		}

		stopBGM();
		try{
			File bgmPath = new File(bgmFolder + '/' + bgmName);
			if(bgmPath.exists()){
				AudioInputStream ais = AudioSystem.getAudioInputStream(bgmPath);
				clip = AudioSystem.getClip();
				clip.open(ais);

				// 소리 설정
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
				// 볼륨 조정
				gainControl.setValue(-30.0f);

				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			}else{
				System.out.println("bgm file isn't exist!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
