package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import bubble.game.BubbleFrame;
import bubble.game.component.Bubble;
import bubble.game.component.Enemy;
import bubble.game.component.GameOver;
import bubble.game.component.Player;

//백그라운드에서 관찰
public class BackgroundPlayerService implements Runnable {
	
	private BubbleFrame mContext;
	
	private BufferedImage image;
	private Player player;
	private List<Bubble> bubbleList;
	private List<Enemy> enemyList;
	
	//플레이어, 버블
	public BackgroundPlayerService(Player player, BubbleFrame mContext) {
		this.mContext = mContext;
		this.player = player;
		this.bubbleList= player.getBubbleList();
		this.enemyList= player.getEnemyList();
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		while(true) {
			
			//1.버블충돌체크
			for(int i=0; i<bubbleList.size(); i++) {
				Bubble bubble = bubbleList.get(i);
				if(bubble.getState() ==1 ) {
					if((Math.abs(player.getX()-bubble.getX()) < 10) &&
					Math.abs(player.getY()-bubble.getY()) > 0 && Math.abs(player.getY()-bubble.getY()) < 50 ){
						System.out.println("적군 사살 완료");
						bubble.clearBubbled();
						break; 
					}
				}
			}
			
			//적군충돌체크
			for(int i=0; i<enemyList.size(); i++) {
				Enemy enemy = enemyList.get(i);
				if(enemy.getState() ==0  ) {
					if((Math.abs(player.getX()-enemy.getX()) < 10) &&
					Math.abs(player.getY()-enemy.getY()) > 0 && Math.abs(player.getY()-enemy.getY()) < 50 ){
						System.out.println("적군 충돌");
						
						mContext.remove(player);
						mContext.repaint();
						mContext.gameOver();
						mContext.refresh();
					
						break; 
					}
				}
			}
			
			//2.벽 충돌체크
			//색상확인
			Color leftColor = new Color(image.getRGB(player.getX()-10, player.getY()+25));
			Color rightColor = new Color(image.getRGB(player.getX()+50+15, player.getY()+25));
			int bottomColor = image.getRGB(player.getX()+20, player.getY()+50 +5) + 
							image.getRGB(player.getX()+50-20, player.getY()+50 +5);
			//System.out.println("바텀컬러"+ bottomColor);
		
			//바닥 충돌 확인
			if(bottomColor != -2) {
				//System.out.println("바닥에 충동함");
				player.setDown(false);
			} else {
				if(!player.isUp() && !player.isDown()) {
					player.down();
				}
			}
			
			//외벽충돌 확인
			if(leftColor.getRed() == 255 && leftColor.getGreen()==0 && leftColor.getBlue() == 0) {
				//System.out.println("왼쪽 충돌");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if(rightColor.getRed() == 255 && rightColor.getGreen()==0 && rightColor.getBlue() == 0) {
				player.setRightWallCrash(true);
				player.setRight(false);
				//System.out.println("오른쪽 충돌");
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
