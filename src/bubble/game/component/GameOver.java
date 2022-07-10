package bubble.game.component;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.state.PlayerDirection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOver extends JLabel {

	private int x;
	private int y;
	
	private ImageIcon image;
	
	public GameOver() {
		initObject();
		initSetting();
	}
	
	private void initObject() {
		image = new ImageIcon("image/gameOver.png");
	}
	
	private void initSetting() {
		x = 0;
		y = 0;

		setIcon(image);
		setSize(1000, 640);
		setLocation(x, y);
	}
	
}
