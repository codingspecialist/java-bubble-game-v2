package bubble.game.component;

import bubble.game.Main;
import bubble.game.Moveable;
import bubble.game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import java.util.List;

@Getter
@Setter
@ToString
public class Bubble extends JLabel implements Moveable {
    private Main main;
    private Player player;
    private List<Enemy> enemys;

    private final int SPPED = 3;
    private final int JUMP = 2;
    private int x;
    private int y;
    private ImageIcon bubble;
    private ImageIcon bubbled;
    private ImageIcon bomb;

    private boolean left;
    private boolean right;
    private boolean up;

    private int state; // 0, 기본물방울, 1 적을 가뒀을때, 2 터졌을때
    private Enemy attackEnemyInfo; // 가둔 적의 정보

    private BackgroundBubbleService backgroundBubbleService;

    public Bubble(Main main) {
        this.main = main;
        this.player = main.getPlayer();
        this.enemys = main.getEnemys();
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
        setLocation(x, y);

        state = 0;
    }


    // 물방울이 적에게 닿았을때
    public void attack(Enemy enemy) {
        System.out.println("적 맞춤");
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        attackEnemyInfo= enemy;
        main.remove(enemy);
        main.repaint();
    }

    // 3초후 물방울이 사라지게
    private void clearBubble(){
        if(state == 0 ){

        }

    }

    // 적을 가둔 물방울을 터트렸을때
    public void clearBubbled (){
        new Thread(()->{
            try {
                up = false;
                setIcon(bomb);
                Thread.sleep(1000);
                main.remove(this);
                main.repaint();

                if(state == 1) {
                    enemys.remove(attackEnemyInfo);
                    System.out.println("남은 적 수  : " + enemys.size());

                    if(enemys.size() == 0 ) {
                        main.next(); // 다음스테이지로
                    }
                }
                state = 2;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }


    @Override
    public void left() {
        left = true;

        EXIT :
        for (int i = 0; i < 200; i++) {
            x--;
            setLocation(x, y);
            if (backgroundBubbleService.leftWall()) {
                left = false;
                break;
            }
            for(int j=0;j<enemys.size();j++) {
                if(Math.abs(x - enemys.get(j).getX()) < 20 && Math.abs(y - enemys.get(j).getY()) < 30) {
                    if(enemys.get(j).getState() == 0) {
                        attack(enemys.get(j));
                        break EXIT;
                    }
                }

            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;

        EXIT :
        for (int i = 0; i < 200; i++) {
            x++;
            setLocation(x, y);
            if (backgroundBubbleService.rightWall()) {
                right = false;
                break;
            }

            for(int j=0;j<enemys.size();j++) {
                if(Math.abs(x - enemys.get(j).getX()) < 20 && Math.abs(y - enemys.get(j).getY()) < 30) {
                    if(enemys.get(j).getState() == 0) {
                        attack(enemys.get(j));
                        break EXIT;
                    }
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;

        while (up) {
            y--;
            setLocation(x, y);
            if(Math.abs(player.getX() - x) < 20 &&
                    Math.abs(player.getY() - y) > 0 && Math.abs(player.getY() - y) < 50) {
                clearBubbled();
                break;
            }

            if (backgroundBubbleService.topWall()) {
                up = false;
                top();
                break;
            }

            try {
                if(state == 0) {
                    Thread.sleep(1);
                } else {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void top(){
        while(true){
            // 적을 가둔 물방울은 3초 지나도 안터짐
            if(Math.abs(player.getX() - x) < 30 && Math.abs(player.getY() - y) < 50) {
                System.out.println("터짐");
                clearBubbled();
                break;
            }

            // 기본 물방울 3초후 터짐
            if(state == 0) {
                new Thread(()->{
                    try {
                        Thread.sleep(3000);
                        setIcon(bomb);
                        Thread.sleep(500);
                        main.remove(this);
                        main.repaint();
                        player.getBubbleList().remove(this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
