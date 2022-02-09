package bubble.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.game.component.Enemy;
import bubble.game.component.GameOver;
import bubble.game.component.Player;
import bubble.game.music.BGM;
import bubble.game.state.EnemyWay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BubbleFrame extends JFrame {
 
	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	private List<Enemy> enemys; // 컬렉션으로 관리
	private BGM bgm;
	private GameOver gameOver;

	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player(mContext);
		add(player);
		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(mContext, EnemyWay.RIGHT));
		enemys.add(new Enemy(mContext, EnemyWay.LEFT));
		for(Enemy e : enemys) add(e);
		bgm = new BGM();
		bgm.playBGM("bgm.wav");
	}

	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); // absoulte 레이아웃 (자유롭게 그림을 그릴 수 있다)
		setLocationRelativeTo(null); // JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료하기
	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {

			// 키보드 클릭 이벤트 핸들러
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT: 
					if (!player.isLeft() && !player.isLeftWallCrash() && player.getState() == 0)
						player.left();
					break;
				case KeyEvent.VK_RIGHT:
					if (!player.isRight() && !player.isRightWallCrash() && player.getState() == 0)
						player.right();
					break;
				case KeyEvent.VK_UP:
					if(!player.isUp() && !player.isDown() && player.getState() == 0)
						player.up();
					break;
				case KeyEvent.VK_SPACE:
					if(player.getState() == 0)
						player.attack();
					break;
				}
			}

			// 키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}

		});
	}

	public static void main(String[] args) {
		new BubbleFrame();
	}
}
