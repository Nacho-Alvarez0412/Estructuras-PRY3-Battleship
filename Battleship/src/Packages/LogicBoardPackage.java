/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import Game.Grafo;

/**
 *
 * @author sebasgamboa
 */
public class LogicBoardPackage extends Package{
    public int[][] board;
    public int id;
    
    public LogicBoardPackage(int[][] board,int id) {
        super("LogicBoard");
        this.id = id;
        this.board=board;
    }
}
