/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import Clients.BoardLabel;
import Game.Ship;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author sebasgamboa
 */
public class ShipPackage extends Package{
    public int target;
    public int origin;
    public Point point;
    public ArrayList<BoardLabel> discoveries;
    public Ship ship;
    
    public ShipPackage(int t,int o,Point p,ArrayList<BoardLabel> d,Ship s){
        super("ship");
        this.target=t;
        this.origin=o;
        this.point=p;
        this.discoveries=d;
        this.ship=s;
    }
}
