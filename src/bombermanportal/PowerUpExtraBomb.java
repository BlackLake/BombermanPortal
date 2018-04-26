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
public class PowerUpExtraBomb extends Entity {

    public PowerUpExtraBomb() {
        super();
        InitPowerUpExtraBomb();
    }

    public PowerUpExtraBomb(int x, int y) {
        super(x, y);
        InitPowerUpExtraBomb();
    }

    private void InitPowerUpExtraBomb() {
        setVisible(true);
        loadImage("images/walls/ExtraBomb.png");
    }
}
