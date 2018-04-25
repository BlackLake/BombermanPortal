/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombermanportal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.SamplePlayer;

/**
 *
 * @author blacklake
 */
public class Board extends JPanel implements ActionListener {

    //the games takes place in this class.
    Timer timer;
    public Entity[][] entities = new Entity[15][17];//Wall table
    int map[][] = new int[15][17]; //[row][column]
    private final Player[] players = new Player[2];//Players Array
    int player1X = 15, player1Y = 13, player2X = 1, player2Y = 1;
    //List <Bomb> bombs = new ArrayList<Bomb>();
    static AudioContext ac = new AudioContext();
    Color backGroundColor = Color.decode("#55A704");
    Image background;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        players[0] = new Player(480, 464 - 32);//col:15 line:12
        players[1] = new Player(32, 80 - 32);//col:1 line:1
        players[0].loadImage("images/Player/player0.png");
        players[1].loadImage("images/Player/player1.png");
        drawMap();
        timer = new Timer(100, (ActionListener) this);
        timer.start();
        playBackgroundSound();
        backgroundPanel();
    }

    int x;
    int y;
    int multiplier = 32;
    int scorBoardSpace = 48;

    public void backgroundPanel() {
        // Loads the background image and stores in background object.
        background = Toolkit.getDefaultToolkit().createImage("images/walls/background.png");
    }

    public static void playBackgroundSound() {
        try {
            String audioFile = "audio/bombermanMusic.mp3";
            SamplePlayer player = new SamplePlayer(ac, SampleManager.sample(audioFile));
            Gain g = new Gain(ac, 2, 2f);
            g.addInput(player);
            ac.out.addInput(g);
            ac.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error with playing sound.");
        }
    }

    private void drawMap() {

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 17; column++) {
                x = column * multiplier;
                y = row * multiplier + scorBoardSpace;
                if ((row == 0) || (row == 14)) {
                    entities[row][column] = new NonFragileWall(x, y);
                } else if ((column % 2 == 0) && (row % 2 == 0)) {
                    entities[row][column] = new NonFragileWall(x, y);
                } else if ((column == 0) || (column == 16)) {
                    entities[row][column] = new NonFragileWall(x, y);
                } else if ((row == 1) && (column == 1) || (row == 1) && (column == 2) || (row == 1) && (column == 3) || (row == 2) && (column == 1) || (row == 3) && (column == 1)) {
                } else if ((row == 13) && (column == 15) || (row == 13) && (column == 14) || (row == 13) && (column == 13) || (row == 12) && (column == 15) || (row == 11) && (column == 15)) {
                } else {
                    entities[row][column] = new FragileWall(x, y);
                }
            }
        }

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 17; column++) {
                //0 : space
                //1 : player 1
                //2 : NonFragileWall
                //3 : FragileWall
                //4 : Bomb
                //5 : Flame
                //6 : player 2

                if ((row == 0) || (row == 14)) {
                    map[row][column] = 2;//NonFragileWall
                } else if ((column % 2 == 0) && (row % 2 == 0)) {
                    map[row][column] = 2;//NonFragileWall
                } else if ((column == 0) || (column == 16)) {
                    map[row][column] = 2;//NonFragileWall
                } else if ((row == 1) && (column == 1) || (row == 1) && (column == 2) || (row == 1) && (column == 3) || (row == 2) && (column == 1) || (row == 3) && (column == 1)) {
                    map[row][column] = 0;//Space
                } else if ((row == 13) && (column == 15) || (row == 13) && (column == 14) || (row == 13) && (column == 13) || (row == 12) && (column == 15) || (row == 11) && (column == 15)) {
                    map[row][column] = 0;//Space
                } else {
                    map[row][column] = 3;//FragileWall
                }
            }
        }
        map[player1Y][player1X] = 1;
        map[player2Y][player2X] = 6;
    }

    @Override
    public void paintComponent(Graphics g) {
        boolean last = false;
        if (timer.isRunning()) {
            super.paintComponent(g);
            last = drawObjects(g);
        }
        if (last) {
            drawObjects(g);
        }
    }

    private boolean drawObjects(Graphics g) {
        //draw game map
        g.drawImage(background, 0, 0, null);//background image   
        showMap();
        boolean last = false;

        for (int line = 0; line < 15; line++) {
            for (int column = 0; column < 17; column++) {
                x = column * multiplier;
                y = line * multiplier + scorBoardSpace;
                if (entities[line][column] != null) {
                    if ((line == 0) || (line == 14)) {
                        g.drawImage(entities[line][column].getImage(), x, y, 32, 32, null);
                    } else if ((column % 2 == 0) && (line % 2 == 0)) {
                        g.drawImage(entities[line][column].getImage(), x, y, 32, 32, null);
                    } else if ((column == 0) || (column == 16)) {
                        g.drawImage(entities[line][column].getImage(), x, y, 32, 32, null);
                    } else if (entities[line][column].isVisible()) {
                        g.drawImage(entities[line][column].getImage(), x, y, 32, 32, null);
                    }
                    if (map[line][column] == 4) {
                        if (!entities[line][column].isVisible()) {
                            if (((Bomb) entities[line][column]).owner == 0) {
                                players[0].bombCount++;
                            }
                            if (((Bomb) entities[line][column]).owner == 1) {
                                players[1].bombCount++;
                            }
                            bombSound();
                            for (int i = 1; i <= 2; i++) {// flame count = 2
                                if (map[line + i][column] == 2) {//NonFragileWall
                                    break;
                                }
                                if (map[line + i][column] == 3) {//FragileWall
                                    map[line + i][column] = 0;
                                    entities[line + i][column] = new Flame(x, y, FlameDirection.Down);
                                    break;
                                }
                                if (map[line + i][column] == 0) {//space
                                    map[line + i][column] = 0;
                                    entities[line + i][column] = new Flame(x, y, FlameDirection.Down);
                                }
                                if (map[line + i][column] == 1) {
                                    entities[line + i][column] = new Flame(x, y, FlameDirection.Down);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Purple wins");
                                        System.exit(0);
                                    });
                                }
                                if (map[line + i][column] == 6) {
                                    entities[line + i][column] = new Flame(x, y, FlameDirection.Down);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Blue wins");
                                        System.exit(0);
                                    });
                                }
                            }
                            for (int i = 0; i <= 2; i++) {
                                if (map[line][column + i] == 2) {//NonFragileWall
                                    break;
                                }
                                if (map[line][column + i] == 3) {//FragileWall
                                    map[line][column + i] = 0;
                                    entities[line][column + i] = new Flame(x, y, FlameDirection.Right);
                                    break;
                                }
                                if (map[line][column + i] == 0) {//space
                                    map[line][column + i] = 0;
                                    entities[line][column + i] = new Flame(x, y, FlameDirection.Right);
                                }
                                if (map[line][column + i] == 1) {
                                    entities[line][column + i] = new Flame(x, y, FlameDirection.Right);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Purple wins");
                                        System.exit(0);
                                    });
                                }
                                if (map[line][column + i] == 6) {
                                    map[line][column + i] = 0;
                                    entities[line][column + i] = new Flame(x, y, FlameDirection.Right);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Blue wins");
                                        System.exit(0);
                                    });
                                }
                            }
                            for (int i = 0; i <= 2; i++) {
                                if (map[line - i][column] == 2) {//NonFragileWall
                                    break;
                                }
                                if (map[line - i][column] == 3) {//FragileWall
                                    map[line - i][column] = 0;
                                    entities[line - i][column] = new Flame(x, y, FlameDirection.Up);
                                    break;
                                }
                                if (map[line - i][column] == 0) {//pace
                                    map[line - i][column] = 0;
                                    entities[line - i][column] = new Flame(x, y, FlameDirection.Up);
                                }
                                if (map[line - i][column] == 1) {
                                    entities[line - i][column] = new Flame(x, y, FlameDirection.Up);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Purple wins");
                                        System.exit(0);
                                    });
                                }
                                if (map[line - i][column] == 6) {
                                    entities[line - i][column] = new Flame(x, y, FlameDirection.Up);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Blue wins");
                                        System.exit(0);
                                    });
                                }
                            }
                            for (int i = 0; i <= 2; i++) {
                                if (map[line][column - i] == 2) {//NonFragileWall
                                    break;
                                }
                                if (map[line][column - i] == 3) {//FragileWall
                                    map[line][column - i] = 0;
                                    entities[line][column - i] = new Flame(x, y, FlameDirection.Left);
                                    break;
                                }
                                if (map[line][column - i] == 0) {//space
                                    map[line][column - i] = 0;
                                    entities[line][column - i] = new Flame(x, y, FlameDirection.Left);
                                }
                                if (map[line][column - i] == 1) {
                                    entities[line][column - i] = new Flame(x, y, FlameDirection.Left);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Purple wins");
                                        System.exit(0);
                                    });
                                }
                                if (map[line][column - i] == 6) {
                                    entities[line][column - i] = new Flame(x, y, FlameDirection.Left);
                                    timer.stop();
                                    last = true;
                                    SwingUtilities.invokeLater(()
                                            -> {
                                        JOptionPane.showMessageDialog(null, "Blue wins");
                                        System.exit(0);
                                    });
                                }
                            }

                            if (player1X == column && player1Y == line) {//bomb = player1
                                entities[line][column] = new Flame(x, y, FlameDirection.Up);
                                timer.stop();
                                last = true;
                                SwingUtilities.invokeLater(()
                                        -> {
                                    JOptionPane.showMessageDialog(null, "Purple wins");
                                    System.exit(0);
                                });
                            }
                            if (player2X == column && player2Y == line) {//bomb = player2
                                entities[line][column] = new Flame(x, y, FlameDirection.Up);
                                timer.stop();
                                last = true;
                                SwingUtilities.invokeLater(()
                                        -> {
                                    JOptionPane.showMessageDialog(null, "Blue wins");
                                    System.exit(0);
                                });
                            }

                            entities[line][column] = null;
                            map[line][column] = 0;
                        } else {
                            g.drawImage(entities[line][column].getImage(), x, y, 32, 32, null);
                        }
                    }
                }
            }
        }
        if (players[0].getY() <= players[1].getY()) {
            g.drawImage(players[0].getImage(), players[0].getX(), players[0].getY(), 32, 64, this);
            g.drawImage(players[1].getImage(), players[1].getX(), players[1].getY(), 32, 64, this);
        } else {
            g.drawImage(players[1].getImage(), players[1].getX(), players[1].getY(), 32, 64, this);
            g.drawImage(players[0].getImage(), players[0].getX(), players[0].getY(), 32, 64, this);
        }
        Toolkit.getDefaultToolkit().sync();

        return last;
    }

    private void showMap() {
        System.out.println("new map");
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 17; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void bombSound() {
        try {
            String audioFile = "audio/explosion.wav";
            SamplePlayer player = new SamplePlayer(ac, SampleManager.sample(audioFile));
            Gain g = new Gain(ac, 2, 0.5f);
            g.addInput(player);
            ac.out.addInput(g);
            ac.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error with playing sound.");
        }
    }

    private class TAdapter extends KeyAdapter implements ActionListener {

        public Timer timer;

        public TAdapter() {
            timer = new Timer(100, this);
            timer.start();
        }

        MultiKeyPressListener listener = new MultiKeyPressListener();

        @Override
        public void keyPressed(KeyEvent e) {
            listener.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            listener.keyReleased(e);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            listener.GetKeys().stream().map((key)
                    -> {
                if (key == KeyEvent.VK_LEFT) {

                    if (map[player1Y][player1X - 1] == 0) {
                        if (map[player1Y][player1X] != 4) {
                            map[player1Y][player1X] = 0;
                        }
                        player1X--;
                        map[player1Y][player1X] = 1;
                        players[0].setDx(-32);
                        players[0].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_RIGHT) {

                    if (map[player1Y][player1X + 1] == 0) {
                        if (map[player1Y][player1X] != 4) {
                            map[player1Y][player1X] = 0;
                        }
                        player1X++;
                        map[player1Y][player1X] = 1;
                        players[0].setDx(32);
                        players[0].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_UP) {

                    if (map[player1Y - 1][player1X] == 0) {
                        if (map[player1Y][player1X] != 4) {
                            map[player1Y][player1X] = 0;
                        }
                        player1Y--;
                        map[player1Y][player1X] = 1;
                        players[0].setDy(-32);
                        players[0].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_DOWN) {

                    if (map[player1Y + 1][player1X] == 0) {
                        if (map[player1Y][player1X] != 4) {
                            map[player1Y][player1X] = 0;
                        }
                        player1Y++;
                        map[player1Y][player1X] = 1;
                        players[0].setDy(32);
                        players[0].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_A) {

                    if (map[player2Y][player2X - 1] == 0) {
                        if (map[player2Y][player2X] != 4) {
                            map[player2Y][player2X] = 0;
                        }
                        player2X--;
                        map[player2Y][player2X] = 1;
                        players[1].setDx(-32);
                        players[1].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_D) {

                    if (map[player2Y][player2X + 1] == 0) {
                        if (map[player2Y][player2X] != 4) {
                            map[player2Y][player2X] = 0;
                        }
                        player2X++;
                        map[player2Y][player2X] = 1;
                        players[1].setDx(32);
                        players[1].move();
                    }
                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_W) {

                    if (map[player2Y - 1][player2X] == 0) {
                        if (map[player2Y][player2X] != 4) {
                            map[player2Y][player2X] = 0;
                        }
                        player2Y--;
                        map[player2Y][player2X] = 1;
                        players[1].setDy(-32);
                        players[1].move();
                    }

                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_S) {

                    if (map[player2Y + 1][player2X] == 0) {
                        if (map[player2Y][player2X] != 4) {
                            map[player2Y][player2X] = 0;
                        }
                        player2Y++;
                        map[player2Y][player2X] = 1;
                        players[1].setDy(+32);
                        players[1].move();
                    }
                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_SPACE)//player 2 bomb
                {
                    if (players[1].bombCount != 0 && map[player2Y][player2X] != 4) {
                        x = player2X * multiplier;
                        y = player2Y * multiplier + scorBoardSpace;

                        map[player2Y][player2X] = 4;
                        Bomb bomb = new Bomb(x, y);
                        bomb.owner = 1;
                        entities[player2Y][player2X] = bomb;
                        bombPutSound();
                        players[1].bombCount--;
                    }
                }
                return key;
            }).map((key)
                    -> {
                if (key == KeyEvent.VK_L)//player 1 bomb
                {
                    if (players[0].bombCount != 0 && map[player1Y][player1X] != 4) {
                        x = player1X * multiplier;
                        y = player1Y * multiplier + scorBoardSpace;

                        map[player1Y][player1X] = 4;
                        Bomb bomb = new Bomb(x, y);
                        bomb.owner = 0;
                        entities[player1Y][player1X] = bomb;
                        bombPutSound();
                        players[0].bombCount--;
                    }
                }
                return key;
            }).forEachOrdered((_item)
                    -> {
                repaint();
            });
        }

        private void bombPutSound() {
            try {
                String audioFile = "audio/bombPutted.wav";
                SamplePlayer player = new SamplePlayer(ac, SampleManager.sample(audioFile));
                Gain g = new Gain(ac, 2, 0.1f);
                g.addInput(player);
                ac.out.addInput(g);
                ac.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error with playing sound.");
            }
        }
    }

}
