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
public class Wall extends Entity{
    
    public Wall()
    {
        super();
        initWalls();
    }
    
    public Wall(int x, int y) {
        super(x, y);
    }

    private void initWalls() {
        setVisible(true);
    }

}
