/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombermanportal;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author blacklake
 */
public class Application extends JFrame{
    
    public Application(){
        initUI();
    }

    private void initUI() {
        add(new Board());
        setSize(544,550);
        setTitle("Bomberman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public static void main(String args[]){
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }

    
}
