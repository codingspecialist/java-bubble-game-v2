package bubble.game.service;

import bubble.game.component.Enemy;
import bubble.game.state.EnemyWay;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundEnemyService implements Runnable {

    private BufferedImage image;
    private Enemy enemy;

    public BackgroundEnemyService(Enemy enemy) {
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.enemy = enemy;
    }

    @Override
    public void run() {
        while(enemy.getState() == 0) {
            Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25 ));
            Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15 , enemy.getY() + 25 ));

            // 왼쪽 벽에 닿았을때
            if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                enemy.setLeft(false);

                // 오른쪽이동, 점프 중 한가지
                if(!enemy.isLeftWall()) {
                    int move = (int)(Math.random() * 3);
                     if(move == 0) {
                        if(!enemy.isRight()) {
                            enemy.right();
                        }
                    } else {
                        if(!enemy.isUp() && !enemy.isDown()) {
                            enemy.setLeftWall(true);
                            enemy.up();
                        }
                    }
                }
            // 오른쪽 벽에 닿았을때
            } else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                enemy.setRight(false);

                // 왼쪽이동, 점프 중 한가지
                if(!enemy.isRightWall()) {
                    int move = (int)(Math.random() * 3);
                    if(move == 0) {
                        if(!enemy.isLeft()) {
                            enemy.left();
                        }
                    } else {
                        if(!enemy.isUp() && !enemy.isDown()) {
                            enemy.setRightWall(true);
                            enemy.up();
                        }
                    }
                }
            }


            int bottomColor = image.getRGB(enemy.getX() + 15, enemy.getY() + 50 + 5 )
                        + image.getRGB(enemy.getX() + 50 - 15, enemy.getY() + 50 + 5 );

            if(bottomColor != -2) {
                enemy.setDown(false);
            } else {
                if(!enemy.isUp() && !enemy.isDown()) {
                    enemy.down();
                }
            }

            int LeftBottomColor = image.getRGB(enemy.getX() - 15, enemy.getY() + 50 + 5 ) ;
            if(enemy.getEnemyWay() == EnemyWay.LEFT && LeftBottomColor == -1) {
                if(!enemy.isUp() && !enemy.isDown()) {
                    enemy.up();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
