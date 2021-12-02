package bubble.game.component;

import bubble.game.Main;
import bubble.game.Moveable;
import bubble.game.service.BackgroundEnemyService;
import bubble.game.state.EnemyWay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;

@Getter
@Setter
@ToString
public class Enemy extends JLabel implements Moveable {
    private Main main;
    private final int SPPED = 2;
    private final int JUMP = 1;
    private int x;
    private int y;
    private ImageIcon enemyR, enemyL;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private boolean leftWall;
    private boolean rightWall;
    private int crashCount;

    private EnemyWay enemyWay;
    private int state; // 0 살아있을때, 1 물방울에 갇혔을때

    public Enemy(Main main, int x, int y) {
        this.main = main;
        this.x= x;
        this.y = y;

        initObject();
        initSetting();

        int n = (int)(Math.random()*2);
        if(n ==0) {
            left();
        } else {
            right();
        }
    }

    private void initObject() {
        enemyL = new ImageIcon("image/enemyL.png");
        enemyR = new ImageIcon("image/enemyR.png");
        initBackgroundService();

    }

    private void initBackgroundService() {
        new Thread(new BackgroundEnemyService(this)).start();
    }

    private void initSetting() {
//        x = 480;
//        y = 178;
        setIcon(enemyR);
        setSize(50, 50);
        setLocation(x, y);

        left = false;
        right = false;
        up = false;
        down = false;
        leftWall = false;
        rightWall = false;

        enemyWay = EnemyWay.RIGHT;
        state = 0;
        crashCount = 0;
        attack();
    }


    public void attack(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(main.getPlayer().getState() == 0){
                    int attackX = Math.abs(x - main.getPlayer().getX());
                    int attackY = Math.abs(y - main.getPlayer().getY());
                    if(state== 0 &&  5 <= attackX && attackX <= 45 && 5 <= attackY && attackY <= 45) {
                        main.gameover();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public void left() {
        setIcon(enemyL);
        left = true;
        enemyWay = EnemyWay.LEFT;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (left) {
                    x = x - SPPED;
                    setLocation(x, y);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void right() {
        setIcon(enemyR);
        right = true;
        enemyWay = EnemyWay.RIGHT;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (right) {
                    x = x + SPPED;
                    setLocation(x, y);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void up() {
        up = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    for (int i = 0; i < 130 / JUMP; i++) {
                        y = y - JUMP;
                        setLocation(x, y);
                        Thread.sleep(5);
                    }
                    up = false;
                    down();
                    leftWall = false;
                    rightWall = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void down() {
        down = true;

        left = false;
        right = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(down) {
                    y = y + JUMP;
                    setLocation(x, y);

                    try {
                        Thread.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                down = false;
                if(enemyWay == EnemyWay.RIGHT) {
                    right();
                } else {
                    left();
                }
            }
        }).start();



    }
}
