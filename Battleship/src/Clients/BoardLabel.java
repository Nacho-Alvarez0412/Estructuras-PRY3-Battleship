/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.ArmoryThread;
import Game.MineThread;
import Game.TempleThread;
import javax.swing.JLabel;

/**
 *
 * @author sebasgamboa
 */
public class BoardLabel extends JLabel {
    
    public int i;
    public int j;
    public int verticeName;
    public MineThread mine;
    public ArmoryThread armory;
    public TempleThread temple;
    
    public BoardLabel(int i, int j) {
        super();
        this.i = i;
        this.j = j;
        
    }

    public void setVerticeName(int verticeName) {
        this.verticeName = verticeName;
    }
       
}
