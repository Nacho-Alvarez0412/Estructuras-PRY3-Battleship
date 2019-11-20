/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import Game.Vertice;
import java.util.ArrayList;

/**
 *
 * @author sebasgamboa
 */
public class AristasPackage extends Package{
    public ArrayList<Vertice> vertices;
    public int target;
    public int origin;
    
    public AristasPackage(ArrayList<Vertice> v,int t,int o){
        super("aristas");
        this.vertices=v;
        this.target=t;
        this.origin=o;
    }
}
