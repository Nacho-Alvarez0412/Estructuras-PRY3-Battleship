/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author sebasgamboa
 */
public class hitsPackage extends Package{
    public ArrayList<Point> points;
    public int target;
    public int origin;
    
    public hitsPackage(ArrayList<Point> p,int t,int o){
        super("hits");
        this.points=p;
        this.target=t;
        this.origin=o;
    }
}
