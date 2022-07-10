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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BubbleFrame extends JFrame {
	
	private BubbleFrame mContext = this;
	
	private JLabel backgroundMap;
	private Player player;
	private List<Enemy> enemyList;
	private String direction;
	private GameOver gameOver;
	
	private BGM bgm;

	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}
	
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			
			//키보드 클릭이벤트
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:	
					if(!player.isLeft() && !player.isLeftWallCrash()) {
						player.left();						
					}
					break;

				case KeyEvent.VK_RIGHT:
					if(!player.isRight() && !player.isRightWallCrash()) {
						player.right();						
					}
					break;
					
				case KeyEvent.VK_UP:	
					if(!player.isUp() && !player.isDown()) {
						player.up();
					}
					break;
					
				case KeyEvent.VK_SPACE:
//					Bubble bubble  = new Bubble(mContext);
//					add(bubble);
					player.attack();
					break;
					
				}
			}
			
			//키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
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

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		
		mContext.setDirection("left");
		Enemy enemy = new Enemy(mContext);
		enemy.setX(280);
		
		mContext.setDirection("right");
		Enemy enemy2 = new Enemy(mContext);
		
		enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);
		enemyList.add(enemy2);
		
		for(Enemy e : enemyList) {			
			add(e);
		}
		
		player = new Player(mContext);
		add(player);

		bgm = new BGM();
		
		//backgroundMap.setLocation(300, 300);
		//backgroundMap.setSize(1000, 600);
		//add(backgroundMap); //jframe에 jlabel이 그려진다.
		
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); //absoulte 레이아웃을 자유롭게
		setLocationRelativeTo(null); //jframe을 가운데로
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창을 끌때 jvm도 같이 종료
	}
	
	public void gameOver() {
		gameOver = new GameOver();
		mContext.add(gameOver);
		bgm.gameOverBGM();
    }
    
    public void refresh() {
        validate();
        repaint();
    }
	
	
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
