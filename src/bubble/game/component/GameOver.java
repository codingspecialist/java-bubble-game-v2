package bubble.game.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOver extends JLabel{
	
	// 의존성 콤포지션
	private BubbleFrame mContext;
	
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean down;

	private ImageIcon gameOver;



	public GameOver(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
	}
	
	private void initObject() {
		gameOver = new ImageIcon("image/GameOverText.png");
	}
	
	private void initSetting() {
		down = false;
		
		x = 150;
		y = 150;
		
		setIcon(gameOver);
		setSize(700,187);
		setLocation(20,40);
		
	}



}
