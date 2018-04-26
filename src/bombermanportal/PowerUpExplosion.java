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
public class PowerUpExplosion extends Entity {

    public PowerUpExplosion() {
        super();
        InitPowerUpExplosion();
    }

    public PowerUpExplosion(int x, int y) {
        super(x, y);
        InitPowerUpExplosion();
    }

    private void InitPowerUpExplosion() {
        setVisible(true);
        loadImage("images/walls/ExtraFlame.png");
    }

}
