/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author sebasgamboa
 */

    /*
    1=connector
    2=mine
    3=armory
    4=temple
    5=market
    6=energy source
    */

public class Vertice implements Serializable{
    public int dato;
    public ArrayList<Point> point;
    public ArrayList<Arista> aristas=new ArrayList<>();
    public boolean vivo=true;
    public int hits;
    
    
    public Vertice(int dato,ArrayList<Point> p){
        this.dato=dato;
        this.vivo=true;
        this.point=p;
        if(this.dato==1) {
            this.hits=1;
        } 
        else if(this.dato==6){
            this.hits=4;
        }
        else{
            this.hits=2;
        }
    }
     
}
