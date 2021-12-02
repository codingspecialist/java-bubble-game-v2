package bubble.game.component;

import bubble.game.Main;
import bubble.game.Moveable;
import bubble.game.service.BackgroundPlayerService;
import bubble.game.state.PlayerWay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Player extends JLabel implements Moveable {
    private Main main;
    private List<Bubble> bubbleList;
    private final int SPPED = 3;
    private final int JUMP = 2;
    private int x;
    private int y;
    private ImageIcon playerR, playerL;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private boolean leftWall;
    private boolean rightWall;

    private PlayerWay playerWay;

    private int state; // 0 살아있을때, 1 죽었을때


    public Player(Main main) {
        this.main = main;
        initObject();
        initSetting();
        initBackgroundService();
    }

    private void initObject() {
        playerL = new ImageIcon("image/playerL.png");
        playerR = new ImageIcon("image/playerR.png");
        bubbleList = new ArrayList<>();
    }

    private void initBackgroundService() {
        new Thread(new BackgroundPlayerService(this)).start();
    }

    public void initSetting() {
        x = 80;
        y = 535;

        setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);

        left = false;
        right = false;
        up = false;
        down = false;
        leftWall = false;
        rightWall = false;

        playerWay = PlayerWay.RIGHT;
        state = 0;
    }

    public void attack(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bubble bubble = new Bubble(main);
                main.add(bubble);
                bubbleList.add(bubble);
                if (playerWay == PlayerWay.LEFT) {
                    bubble.left();
                } else {
                    bubble.right();
                }
            }
        }).start();

    }



    @Override
    public void left() {
        setIcon(playerL);
        left = true;
        playerWay = PlayerWay.LEFT;

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
        setIcon(playerR);
        right = true;
        playerWay = PlayerWay.RIGHT;

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
                        for (int i = 0; i < 130 / JUMP; i++) {
                            y = y - JUMP;
                            setLocation(x, y);
                            Thread.sleep(5);
                        }


                        up = false;
                        down();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        }).start();

    }

    @Override
    public void down() {
        down = true;

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
            }
        }).start();
    }




}
