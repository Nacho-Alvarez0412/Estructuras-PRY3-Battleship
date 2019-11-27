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
public class TradeAcceptPackage extends Package{
    public String weapon;
    public int price;
    public int player;
    public int steel;
    
    public TradeAcceptPackage(String w,int p,int pla){
        super("tradeAccept");
        this.weapon=w;
        this.price=p;
        this.player=pla;
        
    }
}
