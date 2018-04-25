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
public class FragileWall extends Wall{
    
    public FragileWall()
    {
        super();
        initFragileWall();
    }
    
    public FragileWall(int x, int y) {
        super(x, y);
        initFragileWall();
    }

    private void initFragileWall() {
        loadImage("images/walls/fragilewall.png");
        setVisible(true);
    }
    
    
}
