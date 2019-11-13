/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author sebasgamboa
 */
public class Arista {
    public int dato;
    public int peso;
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    
    public Arista(int dato,int sX,int sY,int eX,int eY){
        this.dato=dato;
        this.startX=sX;
        this.startY=sY;
        this.endX=eX;
        this.endY=eY;
    }
}
