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
public class Bomb extends Entity implements ActionListener{
    
    Timer timer;   
    int owner;
    
    public Bomb()
    {
        super();
        InitBomb();
    }
    
    public Bomb(int x, int y)
    {
        super(x, y);
        InitBomb();
    }

    private void InitBomb()
    {
        setVisible(true);        
        loadImage("images/walls/Bomb1.png");
        timer = new Timer(1500, (ActionListener) this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        setVisible(false);
    }
}
