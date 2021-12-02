package bubble.game.service;

import bubble.game.component.Bubble;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundBubbleService {

    private BufferedImage image;
    private Bubble bubble;

    public BackgroundBubbleService(Bubble bubble) {
        this.bubble = bubble;
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean leftWall() {
        Color leftColor = new Color(image.getRGB(bubble.getX() - 10, bubble.getY() + 25));

        if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
            bubble.setLeft(false);
            return true;
        }

        return false;
    }

    public boolean rightWall() {
        Color rightColor = new Color(image.getRGB(bubble.getX() + 50 + 15, bubble.getY() + 25));

        if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
            bubble.setRight(false);
            return true;
        }

        return false;
    }

    public boolean topWall(){
        Color topColor = new Color(image.getRGB(bubble.getX() + 25, bubble.getY()));
        if (topColor.getRed() == 255 && topColor.getGreen() == 0 && topColor.getBlue() == 0) {
            bubble.setUp(false);


            return true;
        }

        return false;
    }


}
