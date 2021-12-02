package bubble.game;

import bubble.game.component.Enemy;
import bubble.game.component.Player;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Main extends JFrame {
    private Main main = this;
    private JLabel backgroundMap;
    private Player player;
    private List<Enemy> enemys = new ArrayList<>();
    public static int time;

    public Main() {
        initObject();
        initSetting();
        initListener();
        setVisible(true);
        timer();
    }

    public void next(){
        System.out.println("다음스테이지 이동");

    }

    private void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWall() && player.getState() == 0) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWall() && player.getState() == 0) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown() && player.getState() == 0) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if(player.getState() == 0) {
                            player.attack();
                        }
                        break;
                }

            }

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

    private void initObject() {
        player = new Player(main);
        backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
        setContentPane(backgroundMap);
        add(player);

        for(int i=0;i<4;i++) {
            Enemy enemy = new Enemy(main, 300 + (i*100), 178);
            add(enemy);
            enemys.add(enemy);
        }

    }

    private void initSetting() {
        setSize(1000, 640);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void timer(){
        time = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        while (true) {
                            if (player.getState() == 1) {
                                break;
                            }
                            Thread.sleep(1000);
                            time++;
                            System.out.println(time);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    public void gameover(){
        System.out.println("충돌 게임오버");
        player.setState(1);
    }





    public static void main(String[] args) {
        new Main();
    }
}
