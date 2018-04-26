/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombermanportal;

/**
 *
 * @author blacklake
 */
public class Player extends Entity {

    private int dx;
    private int dy;
    int bombCount = 1;
    int bombFlameLength = 1;

    public Player() {
        super();
    }

    public Player(int x, int y) {
        super(x, y);
    }

    public void move() {
        x += dx;
        y += dy;
        //System.out.println("PlayerX: "+x+" PlayerY: "+y+"PlayerDx: "+dx+"PlayerDY: "+dy);
        setTempZero();
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setTempZero() {
        dx = 0;
        dy = 0;
    }
}
