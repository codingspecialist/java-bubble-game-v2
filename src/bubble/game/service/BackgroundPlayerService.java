package bubble.game.service;

import bubble.game.component.Bubble;
import bubble.game.component.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    public BackgroundPlayerService(Player player) {
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.player = player;
        this.bubbleList = player.getBubbleList();
    }

    @Override
    public void run() {
        while(true) {

            // 벽 감지
            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25 ));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15 , player.getY() + 25 ));

            if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                player.setLeft(false);
                player.setLeftWall(true);

            } else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                player.setRight(false);
                player.setRightWall(true);
            } else {
                player.setRightWall(false);
                player.setLeftWall(false);
            }


            int bottomColor = image.getRGB(player.getX() + 15, player.getY() + 50 + 5 )
                        + image.getRGB(player.getX() + 50 - 15, player.getY() + 50 + 5 );

            if(bottomColor != -2) {
                player.setDown(false);
            } else {
                if(!player.isUp() && !player.isDown()) {
                    player.down();
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
