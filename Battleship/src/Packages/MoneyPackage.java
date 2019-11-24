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
public class MoneyPackage extends Package{
    public int target;
    public int money;
    public MoneyPackage(int t,int m){
        super("money");
        this.target=t;
        this.money=m;
    }
}
