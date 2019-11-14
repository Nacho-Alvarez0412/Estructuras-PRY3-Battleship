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
public class TurnPackage extends Package{
    public int turn;
    
    public TurnPackage(int turn) {
        super("Turn");
        this.turn = turn;
    }
}
