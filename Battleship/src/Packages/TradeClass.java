/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

/**
 *
 * @author sebasgamboa
 */
public class TradeClass extends Package{
    public String weapon;
    public int price;
    public int player;
    public int origin;
    public int steel;
    
    public TradeClass(String w,int p,int pla,int o){
        super("trade");
        this.weapon=w;
        this.price=p;
        this.player=pla;
        this.origin=o;
    }
    
}
