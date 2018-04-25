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
public class NonFragileWall extends Wall {

    public NonFragileWall() {
        super();
        initNonFragileWall();
    }

    public NonFragileWall(int x, int y) {
        super(x, y);
        initNonFragileWall();
    }

    private void initNonFragileWall() {
        loadImage("images/walls/nonFragileWall.png");
        setVisible(true);
    }

}
