package bubble.game.component;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Moveable;
import bubble.game.service.BackgroundEnemyService;
import bubble.game.state.EnemyDirection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {
	
	private BubbleFrame mContext;
	
	//위치상태
	private int x;
	private int y;
	
	// 작군의 방향
	private EnemyDirection ed;
	
	//움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//플레이어 속도 상태
	private final int SPEED = 3;
	private final int JUMPSPEED = 1;
	
	private int state; //0(살아있는 상태), 1(갇힌 상태)
	
	private boolean bottom;
	
	
	private ImageIcon enemyR, enemyL;

	public Enemy(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
		
		if(mContext.getDirection() == "left") {
			left();
		}else {
			right();			
		}
	}

	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	private void initSetting() {
		x = 480;
		y = 178;

		left = false;
		right = false;
		up = false;
		down = false;
	
		state = 0;
		
		bottom = false;
		
		if(mContext.getDirection() == "left") {
			ed = EnemyDirection.LEFT;
		}else {
			ed = EnemyDirection.RIGHT;			
		}
		setIcon(enemyR);
		setSize(50, 50);
		setLocation(x, y);
		
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		//System.out.println("left");
		ed = EnemyDirection.LEFT;
		left = true;
		new Thread(()-> {
			while(left) {
				setIcon(enemyL);
				x = x-SPEED;
				setLocation(x, y);		
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		//System.out.println("right");
		ed = EnemyDirection.RIGHT;
		right=true;
		new Thread(()-> {
			while(right) {
				setIcon(enemyR);
				x = x+SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}).start();
	}

	//left+up, right+up
	@Override
	public void up() {
		// TODO Auto-generated method stub
		//System.out.println("up");
		up = true;
		new Thread(()->{
			for(int i=0; i<130/JUMPSPEED; i++) {
				y = y-JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up=false;
			down();
			
		}).start();
		
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		//System.out.println("down");
		down=true;
		new Thread(()->{
			while(down) {
				y = y+JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down=false;
		}).start();

	}
}
