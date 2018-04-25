/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombermanportal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author blacklake
 */
public class Flame extends Entity implements ActionListener {

    Timer timer;
    FlameDirection direction;

    public Flame()
    {
        super();
        InitFlame();
    }
    
    public Flame(int x, int y, FlameDirection dir) {
        super(x, y);
        direction = dir;
        InitFlame();
    }

    private void InitFlame() {
        setVisible(true);
        switch (direction) {
            case Up:
                loadImage("images/walls/FlameUp.png");break;
            case Right:
                loadImage("images/walls/FlameRight.png");break;
            case Down:
                loadImage("images/walls/FlameDown.png");break;
            case Left:
                loadImage("images/walls/FlameLeft.png");break;
        }
        timer = new Timer(500, (ActionListener) this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

}
