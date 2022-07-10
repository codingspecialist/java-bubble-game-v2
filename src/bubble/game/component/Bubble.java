package bubble.game.component;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Moveable;
import bubble.game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {
	
	//의존성 콤포지션
	private BubbleFrame mContext;
	private Player player;
	private List<Enemy> enemy;
	private BackgroundBubbleService backgroundBubbleService;
	
	//위치상태
	private int x;
	private int y;
	
	//움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	
	//적군을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)
	
	private ImageIcon bubble; //물방울
 	private ImageIcon bubbled; //가둔 물방울
 	private ImageIcon bomb; //물방울이 터진 상태
 
 
 	public Bubble(BubbleFrame mContext) {
 		this.mContext = mContext;
 		this.player = mContext.getPlayer();
 		this.enemy   = mContext.getEnemyList();
 		initObject();
 		initSetting();
 	}
	
	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");	
		
		backgroundBubbleService = new BackgroundBubbleService(this);
	}


	private void initSetting() {
		left = false;
		right = false;
		up = false;
		
		x = player.getX();
		y = player.getY();
		setIcon(bubble);
		setSize(50, 50);
		
		state = 0;
	}

	@Override
	public void left() {
		left = true;
		
		for(int i=0; i<400; i++) {
			x--;
			setLocation(x,y);
			
			if(backgroundBubbleService.leftWall()) {
				left = false;
				break;
			}
			
			
			for(Enemy e : enemy) {
				if((Math.abs(x - e.getX()) < 10) && 
						Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50 ) {
					if(e.getState()==0) {
						attack(e);
						break;
					}
				}				
			}
			
			if(state == 1) break;

			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for(int i=0; i<400; i++) {
			x++;
			setLocation(x,y);
			
			if(backgroundBubbleService.righttWall()) {
				right = false;
				break;
			}
			
			for(Enemy e : enemy) {
				if((Math.abs(x - e.getX()) < 10) && 
						Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50 ) {
					if(e.getState()==0) {
						attack(e);
						break;
					}
					
				}
			}

			if(state == 1) break;

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void up() {
		up = true;
		while(up) {
			y--;
			setLocation(x,y);
			
			if(backgroundBubbleService.topWall()) {
				up = false;
				break;
			}
			
			try {
				if(state == 0) { //기본물방울				 
					Thread.sleep(1);
				} else { //적을 가둔 물방울
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(state == 0) clearBubble(); //천장에 버블이 도착하고 3초후에 메모리에서 소멸
	}
	
	@Override
	public void attack(Enemy e) {
		state = 1;
		e.setState(1);
		setIcon(bubbled);
		mContext.remove(e); //메모리에서 사라지게한다.
		mContext.repaint();
		
	}
	
	private void clearBubble() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(3000);
			mContext.remove(this); //BubbleFrame의 bubble이 메모리에서 소멸
			mContext.repaint(); //BubbleFrame의 전체를 다시 그린다.(메모리에 있는것만!)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearBubbled() {
		new Thread(()-> {
			try {
				up = false;
				setIcon(bomb);
				Thread.sleep(1000);
				mContext.remove(this);
				mContext.repaint();
				
			} catch (Exception e) {
				// TODO: handle exception
			}			
		}).start();
		
	}
}
